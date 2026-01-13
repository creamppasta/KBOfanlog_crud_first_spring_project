package com.example.kbofanlog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ✅ 전역 예외 처리기
 *
 * - 컨트롤러/서비스 곳곳에서 예외를 직접 Response로 바꾸지 않아도 됨
 * - "예외 처리 정책"을 한 곳에 모아서 일관성 유지
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * FanLogNotFoundException이 발생하면 404로 응답한다.
     *
     * 흐름:
     * Service에서 예외 발생 → 컨트롤러까지 전파 → 여기서 잡아서 404로 변환
     */
    @ExceptionHandler(FanLogNotFoundException.class)
    // 어노테이션에 붙은 괄호(메서드처럼 필요한 정보 추가 제공하는것) -> "FanLogNotFoundException 상황의 예외를 잡을거야."
    // 예시 2: @NotNull(message = "필수입니다") -> 의미: "비어있으면 안 된다는 규칙을 적용해 줘. 그리고 만약 규칙을 어기면 에러 메시지는 이걸로 내보내줘."
    public ResponseEntity<String> handleFanLogNotFound(FanLogNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
