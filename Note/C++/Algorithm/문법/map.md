# map
## map container란?
C++의 map는 C++ 표준 라이브러리(**S**tandard **T**emplate **L**ibrary)에 있는 container이다.   
<br/>
map은 각 노드가 **key와 value 쌍(pair)**으로 이루어진 균형이진트리이다. 또한, **중복을 허용하지 않는** 특징이 있다. 벡터와 달리 **Assoiciative Container**에 해당한다.
<br/>
C++의 map의 내부 구현은 검색, 삽입, 삭제가 O(logn) 인 레드블랙트리로 구성되어 있다.
<br/>
map은 데이터를 삽입할 때 내부에서 자동으로 정렬한다. (key를 기준으로 오름차순으로 정렬)   
<br/>

## 헤더
아래의 헤더를 추가하면 map을 사용할 수 있다.
```c++
#include <map>
```
<br/>

## 선언 & 초기화
```c++
// 기본 선언 방식 (<key 타입, value타입>)
map<string, int> m1;
map<int, int> m2;

// 내림차순으로 정렬하고 싶다면 greater 추가
map <int, int, greater> map1; 

// 데이터 삽입(insert)
m1.insert({"Cam", 300});
m1.1insert(make_pair("a", 1));
m1["dodo"]=30;
m2.insert(pair<int, int>(10, 20));
```
