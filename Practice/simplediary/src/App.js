import {useState, useRef, useEffect} from "react";
import "./App.css";
import DiaryEditor from "./DiaryEditor";
import DiaryList from "./DiaryList";
import LifeCycle from "./LifeCycle";

function App() {
    const [data, setData] = useState([]);
    const dataId = useRef(0);

    //async 키워드를 써서, getData가 promise를 반환하는 비동기함수로 만듬
    const getData = async () => {
        const res = await fetch("https://jsonplaceholder.typicode.com/comments").then((res) => res.json());

        const initData = res.slice(0, 20).map((it) => {
            return {
                author: it.email.split("@")[0],
                content: it.body,
                emotion: Math.floor(Math.random() * 5) + 1,
                created_date: new Date().getTime(),
                id: dataId.current++,
            };
        });

        setData(initData);
    };

    useEffect(() => {
        getData();
    }, []);

    const onCreate = (author, content, emotion) => {
        const created_date = new Date().getTime();
        const newItem = {
            author,
            content,
            emotion,
            created_date,
            id: dataId.current,
        };
        dataId.current += 1;
        setData([newItem, ...data]); //새로운 아이템 추가
    };

    const onRemove = (targetID) => {
        const newDiaryList = data.filter((it) => it.id !== targetID);
        setData(newDiaryList);
    };

    const onEdit = (targetID, newContentData) => {
        setData(data.map((it) => (it.id === targetID ? {...it, content: newContentData} : it)));
        // id가 targetID와 같은 경우
        // author, emotion, date는 같게 두고, content만 수정
    };

    return (
        <div className="App">
            {/* <LifeCycle /> */}
            <DiaryEditor onCreate={onCreate} />
            <DiaryList diaryList={data} onRemove={onRemove} onEdit={onEdit} />
        </div>
    );
}

export default App;
