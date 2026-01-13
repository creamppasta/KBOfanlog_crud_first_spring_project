package com.example.kbofanlog.domain.fanlog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * ✅ Entity 역할: "DB에 저장될 데이터 구조 + 도메인 상태"
 *
 * - Entity는 "API 요청/응답 스펙"이 아니다
 * - Entity는 DB 테이블 구조와 연결되고, JPA가 관리(추적)하는 대상이다
 *
 * 📌 면접용 한 줄:
 * "Entity는 내부 도메인 모델로 유지하고, 외부 요청/응답은 DTO로 분리했습니다."
 */
@Entity
public class FanLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ✅ IDENTITY: DB가 PK를 auto increment로 생성해 줌(1,2,3,...)
    private Long id;

    private Long gameId;   // 어떤 경기인지
    private int inning;    // 몇 회인지
    private String memo;   // 메모 내용

    /**
     * ✅ JPA는 기본 생성자가 필요하다.
     * - 접근 제어자는 public보단 protected 권장 -> public은 아무나 접근 가능, private는 jpa가 접근 불가능, jpa가 접근 가능하면서
     * 불필요한 외부 접근 막으려면 protected가 제격
     * - 이유: JPA가 리플렉션으로 객체 생성할 때 사용
     * jpa는 우리가 만든 엔티티 클래스를 기반으로 가짜 객체 '프록시'를 만듦, 이 때 '리플렉션'이라는 기술을 사용하는데
     * 리플렉션이 작동하려면 '아무런 인자가 없는 기본 생성자'가 필요함
     */
    protected FanLog() {
    }

    /**
     * ✅ 우리가 직접 쓰는 생성자
     * - FanLog가 "의미 있으려면" 필요한 값(필수값)을 강제할 수 있다.
     */
    public FanLog(Long gameId, int inning, String memo) {
        this.gameId = gameId;
        this.inning = inning;
        this.memo = memo;
    }

    // ✅ getter만 제공: 외부에서 함부로 값 변경 못 하게(캡슐화) -> getter: 내부의 변수 값을 외부로 읽어가는 통로
    // 이름 규칙: get + 변수이름 (예: getName(), getAge()), 특징: 값을 전달만 할 뿐, 원래 값을 수정하지 않습니다.
    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public int getInning() {
        return inning;
    }

    public String getMemo() {
        return memo;
    }

    /**
     * ✅ "수정"은 setter 남발 대신 도메인 메서드로 의도를 드러낸다.
     * - 어떤 변경이 허용되는지, 나중에 규칙을 추가하기 쉬움
     * - (예: inning은 1~12만 가능 같은 규칙을 여기서 체크 가능)
     */
    public void update(Long gameId, int inning, String memo) {
        this.gameId = gameId;
        this.inning = inning;
        this.memo = memo;
    }
    /** 수정(update)되는 과정 */
    // 영속 상태: 엔티티가 jpa의 관리를 받는 상태
    // 엔티티를 처음 읽어올 때 jpa는 처음 상태를 '스냅샷'으로 찍어 둠
    // find()로 DB에서 데이터를 가져오면 해당 객체는 영속 상태가 됩니다. (JPA 매니저가 감시 시작)
    // update() 메서드를 호출해서 객체 내부 값 변경
    // 트랜잭션이 커밋되면 스냅샷이랑 현재 객체랑 비교
    // 바뀐게 있으면 jpa가 알아서 UPDATE SQL 생성해서 DB에 날림
}
