import React, {useEffect, useState} from "react";

const UnMountTest = () => {
    useEffect(() => {
        console.log("Sub Component Mount");
        //useEffect에 전해지는 콜백 함수에서
        //함수를 리턴하면 unmount시 실행된다.
        return () => {
            console.log("Sub Component Unmount");
        };
    }, []);
    return <div>UN MOUNT TEST</div>;
};

const LifeCycle = () => {
    const [count, setCount] = useState(0);
    const [text, setText] = useState("");

    const [isVisible, setIsVisible] = useState(false);
    const toggle = () => setIsVisible(!isVisible);

    useEffect(() => {
        console.log("Mount!");
    }, []); // 빈배열을 넘기면, mount된 시점에만 작동

    useEffect(() => {
        console.log("Update!");
    }); //dependency array를 안 넘기면, 뭐라도 update 되면 작동

    useEffect(() => {
        console.log(`count is update : ${count}`);
        if (count > 5) {
            console.log("카운트가 5를 넘었습니다. 1로 초기화합니다.");
            setCount(1);
        }
    }, [count]);

    useEffect(() => {
        console.log(`text is update : ${text}`);
    }, [text]);

    return (
        <div style={{padding: 20}}>
            <div>
                {count}
                <button onClick={() => setCount(count + 1)}>count up</button>
            </div>
            <div>
                <input type="text" value={text} onChange={(e) => setText(e.target.value)} />
            </div>
            <button onClick={toggle}>ON/OFF</button>
            {isVisible && <UnMountTest />}
        </div>
    );
};

export default LifeCycle;
