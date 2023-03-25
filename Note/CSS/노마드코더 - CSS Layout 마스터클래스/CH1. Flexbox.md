### ch1.0 ~ 1.7

# Before Flexbox

```CSS
display: block;
```

block은 element이고, block옆에는 어떠한 element도 올 수 없다.

```CSS
display: inline-block;
```

block 속성을 유지하면서, 옆에 element가 올 수 있다.  
But, 가로로 block이 나열될 때, 옆에 이상한 공백이 생기게 된다.

```CSS
display: inline;
```

inline은 box가 아니다.  
유동적이기 때문에, width와 height가 없다.  
ex) text

# Flexbox

flexbox에서는 children과 이야기 하지 않는다.  
flexbox에서 뭔가를 움직이고 싶을 땐, **flexbox container**를 만들어서 사용한다.

## flexbox container를 만드는 법

```CSS
body{
    display:flex;
}
```

위와 같이 쓰면, body가 flexbox container가 된다.  
flexbox를 쓰려면 container와 자식이 바로 붙어있어야 한다. (밑의 body와 box처럼)  
만약, 저 사이에 다른 div 클래스가 있다면 box는 body가 flex인 것을 인지하지 못한다.

```HTML
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="styles.css" />
        <title>(S)CSS Masterclass</title>
    </head>
    <!-- body를 flexbox container로 만들어보자 -->
    <body>
        <div class="box">1</div>
        <div class="box">2</div>
        <div class="box">3</div>
    </body>
</html>
```

<span style="color:yellow">항상 붙어있는 부모가 자식의 위치를 움직일 수 있다.</span>

# Flexbox - Main axis, Cross axis

flex container의 default flex-direction은 row이다.

Q) 수평으로 된 item의 위치를 어떻게 바꿀까?  
A) position 속성 중 하나인 justify-content를 사용하면 된다.  
**justify-content**는 수평축에 있는 flex children의 위치를 변경한다.

```CSS
justify-content:center;
```

center 말고도, space-between, space-around 등 여러가지가 있지만, 외울필욘 없다.

-   flex-direction이 row에서 main axis는 가로다.(horizontal)  
    <span style="color:yellow">main axis 방향으로 item을 옮기기 위해서는 justify-content를 사용한다.</span>
-   main axis의 대비되는 존재로 **cross axis**가 있다.  
    <span style="color:yellow">cross axis 방향으로 item을 옮기기 위해서는 align-items를 사용한다.</span>

-   `align-items : center` 해도 차이가 안 난다면, 부모의 높이가 어떤지 체크해보자.  
    align-items에는 center말고도 flex-start, flex-end 등 여러가지가 있다.  
    이중에 **stretch**의 경우 자식에게 height가 없어야 한다.

-   flex-direction이 column이면, main axis가 세로, cross axis가 가로다.

# align-self, order

-   align-items과 비슷한 일을 한다.(**cross axis**)
    <span style="color:yellow"> 다만, 한 box에만 해당한다.</span>

```CSS
.father {
    display: flex;
    flex-direction: row; /*default: row*/
    /*Main Axis*/
    justify-content: space-around;
    height: 100vh;
}

.child {
    width: 200px;
    height: 200px;
    background: peru;
    color: white;
}

.child:nth-child(2) {
    align-self: center;
}
.child:nth-child(3) {
    align-self: flex-end;
}
```

위와 같이 코드를 쓰면 아래와 같은 결과가 나온다.
![css](https://user-images.githubusercontent.com/70802352/174489125-84b3fdb6-7c30-48f1-9244-c0955b390291.jpg)

-   child에게 줄 수 있는 또 다른 property로는 **order**가 있다.  
    box에게 순서를 변경하라고 할 수 있다.  
    이게 이상해 보이지만, HTML을 변경할 수 없을 때 유용하다.

-   기본적으로 box의 order는 **모두** 0 이다.

==> child에게 줄 수 있는 property는 **align-self**와 **order**이다.

# flex-wrap, reverse

-   flexbox는 item들이 모두 같은 줄에 있도록 유지한다.  
    (그래서 item의 개수가 많아지면 item의 width,height가 바뀌는 경우가 발생함)
-   `flex-wrap:nowrap`이 default이다.  
    이는 무슨 짓을 하더라도, flexbox안에 element들이 같은 줄에 있어야 한다는 것이다.
-   `flex-wrap:wrap`으로 바꾸면, child의 크기를 유지하라고 하는 것이다.

```CSS
.father {
    display: flex;
    flex-direction: row; /*default: row*/
    /*Main Axis*/
    justify-content: space-around;
    height: 100vh;
    flex-wrap: wrap;
}
```

-   flex-direction으로 **row-reverse**와 **column-reverse**를 줄 수도 있다.
-   만약, `flex-direction:row`인 상황에서 reverse를 하고 싶다면 `flex-wrap:wrap-reverse`를 하는 방법도 있다.
    ![css](https://user-images.githubusercontent.com/70802352/174490273-294f6d69-2d2b-404c-8039-f3c0c01241bf.jpg)
-   위의 사진에서 위,아래 box들 간의 공백은 **align-content**로 수정할 수 있다.  
    justify-content와 비슷해 보이지만 line(cross axis)을 위한 것이다.
-   align-content에는 center, space-between, space-around등이 있으며, space-around가 default이다.
-   `align-content: flex-start`를 하면 공백이 사라진다.

# flex-grow, flex-shrink

-   **flex-grow**, **flex-shrink**는 child에 줄 수 있는 property이다. (반응형 screen을 만들 때 유용)
-   **flex-shrink**는 기본적으로 element의 행동을 정의한다. flexbox가 쥐어짜질 때.  
    `flex-shrink:2`는 다른 child에 비히 2배로 줄어든다. (기본값:1)
-   **flex-grow**는 flex-shrink와 같지만 반대로 작용한다. (얼마나 많은 box가 커질까?)  
    `flex-grow:1`은 남아있는 빈 공간을 가져오거나, 가질 수 있는 만큼을 커지게 된다. (기본값:0)  
    아래와 같이 코드를 짜면, 남아있는 공간의 2/3는 두번째 child가, 1/3은 첫번째 child가 가져간다.

```CSS
.child:nth-child(2) {
    background-color: black;
    flex-grow: 2;
}

.child:nth-child(3) {
    flex-grow: 1;
}
```

# flex-basis

-   **flex-basis**는 child에 적용되는 property로, element에게 처음 크기를 주는 것이다.  
    (모든 것이 찌그러지거나 늘어나기 전에)
-   flex-basis는 width와 비슷하나 오직 width만 있는 것은 아니다.  
    (width와 유사 : flex-direction이 row일때. column이면 height와 유사할 것)
-   잘 쓰이지 않는다. width가 있기 때문에. (flex-basis는 변하지 않으념 width랑 같으니까)
