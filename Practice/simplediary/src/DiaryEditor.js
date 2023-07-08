import React, {useState, useRef, useContext} from "react";
import {DiaryDispatchContext} from "./App";

const DiaryEditor = () => {
    //App.js에서 onCreate함수를 내려받음 -> context로 사용
    const {onCreate} = useContext(DiaryDispatchContext); //비구조화 할당으로 받아와야 한다!!

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
            //현재 가리키는 값을 current로 불러오는 것
            authorInput.current.focus(); //authorInput.current가 태그를 가리킨다
            return;
        }

        if (state.content.length < 5) {
            //focus
            contentInput.current.focus();
            return;
        }
        onCreate(state.author, state.content, state.emotion);
        // 데이터 추가하면 필드 초기화 할 것
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
                <textarea name="content" value={state.content} onChange={handleChangeState} ref={contentInput}/>
            </div>

            <div>
                <span>오늘의 감정점수 : </span>
                <select name="emotion" value={state.emotion} onChange={handleChangeState}>
                    {/* JSX에서 {} 안에 for문은 사용할 수 없다. */}
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

export default React.memo(DiaryEditor);
