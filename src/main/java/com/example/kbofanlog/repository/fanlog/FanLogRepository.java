package com.example.kbofanlog.repository.fanlog;

import com.example.kbofanlog.domain.fanlog.FanLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ✅ Repository 역할: "DB 접근만"
 *
 * - FanLog 엔티티를 DB에서 저장/조회/삭제하는 작업 담당
 * - save/findById/findAll/delete 등은 JpaRepository가 제공
 *
 * 📌 중요한 포인트:
 * "인터페이스만 만들었는데 동작하는 이유는,
 *  Spring Data JPA가 런타임에 구현체(프록시)를 생성해 주입하기 때문"
 */
public interface FanLogRepository extends JpaRepository<FanLog, Long> { // JpaRepository<엔티티 클래스, ID 타입>
    // 필요해지면 여기에 커스텀 조회 메서드 추가 가능            // "어떤 테이블(엔티티)을 관리할 것이고, 그 테이블의 키는 무슨 타입인가?"
    // 예: List<FanLog> findByGameId(Long gameId);
}
