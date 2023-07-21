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

## 선언

```c++
// 기본 선언 방식 (<key 타입, value타입>)
map<string, int> m1;
map<int, int> m2;

// 내림차순으로 정렬하고 싶다면 greater 추가
map <int, int, greater> map1;
```

처음에 초기화를 하지 않았다면, 0으로 들어가게 된다.  
(바로 `m1["caty"]++;` 이런 코드가 가능함)

<br/>

## 함수

### 기본 함수

1. 데이터 삽입(insert)

```c++
m1.insert({"Cam", 300});
m1.insert(make_pair("a", 1));
m1["dodo"]=30;
m2.insert(pair<int, int>(10, 20));
```

2. 데이터 삭제 (erase)  
   키 값을 기준으로 삭제할 수도 있고, 주소를 기준으로 삭제할 수도 있다.

```c++
m.erase("Alice");
m.erase(m.find("c"));
m.erase(m.begin()+2);
```

3. 모든 요소 삭제

```c++
m.erase(m.begin(), m.end());
m.clear();
```

4. map이 비어있는지 아닌지 알고 싶을 때

```c++
m.size(); //원소 개수 반환
m.empty(); //비어있으면 true 아니면 false 반환
```

5. map에 원소가 있는지 아닌지 알고 싶을 때

```c++
m.find("Alice"); //key에 해당하는 iterator를 반환 (없으면 m.end()와 동일)
m.count("Alice"); //key에 해당하는 원소의 개수를 반환
```

map에 특정 데이터가 있는지만을 확인하고 싶다면, find()보다는 count()가 더 유연하다.
<br/>

### 접근

```c++
// 방법 1
for (auto iter = m.begin() ; iter !=  m.end(); iter++){
	cout << iter->first << " " << iter->second << endl;
}
cout << endl;

// 방법 2
for (auto iter : m) {
	cout << iter.first << " " << iter.second << endl;
}
```

```c++
/*
* @see https://www.acmicpc.net/problem/1713 후보 추천하기
*/
bool cmp(const pair<int, pair<int, int>> &c1, const pair<int, pair<int, int>> &c2) {
    if (c1.second.first == c2.second.first) { // 추천횟수가 같으면
        return c1.second.second < c2.second.second;
    }
    return c1.second.first < c2.second.first; // c1 < c2  (오름차순)
}

int main() {
    int n, stu; // 사진틀, 학생 수
    cin >> n >> stu;

    map<int, pair<int, int>> mp; // 학생 번호, 추천 횟수

    for (int i = 0; i < stu; i++) {
        int num;
        cin >> num;

        if (mp.size() == n && mp.count(num) == 0) {
            // 삭제
            mp.erase(min_element(mp.begin(), mp.end(), cmp));
        }

        if (mp.count(num) == 0) { // 새로 추가
            mp[num].second = i;
        }

        mp[num].first++; //////////////////// 바로 ++ 가능. (초기화가 기본적으로 0으로 된다.)
    }

    for (auto m : mp) {
        cout << m.first << " ";
    }
}
```
