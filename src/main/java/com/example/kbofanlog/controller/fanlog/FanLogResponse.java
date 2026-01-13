package com.example.kbofanlog.controller.fanlog;

import com.example.kbofanlog.domain.fanlog.FanLog;

// CreateRequest, UpdateRequest가 **"들어오는 편지"**였다면, 이 FanLogResponse는 **"나가는 답장"**이라고 생각하면 쉬워요.
/**
 * ✅ Response DTO
 *
 * - Entity를 그대로 반환하지 않고, 필요한 필드만 선택해 "API 계약"으로 제공
 * - Entity 구조가 바뀌어도 API 응답을 안정적으로 유지할 수 있음
 */
public record FanLogResponse(
        Long id,
        Long gameId,
        int inning,
        String memo
) {
    /**
     * ✅ Entity -> Response DTO 변환 메서드
     *
     * 변환 로직을 한곳에 모아두면 장점:
     * - Controller가 더 얇아짐
     * - 변환 규칙이 바뀌어도 여기만 수정하면 됨
     */
    public static FanLogResponse from(FanLog fanLog) {
        return new FanLogResponse(
                fanLog.getId(),
                fanLog.getGameId(),
                fanLog.getInning(),
                fanLog.getMemo()
        );
    }
}
