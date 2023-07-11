# BFS (너비 우선 탐색)

루트 노드에서 시작해서 인접한 노드를 먼저 탐색하는 방법이다.  
따라서 시작 정점으로부터 가까운 정점을 먼저 방문하고 멀리 떨어져 있는 정점을 나중에 방문하는 순회하게 된다.
두 노드 사이의 최단 경로 or 임의의 경로를 찾고 싶을 때 주로 사용한다.  
BFS는 DFS와 달리 재귀적으로 동작하지 않으며, **Queue를 통해 구현**하는게 일반적이다.

## BFS 기본 동작

1. 시작하는 노드를 큐에 넣고 방문했다는 표시를 남김
2. 큐에서 노드를 꺼내어 그 노드에 연결된 노드에 대해 3번을 진행
3. 해당 노드를 이전에 방문했다면 아무것도 하지 않고, 처음으로 방문했다면 방문했다는 표시를 남기고 해당 노드를 큐에 삽입
4. 큐가 빌 때까지 2번을 반복
   => 모든 노드가 큐에 1번씩 들어가므로 시간복잡도는 칸이 N개일 때 O(N)이다.

```c++
/*
 * @see https://www.acmicpc.net/problem/1260
 */
vector<int> bfs(int n, int v, vector<vector<bool>> &edge) {
    vector<int> result;
    vector<bool> visited (n+1, false);
    queue<int> q;

    //처음에 반드시 먼저 집어넣고, 방문표시를 함
    q.push(v);
    visited[v] = true;

    while(!q.empty()) {
        int node = q.front();
        q.pop();
        result.push_back(node);

        for(int i = 1; i <= n; i++) {
            //연결되는 노드에서 방문하지 않은 것을 찾음
            if(edge[node][i] && !visited[i]) {
                q.push(i); // 큐에 넣고, 방문체크를 함
                visited[i] = true;
            }
        }
    }
    return result;
}
```

```c++
/*
 * @see https://www.acmicpc.net/problem/1926
 */
#include <algorithm>
#include <iostream>
#include <queue>
using namespace std;

int arr[505][505];      // 입력받은 부분
bool visited[505][505]; // 방문한 곳
int n, m;
int dr[4] = {-1, 1, 0, 0};
int dc[4] = {0, 0, -1, 1};

int bfs(int i, int j) {
    queue<pair<int, int>> q;
    q.push({i, j});
    visited[i][j] = true; // 색칠하기
    int num = 1;

    while (!q.empty()) {
        pair<int, int> p = q.front();
        q.pop();

        for (int i = 0; i < 4; i++) {
            int nr = p.first + dr[i];
            int nc = p.second + dc[i];

            if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 1 && visited[nr][nc] == false) {
                q.push({nr, nc});
                visited[nr][nc] = true;
                num++;
            }
        }
    }

    return num;
}

int main() {
    cin >> n >> m;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            cin >> arr[i][j];
        }
    }

    int cnt = 0;
    int maxArea = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (visited[i][j] == false && arr[i][j] == 1) {
                maxArea = max(bfs(i, j), maxArea);
                cnt++;
            }
        }
    }

    cout << cnt << "\n" << maxArea;
}
```
