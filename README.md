## gradlew 실행하기 (IDE 실행 시 자원이 너무 많이 사용됨)
1. gradle/wrapper/gradle-wrapper.properties 파일을 열어서 환경변수 설정 e.g. DB_URL=..., DB_ID=...
```bash
./gradlew bootRun
```

# 모듈 버전
- Java 17
- 스프링부트 3.3.2
- gradle 8.8

# bootRun 실행 시 args 설정
catalog-service: ./gradlew bootRun --args='--server.port=8080'