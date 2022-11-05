// //컨텐츠 페이지의 모든 텍스트를 가져온다.그리고 그 결과를 bodyText변수를 담는다.
// var bodyText= document.querySelector("body").innerText;
// alert(bodyText);
// //=> 이렇게 하면 에러난다. 팝업 페이지를 참고하기 때문

//컨텐츠 페이지를 대상으로 코드를 실행하려면
//chrome 확장 기능만을 위해서 만들어진 객체 chrome을 사용한다
let aaaa=3;
function bbody(){
    var bodyText= document.querySelector("body").innerText; 

    // var bodyNum = bodyText.split(' ').length;
    // //bodyText에서 자신이 알고 있는 단어(the)가 몇번 등장하는지를 알아본다. 그 결과를 myNum이라는 변수에 담는다.
    // var myNum = bodyText.match(new RegExp('\\b(the|is|was|of)\\b', 'gi')).length;
    aaaa=4;
    tabs.location.reload();
    document.querySelector("#result").innerText=aaaa;
}


chrome.tabs.query({ active: true, currentWindow: true }, 
function(tabs) {
    chrome.scripting.executeScript({
      target: {tabId: tabs[0].id},
      function: bbody,
    });
});

