// “HTTP 요청(JSON)”이랑 “DB 구조(Entity)”는 서로 목적이 완전히 다르기 때문에 절대 바로 연결하면 안 된다.
// 즉 Entitiy는 외부 입력을 절대 직접 받지 않는다.
// 그래서 DTO(FanLogCreateRequest)를 끼우는 거임.
// “이 API는 이 값만 받겠습니다”, 그 외는 전부 무시 / 차단
// 그래서 만약 inning이 음수이거나, gameId가 없는 Id이면 여기서 걸러냄
package com.example.kbofanlog.controller.fanlog;

/**
 * ✅ Create 요청 DTO
 *
 * DTO를 쓰는 이유:
 * - Entity는 "DB 모델"이고
 * - Request DTO는 "외부에서 들어오는 입력 스펙(API 계약)"
 * -> 목적/변경 주기가 달라서 분리해야 한다.
 * DTO: 외부 사람이 데이터를 보내거나 받아갈 때 사용하는 **"임시 신청서"**입니다. 필요한 정보만 딱 적어서 주고받는 용도입니다.
 *
 * 지금은 validation 어노테이션이 없는데,
 * 필요하면 아래처럼 추가하고 Controller에 @Valid를 붙이면 됨.
 *
 * 예)
 * @NotNull, @Positive, @NotBlank 등
 */
public record FanLogCreateRequest( // record(자바 최신 문법)를 쓰면 Getter, 생성자, toString, equals 등을 우리가 직접 만들지 않아도 자바가 알아서 다 만들어줍니다.
        Long gameId,
        int inning,
        String memo
) {}

