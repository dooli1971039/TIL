## CH 2

### Typescript

-   strongly typed(강타입) 프로그래밍 언어
-   TS의 경우, 작성한 코드가 JS로 변환된다.  
    (브라우저가 JS를 이해하기 때문 - Node.js는 둘 다 이해함)
-   TS는 개발자가 실수하지 않도록 보호해준다.  
    => TS가 제공하는 보호장치는 TS ->JS 변환 전에 발생한다.  
    (문제가 발생할 것 같으면 컴파일되지 않음)

### Type

-   TS는 데이터와 변수의 타입을 명시적으로 정의할 수 있다. (**변수 : 타입**)
-   타입을 추론할 수 있는 경우, 명시적으로 적지 않아도 된다.

```typescript
let a: boolean = true; // 명시적으로 정의
let b = false; // TS가 타입 추론
let c: boolean = "x"; // 에러
```

-   type checker가 타입을 추론할 수 있는 경우에는 타입을 생략하는 것이 낫다.  
    (명시적 표현은 최소한으로 사용하자)

```typescript
let a = [1, 2, 3, 4];
a.push("3"); // 에러 - 숫자 배열에 문자열 추가 불가능

let b: number[] = []; //이런 경우 추론할 수 없기 때문에 명시적으로 표시
```

### Object

#### Q: Player 객체가 name은 항상 가지는데, age는 선택적으로 가진다면?

```typescript
const player : {
    name : string,
    age ?: number  // age는 number일 수도 있고 undefined일 수도 있다.
} = {
    name : "nico";
}

if(player.age && player.age<10){
    // player.age가 undefined일 수도 있어서 체크를 해줘야 함

}

// 이렇게 alias 타입을 생성할 수도 있다.
type Player = {
    name : string,
    age ?: number
}

const nico : Player = {
    name : "nico"
}

const lynn : Player = {
    name : "lynn"
    age : 20
}

type Num : number; // 어떤 타입이든 가능하다
```

### function

```typescript
function playerMaker(name: string): Player {
    // :Player 라고 리턴타입을 적지 않으면 그냥 object를 리턴하는 것으로 생각한다.
    return {
        name, //함수의 인자와 Player object의 인자의 이름이 같아서 가능
        // 보통은 name : name 으로 쓴다.
    };
}
```

### readonly

-   TS는 readonly라는 속성을 타입에 추가할 수 있다.

```typescript
type Player = {
    readonly name: string; // 값을 수정하려 하면 오류를 발생시킨다.
    age?: number;
};

const numbers: readonly number[] = [1, 2, 3, 4, 5];
numbers.push(30); //에러

const str: readonly string[] = ["aa", "bb"];
str.push("3"); //에러
// map이나 filter 등의 함수 사용 가능 - 배열을 바꾸는 것이 아니기 때문
```

### Tuple

-   Tuple은 배열을 생성할 수 있게 하는데, 최소한의 길이를 가져야 하고, 특정 위치에 특정 타입이 있어야 한다.  
    => 정해진 갯수의 요소를 순서대로 가져야 하는 array를 지정할 수 있다.

```typescript
const player: [string, number, boolean] = ["nico", 10, true];
// 위와 같이 튜플을 사용 시
// player는 길이가 최소 3이고, 문자열-숫자-bool타입을 순서대로 가지는 배열이어야 한다.

const player: readonly [string, number, boolean] = ["nico", 10, true];
player[0] = "hi"; //에러
```

### undefined, null, any

-   비어있는 값들을 쓰면 기본값이 any가 된다.
-   any는 TS의 모든 보호장치를 비활성화 시킨다.

```typescript
let a: undefined = undefined;
let b: null = null;

let c = []; //  TS는 기본적으로 a를 any의 array라고 생각한다.

let d: any[] = [1, 2, 3, 4];
let e: any = ture;
d + e; //가능함
```

### unknown, void, never

-   변수의 타입을 미리 알지 못 할 때 **unknown**을 사용한다.

```typescript
let a: unknown; // 어떤 작업을 하려면, 이 변수의 타입을 먼저 확인해야 한다.
let b = a + 1; // TS가 허락해주지 않음

if (typeof a === "number") {
    // 이런식으로 체크해야 함
    let b = a + 1;
}

if (typeof a === "string") {
    // 이런식으로 체크해야 함
    let b = a.toUpperCase();
}
```

-   아무것도 return하지 않는 함수는 **void** 타입이다.

-   함수가 절대 return하지 않을 때 **never**를 사용한다.
-   또한, **never**는 타입이 두가지 일 수도 있는 상황에 사용한다.

```typescript
function hello(): never {
    throw new Error("xxx");
}

function hello(name: string | number) {
    if (typeof name === "string") {
        // 문자열일 때
    } else if (typeof name === "number") {
        // 숫자일 때
    } else {
        name; // 여기서 name은 string도 아니고 number도 아니고 never이다.
        // 이 부분의 코드는 절대 실행되면 안 된다.
    }
}
```
