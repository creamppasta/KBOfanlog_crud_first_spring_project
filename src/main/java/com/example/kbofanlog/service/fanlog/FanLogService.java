package com.example.kbofanlog.service.fanlog;

import com.example.kbofanlog.domain.fanlog.FanLog;
import com.example.kbofanlog.repository.fanlog.FanLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.kbofanlog.exception.FanLogNotFoundException;

import java.util.List;

/**
 * ✅ Service 역할: "유스케이스(업무 시나리오) + 트랜잭션 경계"
 *
 * - Controller가 넘긴 요청을 실제로 처리하는 중심 레이어
 * - 트랜잭션(@Transactional)을 보통 Service에 둔다
 *   -> 생성/수정/삭제 같은 "유스케이스 단위"로 묶기 자연스러움
 *   @Transactional -> “이 메서드 실행을 DB 관점에서 하나의 작업 단위로 묶고, 성공하면 커밋/실패하면 롤백하게 해주는 장치”
 * 📌 면접용 한 줄:
 * "Service는 비즈니스 로직과 트랜잭션 경계를 담당하며, Controller는 얇게 유지했습니다."
 */
@Service
@Transactional // ✅ 이 클래스의 public 메서드는 기본적으로 트랜잭션 안에서 실행됨
public class FanLogService {

    private final FanLogRepository fanLogRepository;

    public FanLogService(FanLogRepository fanLogRepository) {
        // ✅ Service는 Repository를 사용하지만 직접 생성하지 않고 DI로 받는다.
        this.fanLogRepository = fanLogRepository;
    }

    public Long save(Long gameId, int inning, String memo) {
        // ✅ Entity는 "DB에 저장되는 모델"
        // 외부 입력(JSON)을 Entity가 직접 받지 않게 하고,
        // Service에서 필요한 값들로 Entity를 구성한다.
        FanLog fanLog = new FanLog(gameId, inning, memo);

        // ✅ save()는 JpaRepository에 선언되어 있고, 전달받은 객체(fanLog)를 데이터베이스의 테이블에 저장(Insert)하거나 업데이트(Update)
        // 런타임에 스프링 데이터 JPA가 구현체(프록시)를 만들어 제공한다.
        FanLog saved = fanLogRepository.save(fanLog);
        // saved (저장 후): DB에 성공적으로 저장된 후, ID값까지 포함하여 다시 돌려받은 객체
        return saved.getId();
        // getId()를 리턴하는 이유는 방금 저장된 데이터의 **고유 번호(Primary Key)**를 확인해서 호출한 곳에 알려주려는 목적입니다
        // 예를 들어, 웹사이트에서 글을 쓰자마자 방금 쓴 글의 상세 페이지로 이동하려면 그 글의 번호를 알아야 하기 때문
    }

    public List<FanLog> findAll() { // FanLog라는 객체들만 담겨있는 리스트 선언
        // ✅ 조회는 트랜잭션이 없어도 되지만,
        // 지금은 클래스 단위 @Transactional이라 포함되어 있음.
        // (실무에서는 조회 전용은 readOnly=true 옵션을 주는 경우도 많음)
        return fanLogRepository.findAll();
    }

    public FanLog findById(Long id) {
        // ✅ Optional에서 없으면 예외를 던져 404로 매핑되게 함
        return fanLogRepository.findById(id)
                .orElseThrow(() -> new FanLogNotFoundException(id));
        // findById(id)를 호출하면 FanLog 형태가 바로 나오는게 아니라 Optional("없을 수도 있음"을 타입으로 표현한 것)이라는 상자로 나옴
        // 상자가 비어있다면 던져라(orElseThrow), 뭐를? -> 이 예외 객체를!
    }

    public void update(Long id, Long gameId, int inning, String memo) {
        // 1) 엔티티를 영속 상태로 가져온다(=JPA가 추적 가능한 상태)
        FanLog fanLog = fanLogRepository.findById(id)
                .orElseThrow(() -> new FanLogNotFoundException(id));

        // 2) 값만 변경한다
        // ✅ 트랜잭션 안에서 영속 엔티티의 값이 바뀌면
        // 트랜잭션 커밋 시점에 Dirty Checking으로 UPDATE SQL이 자동 실행된다.
        fanLog.update(gameId, inning, memo);

        // 3) save()를 굳이 호출하지 않아도 됨 (Dirty Checking)
    }

    public void delete(Long id) {
        FanLog fanLog = fanLogRepository.findById(id) // 해당 데이터를 fanLog변수에 넣음
                .orElseThrow(() -> new FanLogNotFoundException(id));

        // ✅ delete는 즉시 삭제 쿼리가 날아갈 수도 있고(상황에 따라 flush 시점),
        // 트랜잭션 커밋 시점에 반영되기도 한다.
        fanLogRepository.delete(fanLog); // delete는 jpa 내장 메서드
    }
}
