# React

## React.Component

```js
class ShoppingList extends React.Component {
    render() {
        return (
            <div className="shopping-list">
                <h1>Shopping List for {this.props.name}</h1>
                <ul>
                    <li>Instagram</li>
                    <li>WhatsApp</li>
                    <li>Oculus</li>
                </ul>
            </div>
        );
    }
}
```

ShoppingList는 **React 컴포넌트 클래스** 또는 **React 컴포넌트 타입**이다.  
개별 컴포넌트는 `props`라는 매개변수를 받아오고, `render` 함수를 통해 표시할 뷰 계층을 반환한다.

## render

render 함수는 화면에서 보고자 하는 내용을 반환한다.  
render는 렌더링할 내용을 경량화한 **React 엘리먼트**를 반환한다.  
React에서는 JSX 라는 특수한 문법을 사용하여 React 구조를 쉽게 작성하는데, 빌드하는 시점에서 아래와 같이 React.creatElement('div') 식으로 변환된다.

```js
return React.createElement(
    "div",
    {className: "shopping-list"},
    React.createElement("h1" /* ... h1 children ... */),
    React.createElement("ul" /* ... ul children ... */)
);
```

## JSX

JSX 내부의 중괄호 안에는 어떤 javascript 표현식도 사용할 수 있다.
React 엘리먼트는 javascript 객체이며, 변수에 저장하거나 프로그램에서 사용할 수 있다.

## props

```js
class Board extends React.Component {
    renderSquare(i) {
        return <Square value={i} />; //value props를 넘겨줌
    }
```

위와 같은 방식으로 부모 컴포넌트에서 props를 넣어주면 자식 컴포넌트에서 받을 수 있다.

```js
class Square extends React.Component {
    render() {
        //props를 받아서 렌더링 해준다.
        return (
            <buttonclassName="square">{this.props.value}</buttonclassName>
        );
    }
}
```

## state

```js
class Square extends React.Component {
    constructor(props){
        super(props);
        this.state={ //state 초기화
            value:null
        }
    }
```

리액트 컴포넌트는 생성자에 this.state를 설정하는 것으로 state를 가질 수 있다.

> JS에서 하위 클래스의 생성자를 정의할 때 super()를 호출해야 하므로, 모든 React 컴포넌트 클래스는 생성자를 가질 때 **super(props)** 호출 구문부터 작성해야 한다.

## setState

```js
class Square extends React.Component {
    //React 컴포넌트는 생성자에 this.state를 설정하는 것으로 state를 가질 수 있습니다
    constructor(props) {
        super(props);
        this.state = {
            //state 초기화
            value: null,
        };
    }

    render() {
        //props를 받아서 렌더링 해준다.
        return (
            <button
                className="square"
                onClick={() => {
                    this.setState({value: "X"});
                }}
            >
                {this.state.value}
            </button>
        );
    }
}
```

onClick 핸들러를 통해 this.setState를 호출하는 것으로, React에게 버튼을 클릭할 때마다 다시 렌더링 해야함을 알리고 있다.

## state 끌어올리기

> 여러개의 자식으로부터 데이터를 모으거나 두 개의 자식 컴포넌트들이 서로 통신하게 하려면 부모 컴포넌트에 공유 state를 정의해야 한다. 부모 컴포넌트는 props를 사용하여 자식 컴포넌트에 state를 다시 전달할 수 있다. 이것은 자식 컴포넌트들이 서로 또는 부모 컴포넌트와 동기화 하도록 만든다.

### onClick

> React에서 이벤트를 나타내는 prop에는 on[Event], 이벤트를 처리하는 함수에는 handle[Event]를 사용하는 것이 일반적이다.

## 불변성 (immutable)

일반적으로 데이터 변경에는 두가지 방법이 있다.

1. 데이터의 값을 직접 변경하는 방법

```js
var player = {score: 1, name: "Jeff"};
player.score = 2;
// 이제 player는 {score: 2, name: 'Jeff'}입니다.
```

2. 원하는 변경 값을 가진 새로운 사본을 데이터를 교체하는 것

```js
var player = {score: 1, name: "Jeff"};

var newPlayer = Object.assign({}, player, {score: 2});
// 이제 player는 변하지 않았지만 newPlayer는 {score: 2, name: 'Jeff'}입니다.

// 객체 spread 구문을 사용한다면 이렇게 쓸 수 있습니다.
var newPlayer = {...player, score: 2};
```

### immutable 지키면 좋은 점

1. 복잡한 특징을 단순하게 만든다.

    > 불변성은 복잡한 특징들을 구현하기 쉽게 만듭니다. 자습서에서는 “시간 여행” 기능을 구현하여 틱택토 게임의 이력을 확인하고 이전 동작으로 “되돌아갈 수 있습니다”. 이 기능은 게임에만 국한되지 않습니다. 특정 행동을 취소하고 다시 실행하는 기능은 애플리케이션에서 일반적인 요구사항 입니다. 직접적인 데이터 변이를 피하는 것은 이전 버전의 게임 이력을 유지하고 나중에 재사용할 수 있게 만듭니다.

2. 변화를 감지한다.
    > 객체가 직접적으로 수정되기 때문에 복제가 가능한 객체에서 변화를 감지하는 것은 어렵습니다. 감지는 복제가 가능한 객체를 이전 사본과 비교하고 전체 객체 트리를 돌아야 합니다.

불변 객체에서 변화를 감지하는 것은 상당히 쉽습니다. 참조하고 있는 불변 객체가 이전 객체와 다르다면 객체는 변한 것입니다.

3. React에서 다시 렌더링하는 시기를 결정함
    > 불변성의 가장 큰 장점은 React에서 순수 컴포넌트를 만드는 데 도움을 준다는 것입니다. 변하지 않는 데이터는 변경이 이루어졌는지 쉽게 판단할 수 있으며 이를 바탕으로 컴포넌트가 다시 렌더링할지를 결정할 수 있습니다.

## 함수 컴포넌트

React에서 함수 컴포넌트는 더 간단하게 컴포넌트를 작성하는 방법이며, state없이 render 함수만을 가진다. React.Component를 확장하는 클래스를 정의하는 대신, props를 입력받아서 렌더링할 대상을 반환하는 함수를 작성할 수 있다.

```js
function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}
```
