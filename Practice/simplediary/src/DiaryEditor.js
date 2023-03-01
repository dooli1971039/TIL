import {useState, useRef} from "react";

const DiaryEditor = ({onCreate}) => {
    //App.js에서 onCreate함수를 내려받음

    const authorInput = useRef(); //html dom 요소에 접근할 수 있는 기능
    const contentInput = useRef();
    const [state, setState] = useState({
        author: "",
        content: "",
        emotion: 1,
    });

    const handleChangeState = (e) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = () => {
        if (state.author.length < 1) {
            //focus
            authorInput.current.focus(); //authorInput.current가 태그를 가리킨다
            return;
        }

        if (state.content.length < 5) {
            //focus
            contentInput.current.focus();
            return;
        }
        onCreate(state.author, state.content, state.emotion);
        setState({
            author: "",
            content: "",
            emotion: 1,
        });
    };

    return (
        <div className="DiaryEditor">
            <h2>오늘의 일기</h2>
            <div>
                <input
                    name="author" //state의 key와 같다
                    value={state.author}
                    onChange={handleChangeState}
                    ref={authorInput}
                />
            </div>

            <div>
                <textarea name="content" value={state.content} onChange={handleChangeState} ref={contentInput} />
            </div>

            <div>
                <span>오늘의 감정점수 : </span>
                <select name="emotion" value={state.emotion} onChange={handleChangeState}>
                    <option value={1}>1</option>
                    <option value={2}>2</option>
                    <option value={3}>3</option>
                    <option value={4}>4</option>
                    <option value={5}>5</option>
                </select>
            </div>

            <div>
                <button onClick={handleSubmit}>일기 저장하기</button>
            </div>
        </div>
    );
};

export default DiaryEditor;
