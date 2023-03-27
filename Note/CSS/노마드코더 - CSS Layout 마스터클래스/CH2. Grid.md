### ch2.1 ~ 2.15

# ch2.1 : Life Before Grid

-   flex box로는 grid 형태를 만들기 어렵다.

# ch2.2 : CSS Grid Basic Concepts

```CSS
.father {
    display: grid; /*grid 화면 생성 - father에서 해야함*/
    grid-template-columns: 250px 250px 250px; /*3개 열 생성*/
    grid-template-rows: 100px 50px 300px; /*3개 행 생성*/
    column-gap: 5px; /*열 사이 간격*/
    row-gap: 10px; /*행 사이 간격*/
}
```

-   행과 열의 간격이 동일하면 `gap:10px`로 적어도 된다.

# ch2.3 : Grid Template Areas

`grid-template-columns: 250px 250px 250px;` 이 코드를 `grid-template-columns: repeat(3,250px);`로 쓸 수 있다.

```CSS
.grid {
    grid-template-columns: auto 200px;
    grid-template-rows: 100px repeat(2, 200px) 100px;
    grid-template-areas:
        "header header header header"
        "content content content nav"
        "content content content nav"
        "footer footer footer footer";

}

.header {
    background-color: #2ecc71;
    grid-area: header; /*따옴표 쓰면 안 됨*/
}
```

-   **grid-template-areas**를 쓰려면 **grid-area**로 영역에 이름을 설정해줘야 한다.

-   `"content content . nav"` 이런식으로 비워둘 수도 있다.

# ch2.4 : Rows and Columns

```CSS
.grid {
    display: grid;
    gap: 10px;
    grid-template-columns: repeat(4, 100px);
    grid-template-rows: repeat(4, 100px);
}

.header {
    background-color: #2ecc71;
    grid-column-start: 1; /*header가 어디서부터 시작하는지*/
    grid-column-end: 3; /*header가 어디서 끝나는지*/
}

.nav {
    background-color: #8e44ad;
    grid-row-start: 2;
    grid-row-end: 4;
}
```

-   start와 end는 같이 있어야 한다.
-   위의 start~end는 column을 의미하는 것이 아니라, line을 의미하는 것이다.  
     즉, column 1에서 시작하고 3에서 끝나는 것이 아니다.(3칸 차지)  
     line 1 ~ line 3 이므로, 2칸을 차지한다. (line은 gap이 생기는 위치)
-   **grid-row/column-start/end**를 잘 쓰면 HTML수정 없이 요소의 위치/순서를 바꿀 수 있다.

# ch2.5 : Shortcuts

```CSS
grid-row-start: 2;
grid-row-end: 4;
```

-   위 코드는 **grid-row : 2/4**로 바꿀 수 있다.

-   **grid-row : 1/5**는 **grid-row : 1/-1**로 바꿔 쓸 수 있다.  
    끝에서부터 -1, -2, -3 ... 으로 쓸 수 있다.

-   위의 방법처럼 시작과 끝을 적는 대신에 **span**을 쓸 수 있다.  
    얼마나 많은 cell을 가지고 있는지를 적으면 된다.

-   시작점이 필요할 때는 **grid-row: 2 / span 2** 처럼 적어도 된다.

# ch2.6 : Line Naming

```CSS
/*line에 이름 붙이기*/
.grid {
    display: grid;
    gap: 10px;
    grid-template-columns: [first-line] 100px [second-line] 100px [third-line] 100px [fourth-line] 100px [fifth-line];
    grid-template-rows: repeat(4, 100px [sexy-line]); /*repeat 안에 이름 넣기*/
}

/*line 이름 사용하기*/
.content {
    background-color: #3498db;
    grid-column: first-line / fourth-line;
    grid-row: sexy-line 1 / sexy-line 3;
}
```

-   위 코드처럼 line에 이름을 붙일 수 있다.
-   repeat에 넣어서 하는 방식은 결국 `100px [first-line] 100px [second-line] 100px .....`과 같다. (맨 앞에 line이 사라짐)

# ch2.7 : Grid Template

**fr**은 fraction이다. fraction은 사용 가능한 공간을 의미한다.

```CSS
grid-template-columns: repeat(4, 1fr); /*화면 크기 전체를 4개로 나눔*/
grid-template-rows: repeat(4, 100px);
```

-   width나 height를 따로 지정해주면 fr의 크기고 그에 따라 바뀐다.

```CSS
.grid {
    display: grid;
    gap: 5px;
    height: 50vh;
    grid-template:
        "header header header header" 1fr /*row의 높이가 어떻게 되는지 정해줌*/
        "content content content nav" 2fr
        "footer footer footer footer" 1fr / 1fr 1fr 1fr 1fr;
        /*마지막으로 각 column의 너비가 어떻게 되는지를 정해줌*/
}

.header {
    background-color: #2ecc71;
    grid-area: header;
}
```

-   이런식으로 짧게 해결할 수도 있다.
-   `[line-start] "header header header header" 1fr [line-end]` 이런식으로 이름을 넣을 수도 있다.  
    (단, 한 줄만 넣는 것이 아니라 전부 넣어야 한다.)
-   **grid-template**에서는 repeat는 쓰지 못한다.

# ch2.8 : Place Items

-   justify-items(수평) / align-items(수직)  
    stretch가 기본, start, end, center 등이 있다.

-   `<div class="footer">footer</div>` 이런식으로 내용이 있을 때 사용해야 한다.  
    내용이 없는데 사용하면, width와 height가 없기 때문에 나타나지 않을 것이다.  
    (css에서 .footer에 width와 height를 지정하면 나타나긴 한다.)

-   align-items(수직) / justify-items(수평)을 한번에 쓸 수도 있다.  
    `place-items: stretch center; /*수직 수평*/`

# ch2.9 : Place Content

-   **justify-content**는 grid 자체를 움직이는 것
-   **justify-items**는 cell의 배치를 논하는 것

-   align-content(수직) / justify-content(수평)을 한번에 쓸 수도 있다.  
     `place-content: end center; /*수직 수평*/`

# ch2.10 : Auto Columns and Rows

```CSS
.header {
  background-color: #2ecc71;
  align-self:end; /*child에 적용되는 property*/
  justify-self:center;
  place-self: end center; /*위에 두개 한번에 적는 경우*/
}
```

```CSS
.grid {
    color: white;
    display: grid;
    gap: 5px;

    grid-template-columns: repeat(4, 100px);
    grid-template-rows: repeat(4, 100px);
    grid-auto-flow: column; /*진행방향*/
    grid-auto-columns: 100px; /*grid template에서 정해주지 않은게 있다면 100px로*/
}

.item:nth-child(odd) {
    background-color: #2ecc71;
}
```

# ch2.11 : minmax

-   minmax: 요소가 얼마나 작게/크게 될 수 있는지  
    `grid-template-columns: repeat(5, minmax(100px, 1fr));`

# ch2.12 : auto-fit auto-fill

-   **auto-fit**과 **auto-fill**은 repeat에만 사용한다.
-   auto-fill : row를 empty column으로 채우는 것
-   auto-fit : current div를 가져와서 stretch한다.

```CSS

.grid:first-child {
    grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
}

.grid:last-child {
    grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
}
```

# ch2.13 : min-content max-content

-   min-content: content가 작아질 수 있는 만큼 작아지는 것
-   max-content: content가 필요한 만큼 커지는 것  
    `grid-template-columns: min-content max-content;`
-   혼합해서 쓸 수도 있다.  
    `grid-template-columns: repeat(5, minmax(max-content, 1fr));`
