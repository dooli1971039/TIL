# Chap 06. 객체와 자료 구조
1. 자료 구조
    - **자료를 그대로 공개**하며 별다른 함수는 제공하지 않는다.
    - 자료 구조를 사용하는 절차적인 코드는 기존 자료 구조를 변경하지 않으면서 새 함수를 추가하기 쉽다.
    - 하지만, 절차적인 코드는 새로운 자료 구조를 추가하기 어렵다.


2. 객체
    - 추상화 뒤로 자료를 숨긴 채 **자료를 다루는 함수만 공개**한다.
    - 객체 지향 코드는 기존 함수를 변경하지 않으면서 새 클래스를 추가하기 쉽다.
    - 하지만, 객체 지향 코드는 새로운 함수를 추가하기 어렵다. (모든 클래스를 고쳐야 한다)


- 복잡한 시스템을 짜다 보면 새로운 함수가 아니라 새로운 자료 타입이 필요한 경우가 생긴다. 이때는 클래스와 객체 지향 기법이 가장 적합하다.
- 새로운 자료 타입이 아니라 새로운 함수가 필요한 경우도 생긴다. 이 때는 절차적인 코드와 자료 구조가 더 적합하다.

## 디미터 법칙
- 모듈은 자신이 조작하는 객체의 속사정을 몰라야 한다는 법칙   
    => 객체는 조회 함수로 내부 구조를 공개하면 안 된다.

### 기차 충돌
```java
final String outputDir = ctxt.getOptions().getScraptionDir().getAbsolutePath();
```
위와 같은 코드를 기차 충돌(train wreck)이라 부른다.
당연히 피하는 것이 좋다.

```java
Options opts = ctxt.getOptions();
File scratchDir = opts.getScratchDir();
final String outputDir = scratchDir.getAbsolutePath();
```
위와 같이 나누면 어떻게 될까?   
위의 코드가 디미터 법칙을 위반하는지의 여부는, ctxt, Options, ScratchDir이 객체인지 자료 구조인지에 달려있다.   
객체라면 내부 구조를 숨겨야 하므로 확실히 디미터 법칙을 위반한다.   
자료 구조라면 원래 내부 구조를 노출해야 하므로 디미터 법칙이 적용되지 않는다. 하지만 getOptions()와 같은 방법을 사용하는 것이 아니라 아래 방식이 더 적합하다.
```java
final String outputDir = ctxt.options.scratchDir.absolutePath;
```

- 자료 구조는 무조건 함수 없이 공개 변수만 포함하고 객체는 비공개 변수와 공개 함수를 포함하면 좋다.

### 잡종 구조
- 절반은 객체, 절반은 자료 구조인 잡종 구조가 나온다.
- 잡종 구조는 중요한 기능을 수행하는 함수도 있고, 공개 변수나 공개 조회/설정 함수도 있다. 공개 조회/설정 함수는 비공개 변수를 그대로 노출하고, 덕택에 다른 함수가 절차적인 프로그래밍의 자료 구조 접근 방식처럼 비공배 변수를 사용하고 싶게 한다.

=> 위와 같은 잡종 구조는 새로운 함수, 새로운 자료 구조 모두 추가하기 어렵게 한다. (양쪽의 단점만 모아 놓은 구조이다.)