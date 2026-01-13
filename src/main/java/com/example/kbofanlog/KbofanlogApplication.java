package com.example.kbofanlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ✅ 스프링부트 애플리케이션의 시작점(부팅 스위치)
 *
 * - @SpringBootApplication 하나로 아래가 자동 설정됨
 *   1) 컴포넌트 스캔(@Controller/@Service/@Repository 찾아서 Bean 등록)
 *   	'컴포넌트'란? 스프링이 “우리 프로젝트 안에서 스프링이 관리해야 할 클래스(@Controller/@Service/@Repository 같은 애들)”를 자동으로 찾아서 등록하는 과정.
 *      "아 이건 @Service 니까 내가 관리해야겠다"라고 스프링이 생각할 수 있게 해줌.
 *      '빈(Bean)'이란? 스프링이 “내가 대신 만들어서 보관하고, 필요할 때 꺼내주고, 생명주기까지 관리해주는 객체(인스턴스)”.
 *      new로 만들면 내가 직접 관리해야 하지만 어노테이션(@)이 붙어있으면 Bean에 등록이 되고 스프링이 중앙에서 관리해줌
 *      일일이 연결, 테스트, 교체 할 필요가 없음.
 *
 *   2) 자동 설정(Auto Configuration)
 *   '자동 설정'이란 “네가 지금 가진 라이브러리/설정파일을 보고, 필요한 설정을 스프링부트가 자동으로 맞춰주는 기능”
 *
 *   3) 스프링부트 기본 설정 적용
 *
 * 📌 면접용 한 줄:
 * "이 클래스는 스프링부트를 실행시키는 엔트리 포인트이며, 컴포넌트 스캔과 자동 설정의 기준점입니다."
 */
@SpringBootApplication
public class KbofanlogApplication {

	public static void main(String[] args) {
		// ✅ 내장 톰캣(서버)을 띄우고, 스프링 컨테이너(ApplicationContext)를 만들고,
		//'스프링 컨테이너'란? Bean들을 “보관하는 창고”이자 “조립 공장”.
		// 	Bean을 만들고, Bean끼리 연결해주고, Bean을 보관하고 있다가 요청하면 꺼내줌.
		// 컨트롤러/서비스/레포지토리 Bean을 조립해서 실행한다.
		SpringApplication.run(KbofanlogApplication.class, args);
	}
}
