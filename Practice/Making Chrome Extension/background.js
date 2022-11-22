/**
* ---------------------------------------------------------------------------------
* | 백그라운드 |
- 백그라운드에서 브라우저 이벤트를 모니터링할 수 있습니다 . 
- 서비스 작업자는 이벤트를 처리하기 위해 로드되고 더 이상 필요하지 않을 때 종료되는 특수 JavaScript 환경입니다.
* ---------------------------------------------------------------------------------
* - 디폴트 컬러를 지정하여 스토리지 API 를 호출하여 지정한 색을 저장시킵니다.
**/
let color = '#3aa757';

chrome.runtime.onInstalled.addListener(() => {
  chrome.storage.sync.set({ color });
  console.log('기본 배경색은 %cgreen', `color: ${color}`);
});
//This method allows the extension to set an initial state or complete some tasks on installation.