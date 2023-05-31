# function
c++로 코딩테스트를 볼 때 유용하게 쓰이는 함수를 정리

## algorithm
### 헤더
```c++
#include <algorithm>
```

### 메서드
- **find(시작 주소, 끝 주소, num)**
```c++
// 범위 내에서 num을 찾는다.
find(v.begin(), v.end(), num); 
```
시작 주소부터 끝 주소 사이에서 값을 찾는다.  
값이 있으면 해당 값이 있는 주소를, 없다면 종료 주소(**v.end()**)를 반환한다.  
+) #include <string>의 find()와는 다르다.

- **reverse(시작 주소, 끝 주소)**
```c++
// 객체의 순서를 거꾸로 한다.
reverse(v.begin(),v.end());
```
객체의 순서를 거꾸로 한다. return 값은 없다.