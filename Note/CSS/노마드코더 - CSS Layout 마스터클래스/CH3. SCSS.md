### ch3.0 ~ 3.4

# ch3.0 : CSS Preprocessors and Set Up

-   **SCSS**란 CSS Preprocessor다.
-   preprocessor에는 SCSS 말고도 다른 종류도 있다. (ex- stylus)
-   SCSS는 css를 programming language처럼 만든다.
-   SCSS는 compile 또는 build하는 과정이 필요하다.
-   **gulf**는 compile하거나 transform 해주는 nodeJS 패키지이다.  
    새로운 코드를 오래된 코드로 바꾸거나, SCSS를 평범한 css로 바꿔준다.

# ch3.1 : Variable and Nesting

```javascript
const routes = {
    css: {
        watch: "src/scss/*",
        src: "src/scss/styles.scss", //여기서 일어나는 모든 일은 css로 compile된다.
        dest: "dest/css",
    },
};

const clean = () => del(["dest/"]); //컴파일된 css가 담기는 곳이다.
```

scss에서 내용을 바꾸면, **실시간**으로 dest안의 css파일도 바뀐다.  
(yarn dev 또는 npm run dev를 했을 때!)

## Variable

-   기본적으로 website에서 가장 중요한 colors나 styles을 저장하고 싶을 때 사용한다.
-   파일을 만들 때, 앞에 \_(언더바)를 붙이는 경우는 css로 변하지 않았으면 하는 것들이다.

```scss
$bg: #e7473c;
$title: 32px;
```

-   변수를 만들 때는, $를 붙여서 만든다.

## Nesting

-   코드를 좀 더 정확하게 해준다.  
     => 타겟으로 하는 element를 더 명확하게 해준다.

```scss
.box {
    margin-top: 20px;
    &:hover {
        background-color: green;
    }
    h2 {
        color: blue;
        &:hover {
            color: red;
        }
    }
    button {
        color: red;
    }
}
```

-   이런식으로 nesting을 이용하면 코드를 더 깔끔하게 적을 수 있다.

# ch3.2 : Mixins

-   mixin은 scss functionality를 재사용할 수 있도록 해준다.
-   mixin은 상황에 따라 다르게 코딩을 하고 싶을 때 사용한다.

```scss
@mixin sexyTitle {
    color: blue;
    font-size: 30px;
}
```

-   mixin을 만들라면, `@mixin 함수이름 {}`을 쓰면 된다.
-   그리고 styles.scss에서 위 파일을 import하고 `@include sexyTitle();` 와 같이 사용하면 된다.
-   주로 사용하는 방식은 아래와 같다.

```scss
@mixin link($color) {
    text-decoration: none;
    display: block;
    color: $color;
}
```

```SCSS
@import "_mixins";

a {
    margin-bottom: 10px;
    &:nth-child(odd) {
        @include link(red);
    }
    &:nth-child(even) {
        @include link(blue);
    }
}
```

위 코드에서 색상을 보내지 않고 문자열을 보낸다면, link함수는 아래처럼 바꿀 수 있다.

```scss
@mixin link($word) {
    text-decoration: none;
    display: block;

    @if $word == "odd" {
        color: blue;
    } @else {
        color: red;
    }
}
```

# ch3.3 : Extends

-   같은 코드를 중복하고 싶지 않을 때 사용한다.
-   extend를 사용할 수 있는 scss를 만들려면 %를 앞에 붙이면 된다.

```scss
%button {
    font-family: inherit;
    border-radius: 7px;
    font-size: 12px;
    text-transform: uppercase;
    padding: 5px 10px;
    background-color: peru;
    color: white;
    font-weight: 500;
}
```

그리고 사용은 아래와 같이 한다.

```scss
@import "_buttons";
a {
    @extend %button;
    text-decoration: none;
}

button {
    @extend %button;
    border: none;
}
```

# ch3.4 : Awesome Mixins and Conclusions

```SCSS
@mixin responsive {
    @content;
}
```

```SCSS
@import "_mixins";
a{
    @include responsive{
        text-decoration:none;
    }
}
```

이런식으로 짜면, `text-decoration:none;`이 `@content`가 된다.  
@content를 실제 코드에 적용시켜보면 아래와 같다.

```SCSS
$minIphone: 500px;
$maxIphone: 690px;
$minTablet: $minIphone + 1;
$maxTablet: 1120px;

@mixin responsive($device) {
  @if $device == "iphone" {
    @media screen and (min-width: $minIphone) and (max-width: $maxIphone) { //미디어 쿼리
      @content;
    }
  } @else if $device == "tablet" {
    @media screen and (min-width: $minTablet) and (max-width: $maxTablet) {
      @content;
    }
  } @else if $device == "iphone-l" {
    @media screen and (max-width: $minIphone) and (max-width: $maxIphone) and (orientation: landscape) {
      @content;
    }
  } @else if $device == "ipad-l" {
    @media screen and (min-width: $minTablet) and (max-width: $maxTablet) and (orientation: landscape) {
      @content;
    }
  }
}
```

```SCSS
@import "_mixins";
h1 {
    color: red;
    @include responsive("iphone") {
        color: yellow;
    }
    @include responsive("iphone-l") {
        font-size: 60px;
    }
    @include responsive("tablet") {
        color: green;
    }
}
```
