# Union-Find (유니온 파인드)

서로 다른 두 원소가 같은 집합에 있는지 판별하는 알고리즘

-   Find 연산과 Union 연산으로 이루어짐
    -   Find: 각 원소가 **속한 집합을 판별**하는 연산
    -   Union: 서로 다른 집합에 속한 두 원소를 **같은 집합에 속하도록 합치는** 연산
-   분리 집합(Disjoint-set)으로 불리기도 함
-   시간 복잡도가 O(1)에 가까운 아주 빠른 알고리즘이다.

```c++
// 백준 1717 - 집합의 표현
#include <iostream>
#include <vector>
using namespace std;

vector<int> parent;

int findParent(int node) {
    if (parent[node] < 0) {
        return node; // 루트 정점
    }

    return parent[node] = findParent(parent[node]); // 그래프 압축하여 루트 정점 찾기
}

void unionInput(int x, int y) {
    int nodeX = findParent(x);
    int nodeY = findParent(y);

    if (nodeX == nodeY) { // 이미 같은 집합
        return;
    }

    if (parent[nodeX] < parent[nodeY]) { // nodeX 쪽 집합이 더 큰 경우
        parent[nodeX] += parent[nodeY];
        parent[nodeY] = nodeX;
    } else {
        parent[nodeY] += parent[nodeX];
        parent[nodeX] = nodeY;
    }
    // 위와 같이 갱신을 하면, find를 한 번 거쳐야 루트 정점이 갱신된다.
}

int main() {
    ios_base::sync_with_stdio(0);
    cin.tie(0);
    cout.tie(0);

    int n, m;
    cin >> n >> m;
    parent.assign(n + 1, -1); // 1~n

    int cmd, a, b;
    while (m--) {
        cin >> cmd >> a >> b;

        if (cmd == 0) { // union 연산
            unionInput(a, b);
        } else { // 같은 집합에 포함되어있는지
            if (findParent(a) == findParent(b))
                cout << "YES\n";
            else
                cout << "NO\n";
        }
    }

    return 0;
}
```
