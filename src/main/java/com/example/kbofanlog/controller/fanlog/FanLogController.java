package com.example.kbofanlog.controller.fanlog;

import com.example.kbofanlog.domain.fanlog.FanLog;
import com.example.kbofanlog.service.fanlog.FanLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

/**
 * ✅ Controller 역할: "HTTP 입구" 오직 Controller만이 HTTP를 상대한다.
 *
 * Controller가 하는 일(=HTTP 관심사)
 * - URL 매핑, HTTP Method(GET/POST/PUT/DELETE) 처리
 * - @RequestBody로 JSON -> DTO 변환
 * - @PathVariable로 URL의 {id} 값을 꺼냄
 * - (필요하면) @Valid로 입력값 검증
 * - Service 호출 후 ResponseEntity로 응답(상태코드/바디) 구성
 *
 * Controller가 하면 안 되는 일
 * - DB 접근 / 트랜잭션 처리 / 복잡한 비즈니스 로직
 *
 * 📌 면접용 한 줄:
 * "Controller는 HTTP 요청/응답 처리만 담당하고, 핵심 로직은 Service로 위임해 얇게 유지했습니다."
 */
@RestController // 이 클래스의 반환값은 View가 아니라 "JSON(응답 바디)"로 나간다
@RequestMapping("/fanlogs") // 이 컨트롤러는 /fanlogs로 시작하는 요청을 담당
public class FanLogController {

    // ✅ DI(의존성 주입)
    // Controller는 Service가 필요하지만 "new FanLogService()"로 직접 만들지 않는다.
    // -> 스프링이 만들어둔 Bean을 주입받아 사용한다(결합도 낮추기).
    private final FanLogService fanLogService;

    public FanLogController(FanLogService fanLogService) {
        this.fanLogService = fanLogService;
    }

    // 생성 API: POST /fanlogs
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody FanLogCreateRequest request) { // ResponseEntity는 “스프링 프레임워크가 제공하는 클래스"
        // ✅ request는 JSON을 DTO로 변환한 결과
        // DTO를 쓰는 이유: Entity(FanLog)를 외부 입력과 직접 연결하지 않기 위해
        Long id = fanLogService.save(
                request.gameId(),
                request.inning(),
                request.memo()
        );

        // ✅ 보통 REST에서는 201 Created + Location 헤더를 쓰기도 함
        // 지금은 연습용으로 "생성된 id"만 200 OK로 반환
        return ResponseEntity.ok(id);
    }

    // 전체 조회 API: GET /fanlogs
    @GetMapping
    public ResponseEntity<List<FanLogResponse>> getAll() {
        // ✅ Entity 리스트를 그대로 반환하지 않고 Response DTO로 변환해서 반환
        // 이유: API 응답 스펙(외부 계약)을 Entity 변경과 분리하기 위해
        List<FanLogResponse> responses = fanLogService.findAll()
                .stream()
                .map(FanLogResponse::from) // 변환 로직을 DTO에 둬서 Controller를 깔끔하게 유지
                .toList();

        return ResponseEntity.ok(responses);
    }

    // 단건 조회 API: GET /fanlogs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<FanLogResponse> getOne(@PathVariable Long id) {
        // ✅ {id}는 URL 경로에서 꺼내온 값
        FanLog fanLog = fanLogService.findById(id);

        return ResponseEntity.ok(FanLogResponse.from(fanLog));
    }

    // 수정 API: PUT /fanlogs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody FanLogUpdateRequest request // @Valid 어노테이션을 붙여 줘야 검사해야하는 DTO인지 알아챔
                                                            // -> @Valid가 있어야 DTO에서 @NOTNULL, @Positive같은 어노테이션 작동
    ) {
        // ✅ 수정은 "유스케이스" 단위라 Service에서 트랜잭션 안에서 처리한다.
        // 여기서는 단지 입력받고 서비스 호출만 한다.
        fanLogService.update(id, request.gameId(), request.inning(), request.memo());

        // ✅ 수정 성공 시 내용이 필요 없으면 204 No Content
        return ResponseEntity.noContent().build();
    }

    // 삭제 API: DELETE /fanlogs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fanLogService.delete(id);

        // ✅ 삭제도 보통 204 No Content
        return ResponseEntity.noContent().build();
    }
}
