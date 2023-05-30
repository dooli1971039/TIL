# vector
## vector container란?
C++의 vector는  C++ 표준 라이브러리(**S**tandard **T**emplate **L**ibrary)에 있는 container이다.  
<br/>
vector는 원소를 순서대로 보관하는 **Sequence Container**에 해당하는데, 배열과 달리 동적이기 때문에 **자동으로 메모리가 할당되는 배열**이라고 생각하면 된다. vector를 생성하면 **heap 영역**에 생성된다.
<br/>
기본적으로 맨 뒤에서 원소의 삽입/삭제가 이루어진다. 중간에 값을 삽입하거나 삭제할 수도 있지만, 배열 기반이기 때문에 삽입/삭제가 일어난다면 비효율적이다.(이 경우 linked list를 쓰는게 낫다.)  
<br/>

## 헤더
아래의 헤더를 추가하면 vector를 사용할 수 있다.
```c++
#include <vector>
```
<br/>

## 선언 & 초기화
```c++
//int형 벡터 생성
vector<int> v;


//int형 벡터 생성 후 크기를 5로 할당 (0으로 초기화 됨)
vector<int> v(5);
//int형 벡터 생성 후 크기를 4로 할당 (3으로 초기화 됨)
vector<int> v(5,3);


//int형 벡터를 생성 후 {1, 2, 4, 6}으로 초기화
vector<int> v = {1, 2, 4, 6};


//2차원 벡터 생성 (행은 가변적이지만, 열은 고정됨)
vector<int> v[] = {{1,2}, {3,4}}; 
//2차원 벡터 생성 (행과 열 모두 가변)
vector<vector<int>> v; 


//10행 5열의 2차원 벡터 (3으로 초기화 됨)
vector <vector<int>> v(10, vector<int>(4,3));
```

<br/> 

## 함수
### 기본  
```c++
v.assign(n,m); // n개의 원소를 m으로 할당
v.clear(); //모든 원소를 제거 (메모리는 그대로 남아있는다)


v[idx]; // idx번째 원소 참조 
v.at(idx); // idx번째 원소 참조 (유효한 index인지 체크한다)
//v.at(idx)는 v[idx]보다 느리지만, 범위를 점검하기 때문에 좀 더 안전하다.


v.front(); // 첫번째 원소 참조
v.back(); // 마지막 원소 참조


v.begin(); // 벡터 시작점의 주소 값 반환
v.end(); // 벡터 (끝부분+1)의 주소 값 반환


v.push_back(n); //마지막에 n 삽입
v.pop_back(); //마지막 원소 제거


v.size(); //원소 개수 반환
v.empty(); //비어있으면 true 아니면 false 반환
```
![](https://blog.kakaocdn.net/dn/vJMna/btqDXtCHmkr/0S1cvTdL7Oe3pjJviJC6jk/img.png) 


### 추가
```c++
v.rbegin(); // reverse begin을 가리킴
v.rend(); // reverse end를 가리킴


v.resize(n); // 크기를 n으로 변경 (더 커지면 0으로 초기화)
v.resize(n,m); // 크기를 n으로 변경 (더 커지면 m으로 초기화)


v2.swap(v1); //v1과 v2를 swap


v.insert(iter,m); //iter 위치에 m을 삽입. 이후 해당 위치를 가리키는 iterator 반환 
v.insert(iter,k,m); //iter 위치부터 k개의 m을 삽입. 


v.erase(iter); //iter가 가리키는 원소 제거
v.erase(iter_start,iter_end); //start부터 end까지 원소 제거
```