# function
c++로 코딩테스트를 볼 때 유용하게 쓰이는 함수를 정리

## algorithm
### 헤더
```c++
#include <algorithm>
```
<br/>

### 메서드
- **sort(시작 주소,끝 주소)**
```c++
// 오름차순 정렬
sort(arr, arr+n);
sort(v.begin(),v.end());

// 내림차순 정렬
sort(v.begin(),v.end(), greater<자료형>());
```
시작 주소 ~ (끝 주소 -1) 범위에서 정렬을 진행한다. (원본을 바꾼다)   
**퀵 정렬**을 기반으로 하기 때문에, 평균 시간 복잡도는 **n log(n)**이다.  
3번째 인자를 사용하여 정렬 기준을 바꿀 수 있다.

<br/>

- **find(시작 주소, 끝 주소, num)**
```c++
// 범위 내에서 num을 찾는다.
find(v.begin(), v.end(), num); 
```
시작 주소부터 끝 주소 사이에서 값을 찾는다.  
값이 있으면 해당 값이 있는 주소를, 없다면 종료 주소(**v.end()**)를 반환한다.  
+) #include <string>의 find()와는 다르다.

<br/>

- **reverse(시작 주소, 끝 주소)**
```c++
// 객체의 순서를 거꾸로 한다.
reverse(v.begin(),v.end());
```
객체의 순서를 거꾸로 한다. return 값은 없다.

<br/>

- **min_element(시작 주소, 끝 주소) / max_element(시작 주소, 끝 주소)**
```c++
// 최소값 찾아서 주소값 반환
min_element(v.begin(),v.end());
// 최소값 찾아서 반환
*min_element(v.begin(),v.end());


// 최대값 찾아서 주소값 반환
max_element(v.begin(),v.end());
// 최대값 찾아서 반환
*max_element(v.begin(),v.end());
```
범위 내에서 최소값/최대값을 반환한다.  
기본적으로 주소를 반환하기 때문에, 값을 구하고 싶으면 <b>*</b>를 붙여야 한다.  

<br/>

- **unique(시작 주소, 끝 주소)**
```c++
// 연속된 중복 원소를 vector의 제일 뒷부분으로 쓰레기값으로 보낸다.
unique(v.begin(),v.end());

// 그래서 아래와 같이 erase를 써서 뒷 부분을 지워줘야 한다.
v.erase(unique(v.begin(),v.end()),v.end());
```
sort를 먼저 사용하고 unique를 사용해야 한다. (연속된 중복 값을 처리하기 때문)  
unique는 중복을 제거하고 난 후의 끝 주소를 return한다.  
그래서 erase와 함께 쓰면 중복을 제거할 수 있다.  

<br/>