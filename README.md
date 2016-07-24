# ninjaproj
sw maestro kimdongwoo project1
안녕하세요 Ninjaproj입니다.
===================

Documents
-------------

https://www.dropbox.com/s/yvs3w5ykdejqlo9/%EA%B3%BC%EC%A0%9C1.hwp?dl=0 
슼스클스크린스크린샷스크린샷을 퐇포함포함한 봋보충보충섦보충설명 잘자룡자료이자료입닏자료입니다.
필수사항

 1.앱(게임)과 서버 통시하는 로직이 포함되어야함. 

ninjapro\src\main\java\AssistClass\ConnectDB.java
HttpSaveData(), HttpLoadData() 메소드통해서 웹서버와 통신 게임의 골드획득, 스코어, 접속시간, 종료시간 저장 및 로드

 2.클라이언트 게임은 게임첫 설치 시간. 접속 시간. 접속후 게임종료시간. 마지막 플레이시간등이 DB에 저장되어야 함.

ninjapro\src\main\java\AssistClass\SQLiteDBManager.java
로컬 DB를 생성하고 초기 DB 생성시 설치시간 저장, 첫 Activity가 실행될때 접속시간, 마지막 Activity가 종료될때 게임종료시간 DB에 저장

 3.페이스북, 트위터, 이메일등 타 서비스 연계가 1개이상 되어야 함.

ninjapro\src\main\java\k.com.ninjabeta\FaceBookLoginActivity.java
페이스북의 LoginManager통해서 페이스북으로 로그인

 4.앱스토어에 업로드 되어야함.

https://play.google.com/store/apps/details?id=k.com.ninjabeta 업로드 하였습니다.

 5.서버 프로그램이 클라우드나 호스팅이 되는 서버에 배포가 되어야함.

닷홈 웹호스팅 서버에 업로드됨 

 6.데이터는 DB에 저장되어 SQL을 통해서 조회 및 추가가 가능해야함.

ninjapro\src\main\java\AssistClass\SQLiteDBManager.java
update~time() 메소드 통해서 로컬DB 시간정보 입력가능
getResult() 메소드 통해서 로컬 DB정보 조회 가능

 7.백엔드는 BaaS나 클라우드 제한 없으나 웹서비스 형태이어야 함.

웹호스팅 사용
	
>가점 사항

>1. 클라우드 이용시 가점.

>2. 친구추가, 랭킹, 선물하기등의 기능 추가시 가점.

>3. Restful API 형태 설계 적용시 가점.

>4. push 구현 되었으면 가점.

>5. 안드로이드와 아이폰 둘다 대응시 가점.

>6. DB와 연동되는 별도의 CMS(관리시스템)을 구축시 가점.
