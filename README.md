## gradlew 실행하기 (IDE 실행 시 자원이 너무 많이 사용됨)
1. gradle/wrapper/gradle-wrapper.properties 파일을 열어서 환경변수 설정 e.g. DB_URL=..., DB_ID=...
```bash
./gradlew bootRun
```

# 모듈 버전
- Java 17
- 스프링부트 3.3.2
- gradle 8.8

# spring cloud gateway의 security 설정
- spring cloud gateway는 기본적으로 security 설정이 되어있지 않음
- 단, authFilter를 이용하여 토큰 검증을 함

# bootRun 실행 시 args 설정
catalog-service: ./gradlew bootRun --args='--server.port=8080'

# Gateway filter 순서
- 글로벌 필터
- 커스텀 필터
- 로깅필터(옵션으로 글로벌보다 먼저 실행시킬 수 있음, 옵션=HIGHEST_PRECEDENCE)
- 메인로직 실행

# ddl-auto, generate-ddl 차이
# 방언 설정안해도 잘되는데 이것도 확인해보기
# logging:level:com....으로 해주는게 좋은지 root로 해주는게 좋은지 (둘차이가없는거같은데)
# init sql 설정(DDL 및 테스트 데이터 insert) -> 여기서 ddl하는게 좋은거같음 (안됫엇는데...)

# 질문
- 스프링 클라우드 게이트웨이 uri를 보면 http://localhost:8080/ 이런식으로 되어있는데 이 경우는
서비스 디스커버리 유레카를 사용안햇을경우고 사용하면 lb://USER-SERVICE 이런식으로 사용함, 이걸 k8s에 마이그레이션한다고했을때 lb://USER-SERVICE 이런식으로 사용했을때
k8s service 오브젝트 이름을 잘 찾아가는지... 아니면 못찾아가는지.. 기본적으로 (유레카 서비스는 안넣는게 좋은거같음 강사도 chatGPT도 그렇게 말함) 강의 확인해보기
>> 그냥 서비스 주소 사용하면됨 <...>.local.cluster
> 

- 스프링 클라우드 게이트웨이에서 시큐리티를 추가할 수 없나?