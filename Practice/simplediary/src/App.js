import {useState, useRef, useEffect, useMemo, useCallback} from "react";
import "./App.css";
import DiaryEditor from "./DiaryEditor";
import DiaryList from "./DiaryList";
import LifeCycle from "./LifeCycle";
import diaryList from "./DiaryList";

function App() {
    const [data, setData] = useState([]);
    const dataId = useRef(0); // re-render가 되더라도 값이 유지되어야 함

    //async 키워드를 써서, getData가 promise를 반환하는 비동기함수로 만듬
    const getData = async () => {
        const res = await fetch("https://jsonplaceholder.typicode.com/comments").then((res) => res.json());

        //데이터 20개만 사용
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
    }, []); // 의존성 배열이 비어있으므로, mount 될 때 한번만 실행

    const onCreate = useCallback((author, content, emotion) => {
        const created_date = new Date().getTime();
        const newItem = {
            author,
            content,
            emotion,
            created_date,
            id: dataId.current,
        };
        dataId.current += 1;
        //setData([newItem, ...data]); //새로운 아이템 추가
        setData((data) => [newItem, ...data]) //setState에 함수를 전달하여 항상 최신 data state를 참조하게 된다.
    }, []);
    /**
     *     빈 배열이고, setData([newItem,...data])로 하면 data가 mount된 시점 이후로 업데이트가 되지 않아 문제가 생김
     *     그렇다고 의존성 배열에 data를 넣으면, data를 수정/삭제를 할 때에도 입력창이 리렌더링 된다.
     *     그래서 setState에 함수의 인자로 data를 전달해야 한다.
     */

    const onRemove = useCallback((targetID) => {
        setData((data) =>
            data.filter((it) => it.id !== targetID)
        ); // diaryList를 새로 업데이트
    }, []);

    const onEdit = useCallback((targetID, newContentData) => {
        // id가 targetID와 같은 경우
        // author, emotion, date는 같게 두고, content만 수정
        setData((data) => data.map((it) => (it.id === targetID ? {...it, content: newContentData} : it)));
    }, []);

    const getDiaryAnalysis = useMemo(() => {
        const goodCount = data.filter((it) => it.emotion >= 3).length;
        const badCount = data.length - goodCount;
        const goodRatio = (goodCount / data.length) * 100;
        return {goodCount, badCount, goodRatio}
    }, [data.length]); //data.length의 값이 바뀔때만 useMemo의 callback함수가 다시 실행됨

    /**
     * useMemo로 감싸져 있지 않다면
     * getDiaryAnalysis()는 기본적으로 처음 렌더 되었을 때 data가 빈 배열이라서 0,0,0으로 한번 실행되고
     * 이후 getData()가 실행되고 데이터가 20개가 되면 또 실행된다.
     * 렌더링이 일어나는 만큼 아래 줄이 실행될 테니까..
     */
        //const {goodCount, badCouunt, goodRatio} = getDiaryAnalysis();

        // useMemo는 콜백 함수의 "값"을 반환하므로 함수로 쓸 수 없다.
    const {goodCount, badCouunt, goodRatio} = getDiaryAnalysis;

    return (
        <div className="App">
            {/* <LifeCycle /> */}
            <DiaryEditor onCreate={onCreate}/>
            <DiaryList diaryList={data} onRemove={onRemove} onEdit={onEdit}/>
        </div>
    );
}

export default App;
