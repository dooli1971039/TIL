## CH 2

### Call Signatures

-   함수 위에 마우스를 올렸을 때 보이는 힌트 창
-   어떻게 함수를 호출해야 하는지 알려줌  
    (함수의 인자는 무엇인지, return 타입은 무엇인지 등의 정보가 요약되어 있다.)

```typescript
const add = (a: number, b: number) => a + b;

type Add = (a: number, b: number) => number; // 함수의 call signature 타입을 만듬
// 함수를 구현하기 전에 함수가 어떻게 작동하는 지 서술해둘 수 있다.
const add: Add = (a, b) => a + b; //이렇게 적을 수 있음
```

### Overloading

-   오버로딩은 함수가 서로 다른 여러 개의 call signatures를 가지고 있을 때 발생시킨다.

```typescript
type Add = {
    (a:number, b:number) => number
    (a:number, b:string) => number
}

const add:Add = (a,b) => a+b // 이 경우 a+b에 빨간 줄 뜬다. b가 string|number 이기 때문

const add:Add = (a,b) => {
    if(typeof b ==='string') return a
    return a+b
}
```

```typescript
type Add = {
    (a:number, b:number) => number
    (a:number, b:number, c:number) => number
}

const add:Add = (a,b,c) => { // 에러 - C는 optional이라는 표시를 해줘야 함
    return a+b;
}

const add:Add = (a,b,c?:number) => {
    return a+b;
}
```
