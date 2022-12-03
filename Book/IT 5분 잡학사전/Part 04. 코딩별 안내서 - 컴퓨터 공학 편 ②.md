# Part 04. 코딩별 안내서 - 컴퓨터 공학 편 ②

## Episode 31) 데이터와 단짝 친구, SQL
### SQL
- structured query language 
- **데이터베이스** 에 어떤 **질문/문의** 를 하기 위해 어떤 **구조** 를 가진 **언어**
- 데이터베이스는 데이터를 보관하는 역할만 하고, 데이터를 직접 정리하거나 처리하는 것은 DBMS(database management system, 데이터베이스 관리 시스템)이 한다.  
=> SQL로 데이터베이스와 상호작용을 하려면 DBMS를 거쳐야 한다.  
=> **SQL은 데이터베이스를 관리하는 DBMS와 대화하기 위한 언어**
- DBMS 예시 : MySQL, PostgreSQL, SQLite, Oracle, MariaDB 등..
- 개발자들이 ORM(object relational mapping)에 익숙해서 SQL을 소홀히하는 경향이 있다.

## Episode 32) NoSQL이 뭐죠?
### NoSQL
- SQL과 성질이 다르다.
- document DB, key-value DB, graph DB 등 NoSQL의 데이터베이스 종류는 다양하다

1. document DB - MongoDB
- 데이터를 JSON 형태로 저장 (SQL의 데이터베이스는 데이터를 행과 열의 개념으로 저장)
    - JSON은 대괄호와 중괄호로만 구성하면 되고, 데이터마다 구성이 같지 않아도 된다.
    - 표의 형태인 SQL보다 다양한 종류의 데이터를 저장할 수 있다.

2. key-value DB - CassandraDB
- column wide : 한 행의 열이 엄청 넓은 데이터베이스
- 수만 개의 데이터를 1초 만에 순식간에 쓸 수 있을 정도로 속도가 빠르다.  
=> 대용량의 데이터를 빠르게 저장하거나 읽어야 할 때 좋다.
- ex) 애플, 넷플릭스, 인스타그램, 우버..

3. key-value DB - DynamoDB
- 아마존이 만듬
- 데이터를 1초에 24000개를 읽을 수 있음
- ex) 듀오링고(언어 학습 앱)

4. graph DB
- 열이나 도큐먼트가 필요하진 않는 대신, 노드라는 개념이 필요하다.(관계망)

## Episode 34) 버전을 표기하는 방법도 있어요?
### SemVer
- 시맨틱 버저닝(semantic versioning specification, SemVer)
- 숫자 3개로 표현하는 방식 (ex. 16.8.1)
- 첫 번째 숫자는 프로그램에 엄청나게 큰 변화가 있을 때 바뀜 - 전반적으로 다 갈아 엎은 경우(새 버전에 맞게 코드 업데이트 필요)
- 두 번째 숫자는 마이너한 업데이트를 의미함 - 새 기능을 살짝 추가
- 마지막 숫자는 패치나 버그 수정을 의미함

## Episode 35) 비밀번호는 어떻게 저장될까?
