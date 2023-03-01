import {useState, useRef} from "react";

const DiaryItem = ({id, author, content, emotion, created_date, onRemove, onEdit}) => {
    const [isEdit, setIsEdit] = useState(false); //수정중인지 아닌지

    const toggleIsEdit = () => setIsEdit(!isEdit); // isEdit 반전시키기

    const localContentInput = useRef();
    const [localContent, setLocalContent] = useState(content); //원래 내용을 세팅

    const handleClickRemove = () => {
        if (window.confirm(`${id}번째 일기를 정말 삭제하시겠습니까?`)) {
            onRemove(id);
        }
    };

    const handleQuitEdit = () => {
        toggleIsEdit();
        setLocalContent(content);
    };

    const handleEdit = () => {
        if (localContent.length < 5) {
            localContentInput.current.focus();
            return;
        }

        if (window.confirm(`${id}번 째 일기를 수정하시겠습니까?`)) {
            onEdit(id, localContent);
            toggleIsEdit();
        }
    };

    return (
        <div className="DiaryItem">
            <div className="info">
                <span className="author_info">
                    | 작성자 : {author} | 감정점수 : {emotion} |
                </span>
                <br />
                <span className="date">{new Date(created_date).toLocaleString()}</span>
            </div>
            <div className="content">
                {isEdit ? (
                    <>
                        <textarea
                            ref={localContentInput}
                            value={localContent}
                            onChange={(e) => setLocalContent(e.target.value)}
                        />
                    </>
                ) : (
                    <>{content}</>
                )}
            </div>

            {isEdit ? (
                <>
                    <button onClick={handleQuitEdit}>수정 취소</button>
                    <button onClick={handleEdit}>수정 완료</button>
                </>
            ) : (
                <>
                    <button onClick={handleClickRemove}>삭제하기</button>
                    <button onClick={toggleIsEdit}>수정하기</button>
                </>
            )}
        </div>
    );
};

export default DiaryItem;
