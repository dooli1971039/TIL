# DP (다이나믹 프로그래밍)

여러개의 하위 문제를 먼저 푼 후, 그 결과를 쌓아올려 주어진 문제를 해결하는 알고리즘

## DP 기본 풀이법

1. 테이블 정의하기
2. 점화식 찾기
3. 초기값 정의하기

```
ex) BOJ 1463: https://www.acmicpc.net/problem/1463
1. 테이블 정의하기
    dp[i]=i를 1로 만들기 위해 필요한 사용 횟수의 최솟값
2. 점화식 찾기
    dp[12] = ?
    3으로 나누거나: dp[12]=dp[4]+1
    2로 나누거나: dp[12]=dp[6]+1
    1을 빼거나: dp[12]=dp[11]+1
    => dp[12]=min(dp[4],dp[6],dp[11])+1

    ==> 위 예시를 dp[k]를 식으로 정의하면 된다.

3. 초기값 정의하기
```

### Top-down

```c++
int fibonacci(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;

    if (dp[n] != -1) return dp[n];

    dp[n] = fibonacci(n - 1) + fibonacci(n - 2);
    return dp[n];
}
```

### Bottom-up

```js
int fibonacci(int n){
    dp[0] = 0, dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
}
```
