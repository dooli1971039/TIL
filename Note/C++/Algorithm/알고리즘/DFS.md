# DFS (깊이 우선 탐색)

다차원 배열에서 각 칸을 방문할 때 깊이를 우선으로 방문하는 알고리즘이다.

## DFS 기본 동작

1. 시작하는 칸을 스택에 넣고 방문했다는 표시를 남김
2. 스택에서 원소를 꺼내어 그 칸과 상하좌우로 인접한 칸에 대해 3번을 진행
3. 해당 칸을 이전에 방문했다면 아무 것도 하지 않고, 처음 방문했다면 방문했다는 표시를 남기고 해당 칸을 스택에 삽입
4. 스택이 빌 때까지 2번을 반복
   => 모든 칸이 스택에 1번씩 들어가므로 시간복잡도는 칸이 N개일 때 O(N)이다.

5. 재귀

```c++
/*
 * @see https://www.acmicpc.net/problem/1260
 */
vector<bool> visited_recur;
void dfsRecur(int n, int node, vector<vector<bool>> &edge) {
    visited_recur[node] = true; //가장 먼저 방문 체크를 한다.
    cout << node << ' ';

    for(int i = 1; i <= n; i++) {
        //연결되는 노드에서 방문하지 않은 것을 찾음
        if(edge[node][i] && !visited_recur[i]) {
            dfsRecur(n, i, edge);
        }
    }
}
```

```c++
int dr[4] = {-1, 1, 0, 0};
int dc[4] = {0, 0, 1, -1};

bool arr[50][50];
bool visited[50][50];

void dfs(int r, int c) {
    visited[r][c] = true; // 가장 먼저 방문 체크

    for (int i = 0; i < 4; i++) {
        int nr = dr[i] + r;
        int nc = dc[i] + c;

        if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] && !visited[nr][nc]) {
            dfs(nr, nc);
        }
    }
}
```

2. 반복문

```c++
/*
 * @see https://www.acmicpc.net/problem/1260
 */
vector<int> dfs(int n, int v, vector<vector<bool>> &edge) {
    vector<int> result;
    vector<bool> visited (n+1, false);
    stack<int> s;

    //처음에 반드시 먼저 집어넣고, 방문표시를 함
    s.push(v);
    visited[v] = true;
    result.push_back(v);

    while(!s.empty()) {
        int node = s.top();
        bool child = false;

        for(int i = 1; i <= n; i++) {
            //스택의 가장 위의 노드와 연결되어 있고, 아직 방문 전일 때
            if(edge[node][i] && !visited[i]) {
                child = true;
                s.push(i);
                visited[i] = true;
                result.push_back(i);
                break;
            }
        }
        if(!child) {
            s.pop();
        }
    }
    return result;
}
```
