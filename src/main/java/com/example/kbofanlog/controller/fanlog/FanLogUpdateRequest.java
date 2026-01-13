package com.example.kbofanlog.controller.fanlog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * ✅ Update 요청 DTO
 *
 * - @Valid와 함께 쓰면 "들어오는 입력값"을 Controller 진입 시점에서 자동 검증 가능
 * - 검증 실패 시 Spring이 400 Bad Request를 만든다
 *   (그리고 메시지는 message 속성으로 커스터마이징 가능)
 */
public record FanLogUpdateRequest(
        @NotNull(message = "gameId는 필수입니다")
        Long gameId,

        @Positive(message = "inning은 1 이상의 숫자여야 합니다")
        int inning,

        @NotBlank(message = "memo는 비어 있을 수 없습니다") // null이 아니어야 함은 물론이고, 공백 문자(" ")나 빈 문자열("")도 허용하지 않습니다.
        String memo
) {}
