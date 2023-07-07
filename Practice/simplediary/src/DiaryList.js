import DiaryItem from "./DiaryItem";

const DiaryList = ({diaryList, onRemove, onEdit}) => {
    return (
        <div className="DiaryList">
            <h2>일기리스트</h2>
            <h4>{diaryList.length}개의 일기가 있습니다.</h4>
            <div>
                {/* map의 2번째 인자로 idx를 받아서 key에 넣어주는 방법도 있지만,
                리스트 순서가 바뀌면 문제가 될 수 있으니 id를 따로 받는 것이 좋다.*/}
                {diaryList.map((it) => (
                    <DiaryItem key={it.id} {...it} onRemove={onRemove} onEdit={onEdit}/>
                ))}
            </div>
        </div>
    );
};

// diaryList가 내려오지 않았을 때, []를 기본으로 전달
DiaryList.defaultProps = {
    diaryList: [],
};

export default DiaryList;
