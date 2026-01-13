package com.example.kbofanlog.exception;

/**
 * ✅ "존재하지 않는 FanLog" 상황을 표현하는 예외
 *
 * - Service에서 찾기 실패 시 던짐
 * - Controller는 이 예외를 직접 처리하지 않고,
 *   GlobalExceptionHandler(RestControllerAdvice)에게 맡김
 *
 * 📌 면접용 한 줄:
 * "예외는 Service에서 던지고, HTTP 응답은 Advice에서 일관되게 처리했습니다."
 */
public class FanLogNotFoundException extends RuntimeException {
    public FanLogNotFoundException(Long id) {
        super("FanLog not found: " + id);
    }
}
