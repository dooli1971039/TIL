# Chap 03. 함수

## 작게 만들어라!

-   if/else/while 문 등에 들어가는 블록은 한 줄이어야 한다. (=> 내용을 함수로 분리해야 한다.)
-   함수에서도 들여쓰기 수준은 1단이나 2단을 넘어서면 안 된다.

## 한 가지만 해라!

-   함수 내에서 섹션을 나눌 수 있다면, 그 함수는 여러 작업을 한다는 증거이다.

## 함수 당 추상화 수준은 하나로!

-   함수가 확실히 한 가지 일만 하려면, 함수 내의 모든 문장의 추상화 수준이 동일해야 한다.
-   함수에서 추상화 수준이 섞여있으면, 코드를 읽을 때 특정 표현이 근본 개념인지 세부사항인지 구분하기가 어렵다. 이 둘이 뒤섞이기 시작하면, 깨어진 창문처럼 사람들이 함수에 세부 사항을 더 추가한다.

## 서술적인 이름을 사용하라!

-   코드를 읽으면서 짐작했던 기능대로 함수가 작동한다면 좋은 이름이다.
-   길고 서술적인 이름이 길고 서술적인 주석보다 낫다.

## 함수 인수

-   함수에서 이상적인 인수는 0개(무항) > 1개(단항) > 2개(이항) 순서이다.
-   인수는 개념을 이해하기 어렵게 만든다.
-   테스트 관점에서도 인수가 3개를 넘어가면 인수마다 유효한 값으로 모든 조합을 구성해 테스트하기가 상당히 부담스러워진다.

### 많이 쓰는 단항 형식

-   함수에 인수 1개를 넘기는 경우 예시
    1. 인수에 질문을 던지는 경우
        - ex) bool fileExist("MyFile")
    2. 인수를 무언가로 변환해 결과를 반환하는 경우
        - ex) InputStream fileOpen("MyFile")
    3. 이벤트 함수
        - 입력 인수는 있지만 출력 인수는 없다. 프로그램은 함수 호출을 이벤트로 해석해 입력 인수로 시스템 상태를 바꾼다.
-   위 경우가 아니라면 단항 함수는 가급적 피해야 한다.

### 플래그 인수

-   함수로 bool값을 넘기는 관례는, 함수가 한꺼번에 여러 가지를 처리한다는 뜻이다.

### 이항 함수

-   인수가 2개인 함수는 인수가 1개인 함수보다 이해하기 어렵다.

    -   ex) writeField(name), writeField(outputStream,name)

-   이항 함수가 적절한 경우도 있다.

    -   ex) Point p = new Point(0,0);

-   이항 함수는 인수의 순서를 인위적으로 기억해야 하기 때문에 실수할 수 있다.

### 삼항 함수

-   이항 함수보다 인자가 늘어났기 때문에 더 이해하기 어렵고, 인수 순서를 실수할 수 있다.

### 인수 객체

-   객체를 통해 인수를 줄이는 방법은 눈속임이라고 생각할 수도 있지만, 변수를 묶어 넘기려면 변수 이름이 필요하기 때문에 개념을 표시하게 된다.

```java
Circle makeCircle(double x, double y, double radius);
Circle makeCircle(Point center, double radius);
```

### 인수 목록

-   때로는 인수 개수가 가변적인 함수도 필요하다.  
    대표적인 예시로 String.format이 있지만 자세히 살펴보면 String.format은 사실 이항 함수다.

```java
public String format(String format, Object... args)
```

가변 인수를 취하는 모든 함수에 같은 원리가 적용된다.

### 동사와 키워드

-   함수의 의도나 인수의 순서와 의도를 제대로 표현하려면 좋은 함수 이름이 필요하다.

    1. 단항 함수는 함수와 인수가 동사/명사 쌍을 이뤄야 한다.

        - write(name) : 'name'이 무엇이든 'write'한다. (아래가 더 field에 쓴다는 사실이 드러난다.)
        - writeField(name) : 'name'이 'field'라는 사실이 좀 더 분명히 드러난다.

    2. 함수 이름에 키워드를 추가하는 형식 (함수 이름에 인수 이름을 넣는 것)
        - assertEquals보다 assertExpectedEqualsActual(expected, actual)이 인수 순서를 기억할 필요가 없어 더 좋다.

## 부수 효과를 일으키지 마라

-   함수에서 남몰래 다른 일을 하면 안 된다.

    -   ex) 클래스 변수 수정, 함수로 넘어온 인수나 시스템 전역 변수 수정 (세션 초기화 같은 것들)

-   부수 효과가 반드시 일어나게 된다면, 함수 이름에 명시해줘야 한다.  
     (이 경우 함수가 한 가지 일만 하게 된다는 원칙은 위배하게 된다.)

### 출력 인수

-   일반적으로 우리는 인수를 함수의 **입력**으로 해석한다.
-   appendFooter(s) 는 s를 바닥글에 첨부하는지, 바닥글에 s를 첨부하는지 이해하기 어렵다. 이 경우 함수의 선언부를 찾아가서 확인을 해보아야 한다.
-   report.appendFooter()와 같이, 출력인수를 피하는 방식이 좋다.
-   함수에서 상태를 변경해야 한다면, 함수가 속한 객체 상태를 변경하는 방식을 택해야 한다.

## 명령과 조회를 분리하라!

-   함수는 뭔가를 수행하거나 뭔가에 답하거나 둘 중 하나만 해야 한다. (=> 객체 상태를 변경하거나 객체 정보를 반환하거나)

```java
// 속성 값이 attribute인 값을 찾아 value로 설정한 후 성공하면 true 실패하면 false 반환
public boolean set(String attribute, String value);
```

위와 같은 함수는 좋지 않다. 명령과 조회를 분리해야 한다.

## 오류 코드보다 예외를 사용하라!

-   명령 함수에서 오류 코드를 반환하는 방식은 명령/조회 분리 규칙을 미묘하게 위반한다. if문에서 명령을 표현식으로 사용하기 쉽기 때문이다.
    ```java
    if(deletePage(page)==E_OK){
        ~~~~
    }else{
       ~~~
    }
    ```
-   오류 코드 대신 예외를 사용하면 오류 처리 코드가 원래 코드에서 분리되므로 코드가 깔끔해진다.
    ```java
    try{
        deletePage(page);
        ~~
    }
    catch(Exception e){
        ~~
    }
    ```

### Try/Catch 블록 뽑아내기

-   try/catch 블록은 기본적으로 코드 구조에 혼란을 일으키며, 정상 동작과 오류 처리 동작을 뒤섞는다. 그러므로 try/catch 블록을 별도 함수로 뽑아내는 편이 좋다.

### 오류 처리도 한 가지 작업이다.

-   오류 처리 또한 하나의 작업이므로, 오류를 처리하는 함수는 오류만 처리해야 마땅하다. 함수에 키워드 try가 있다면, 함수는 try로 시작해 catch/finally 문으로 끝나야 한다.

## 반복하지 마라!

-   많은 원칙과 기법이 중복을 없애거나 제어할 목적으로 나왔다.
    -   ex) 객체지향 프로그래밍은 코드를 부모 클래스로 몰아 중복을 없앤다.

## 구조적 프로그래밍

-   Dijksra는 모든 함수와 함수 내 모든 블록에 입구와 출구가 하나만 존재해야 한다고 말했다.  
     => 함수는 return 문이 하나여야 한다. (break, continue, goto 쓰지 말라는 의미)
    -   함수를 작게 만든다면 return, break, continue 등을 여러차례 사용해도 혼란스럽지 않지만, 함수가 큰 경우에는 지양하는 것이 좋다.
    -   goto는 큰 함수에서만 의미가 있으므로, 작은 함수에서는 지양해야 한다.

## 함수를 어떻게 짜죠?

-   처음에는 길고 복잡하게 짜더라도, 이후에 코드를 다듬고 함수를 만들고 이름을 바꾸고 중복을 제거하는 과정을 거치면 된다. 물론 단위 테스트 케이스도 만들어야 한다.
