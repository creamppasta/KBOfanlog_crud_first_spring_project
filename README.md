**FanLog 프로젝트 정리**
이 프로젝트는 Spring Boot와 JPA를 사용해서 간단한 CRUD API를 직접 만들어보면서
스프링 백엔드의 기본 흐름을 이해하기 위한 개인 학습용 프로젝트입니다.
Node.js로 CRUD를 해본 경험은 있었지만,
Spring에서는 요청 처리, 트랜잭션, JPA 동작 방식이 어떻게 이어지는지
말로 설명할 수 있을 정도로 이해하는 것을 목표로 했습니다.

**사용한 기술**
Java 17
Spring Boot
Spring Data JPA (Hibernate)
H2 Database (in-memory)

**이 프로젝트에서 해본 것**
FanLog라는 간단한 도메인을 기준으로 생성, 조회, 수정, 삭제 API를 구현했습니다.
로직이 복잡해질수록 코드가 섞이는 것을 피하고 싶어서 Controller / Service / Repository 구조를 사용했습니다. 
각 레이어가 어떤 역할을 가지는지 명확히 구분하는 데 집중했습니다.
**Controller**
	• HTTP 요청을 받는 역할
	• JSON을 DTO로 변환하고 검증
	• 실제 로직은 Service에 위임
**Service**
	• 비즈니스 로직과 유스케이스 단위 처리
	• 트랜잭션 경계(@Transactional)를 담당
	• 수정 시 JPA Dirty Checking을 이용해 save 없이 업데이트
**Repository**
	• JpaRepository를 상속해서 DB 접근 담당
	• save, findById, delete 등의 구현은 Spring Data JPA가 런타임에 프록시로 제공

**JPA 관련해서 이해한 핵심 포인트**
update 메서드에서 save를 호출하지 않아도 UPDATE 쿼리가 실행되는 이유는
트랜잭션 안에서 조회한 엔티티가 영속 상태이기 때문입니다.
Service 메서드에 @Transactional이 붙어 있고,
findById로 가져온 엔티티의 값을 변경하면
트랜잭션이 끝나는 시점에 JPA가 변경을 감지해서 자동으로 UPDATE를 실행합니다.
이 과정을 통해
“엔티티 상태 변화 중심으로 로직을 작성한다”는 JPA 스타일을 직접 경험했습니다.

**DTO를 사용한 이유**
Entity는 DB에 저장되는 내부 모델이고,
요청/응답은 외부와의 API 계약이기 때문에 분리해서 관리했습니다.
요청 DTO에서는 입력값 검증을 담당하고,
응답 DTO에서는 필요한 값만 선택해서 반환하도록 했습니다.
이렇게 분리하면
Entity 구조가 바뀌어도 API 응답에 영향을 덜 주게 됩니다.

**예외 처리**
존재하지 않는 FanLog를 조회하거나 수정하려는 경우
Service에서 FanLogNotFoundException을 던지도록 했습니다.
이 예외는 Controller에서 직접 처리하지 않고
@RestControllerAdvice를 사용한 전역 예외 처리기에서 404 응답으로 변환했습니다.
이를 통해
로직 처리와 HTTP 응답 처리를 분리할 수 있었습니다.

**테스트 방법**
PowerShell에서 Invoke-RestMethod를 사용해 API를 테스트했습니다.
H2 in-memory DB를 사용하기 때문에
서버를 재시작하면 데이터가 초기화됩니다.
이 점을 감안해서
POST → GET → PUT → DELETE 순서로 테스트했습니다.
PowerShell로 직접 요청을 보내보면서 HTTP 요청이 서버에 들어오는 흐름을 눈으로 확인할 수 있었습니다.

**이 프로젝트를 통해 얻은 것**
	• Spring Boot에서 HTTP 요청이 처리되는 전체 흐름 이해
	• DI, Repository 프록시, JPA Dirty Checking 개념 체득
	• Controller / Service / Repository 구조를 말로 설명할 수 있게 됨
아직 구조적으로 단순한 프로젝트이지만,
Spring 백엔드의 기본 개념을 정리하는 데 큰 도움이 됐습니다.
