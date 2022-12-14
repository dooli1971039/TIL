# Part 05. 코딩별 안내서 - 최신 기술 편

## Episode 40) REST API라니, 휴식 API인가? 이게 대체 뭐죠?

### REST API

-   REST(representational state transfer) : 설계 철학
-   REST 방식으로 설계한 API
-   URL에서 동사를 제외하고, HTTP의 메서드(GET, POST, PUT, DELETE)를 사용하여 나타냄  
    => URL 1개로 조회, 생성, 수정, 삭제가 모두 가능하다  
    +) GET 방식에서는 쿼리를 도입하여 추가 정보를 전할 수도 있다

## Episode 41) 도커가 뭐지? 왜 필요하까?

### 도커

-   개발 환경이 맞지 않는 상황 or 개발 환경이 변경되었을 때 유연하게 대처할 수 있게 해준다.
-   어떤 컴퓨터에서도 같은 개발 환경을 준비할 수 있게 해주는 도구이다.
-   도커가 준비한 프로그래밍 언어가 동작하는 환경을 **컨테이너**라고 함
-   도커 컨테이너는 각각 서로 분리되어 있고 독립적이라서, 컴퓨터 하나에 수많은 컨테이너가 있을 수 있다.

=> 도커는 원하는 개발 환경을 도커 파일에 저장하여 준비해 준다.  
=> 도커가 마련한 개발 환경은 컨테이너 형태로 존재하므로, 개발에 필요한 도구를 따로 모아 준비할 수도 있고 쉽게 복제할 수도 있다.

## Episode 42) 암호화폐의 진실

### 암호화폐

-   암호화폐는 정부가 없앨 수 없다.  
    => 비트코인은 네트워크라서, 없애려면 세상의 모든 인터넷을 꺼야함
-   현재는 거래소를 통제하는 간접적인 방식으로 규제 중
-   그러나 암호화폐를 산 이후에는, 정부가 구매자가 뭘 했는지를 알 수가 없다.
    -   어디에 보냈는지, 무엇을 샀는지 등을 알 수 없음  
        => 과세 불가능
-   암호화폐는 사용자를 은행으로 만든다
    -   스스로가 재산은 안전하게 보관해야 한다.
-   누군가 내 비트코인의 주소를 알면, 나에게 돈이 얼마가 있는지, 얼마를 받았고/송금했는지 등 모든 정보를 알아낼 수 있다.

### 사기 당하지 않으려면

1. 모든 코인에 가치가 있는 것은 아님
2. 암호화폐에 수식어를 남발하는 사람은 거르자
3. 코인, 커뮤니티 등 진짜 정보를 살피자
    - 코드 업데이트 날짜도 오래됐고, 커뮤니티에 글은 많이 올라오는데 가격 이야기만 한다면, 관심을 접자. 가격만 올려서 팔고 도망 가는 곳일 수도 있음
4. 탈중앙화된 코인인지 살펴라
    - 모든 코인이 탈중앙화인 것은 아니다.
    - 탈중앙화가 안 되어있으면, 그 코인을 만든 곳에서 언제든지 마음대로 할 수 있어 위험하다.
5. 이론상 안전한 코인을 골라라
    - stable 코인은 다른 자산에 코인 가치가 연결되어 있어서 가격 변동이 거의 없다는 특징이 있다.  
      (회사가 도망갈수도 있긴 하다)
    - DAI 코인은 역시 stable 코인인데, 보증 주체가 회사가 아닌 알고리즘이라 좀 더 안전하다.  
      (알고리즘도 다 믿을 수 있는 것은 아니다)

## Episode 43) 하이브리드...앱? 뭐라고요?

### 모바일

-   iOS : 아이폰, 아이패드
-   Android : 갤럭시 폰, 태블릿 등
-   IOS나 Android 운영체제 위에서 돌아가는 앱을 만드는 방식은 크게 3가지가 있다.
    -   하이브리드 방식
    -   크로스 플랫폼 방식
    -   네이티브 방식

### 1. 하이브리드 앱

-   하이브리드 앱은 사실 웹 사이트를 보여주는 웹 뷰를 말함  
    (웹 뷰 : 브라우저의 윈도우 부분 - 주소창을 제외한 나머지 부분)
-   HTML, CSS, JS로 개발한 웹 앱을 iOS에서도, Android에서도 쓸 수 있도록 hybrid로 만든 것
-   네이티브 앱 개발 지식이 필요 없다는 장점이 있다.
    -   HTML, CSS, JS만 알아도 충분히 개발할 수 있다.
-   UI를 하나하나 다 짜야하고, 스마트폰의 성능을 온전히 활용하지 못한다는 단점이 있다.  
    ex) 비디오 프로세싱
-   하이브리드 앱을 만들고 싶으면 Apache Cordova를 참고해보기

### 2. 크로스 플랫폼 앱

-   크로스 플랫폼 앱은 특정한 언어로 코딩하면 나중에 iOS, Android가 이해할 수 있는 코드로 변환해서 만든다.
    -   ex) React Native라는 기술로 코드를 짜면, 결국 JS코드로 변환된다. 이 변환된 코드를 iOS, Android가 자바스크립트 엔진으로 실행한다.
    -   ex) Flutter라는 기술도 있는데, 이는 Dart라는 언어로 코딩을 한다. 이 코드는 결국 C나 C++언어로 변환되는데, C나 C++은 iOS, Android에서 이해할 수 있다.
-   크로스 플랫폼 앱 개발에서 가장 중요한 특징은, 어떤 언어로 코드를 작성하면 그 코드가 나중에 네이티브 코드로 변환된다는 것이다.
-   개발자가 익숙한 코드로 한 번만 작성해도 된다는 장점이 있다. (iOs, Android 따로 작성할 필요 X)
-   다양한 형태의 라이브러리, 튜토리얼 등이 생겨 커뮤니티가 발전한다는 장점이 있다.
-   네이티브 앱의 성능은 아직 따라갈 수 없다. (네이티브 언어로 변환하는 과정이 있기 때문)

### 3. 네이티브 앱

-   iOS만을 위한, Android만을 위한 언어로 코드를 작성하여 개발한 것
-   iOS는 swift, Android는 코틀린으로 개발을 한다.
-   스마트폰의 성능을 최대로 사용할 수 있다는 장점이 있다.
-   다른 앱에 비해 배우는 시간이 필요하고, iOS,Android 따로 개발,유지,보수를 해야한다.

## Episode 44) NFT가 도대체 뭐길래?

### NFT

-   non fungible token의 줄임말로 **대체 불가능한 토큰**을 말한다.
    -   대체 가능: 내가 1달러, 친구도 1달려 => 교환 가능
    -   대체 불가능: 한정판 신발, 땅, 전세 계약 등..
    -   토큰: 이더리움과 같은 블록체인으로 발행
        -   핵심 기능 2가지: 돈을 받는 기능, 돈을 받고 토큰을 보내주는 기능 => 합쳐서 **스마트 계약**이라 함
        -   토큰을 잘 활용한 예가 바로 코인
        -   코인은 여러개 발행할 수 있어 화폐로 쓰임
        -   내가 토큰을 딱 1개만 발행할 수 있도록 스마트 계약을 만들고, 이미지, 영상, 노래, 전세 계약 등을 넣으면 NFT 탄생

## Episode 45) 멀웨어, 바이러스, 웜 개념 몽땅 정리

### 멀웨어

-   malware = malicious(악의 있는) + software
-   컴퓨터를 감시하거나 파괴하는 악성 소프트웨어
-   바이러스(virus)와 웜(worm) 모두 복제되어 전파된다.

### 바이러스

-   숙주 필요  
    ex) 어떤 파일을 내려받아서 열 때 감염됨 => 파일이 숙주, 바이러스는 파일 안에

### 웜

-   웜은 숙주가 필요 없이, 자기 자신을 복제하면서 전파된다.
-   웜은 미사일과 페이로드가 필요하다.
    -   미사일을 통해 컴퓨터 안에 침투
    -   페이로드를 배포하는 방식으로 컴퓨터를 파괴
-   감염된 컴퓨터에 있다가, USB와 같은 저장 장치와 연결되면 이것을 타고 다른 컴퓨터에 침투함.  
    웜은 암호화된 상태로 우리의 컴퓨터에 도착해서, 스스로 암호를 해제하고 웜을 만들었던 본부에 연락함
-   웜은 컴퓨터의 루트에 설치된다. **컴퓨터의 모든 자원을 관리하는 OS 바로 옆**이다.  
    => 웜이 백신 프로그램보다 우위에 있다는 것

### 제로 데이(zero day)

-   아직 아무도 발견하지 못한 프로그램의 취약점을 뜻함  
    (발견하는 날까지는 고칠 수 있는 시간이 아예 없는 거나 마찬가지라서 zero day임)
