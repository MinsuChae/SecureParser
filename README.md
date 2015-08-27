SecureParser
======
SecureParser 는 자바 언어로 구현하였고, secure 로그 파일에서 접속을 지속적으로 실패하는 자(Blacklist)와, 성공한 자(Whitelist)를 파싱하여 저장합니다.<br/>
Secure2Iptables 는 자바 언어로 구현하였고, 사용자로부터 Secure 파일들을 읽어서 Blacklist 와 Whitelist 를 GUI 상으로 표현합니다.<br/>
SecureParser.jar 는 Secure2Iptables 의 실행파일로써 jre8 이 있으면 정상실행됩니다.

기능 
-------
   * SecureParser 는 클래스 생성 시 바로 스레드가 동작하여 파싱을 시작한다.
   * 처리 속도를 위해 정규 표현식대신 문자열을 인덱싱하여 처리합니다.
   * 스레드로 파싱이 완료되기 전까진 getBlacklist 와 getWhitelist 메소드는 블락킹된다.
   * SecureParser.jar 의 경우 Blacklist 를 Iptables 에서 DROP 하는 구문을 자동추가한다.
   * 또한 무조건적 DROP 시 서버의 접상적인 사용자도 DROP 될 수 있어 Whitelist 의 ip 보고 지울 수 있도록 되어있다.

   
필요사항
------
   * 개발을 JDK8에서 했으나 JDK 버전을 높게 요구하지는 않는다.
   * SecureParser.jar 의 경우 실행시 JRE8 가 필요로 하다.
   
SecureParser.jar 사용법
------
   1. Secure 로그 파일들을 선택한다.(로그 파일 선택 완료시 취소 클릭)
   2. Blacklist 에 있는 것중 Whitelist에도 있는 경우 사용자가 수동으로 필요시 Blacklist 에 있는 것을 삭제한다.
   3. Iptables 파일을 편집하여 적용시킨다.
   4. Iptables 를 재실행시킨다.