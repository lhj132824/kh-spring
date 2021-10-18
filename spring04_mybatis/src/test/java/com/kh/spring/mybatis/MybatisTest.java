package com.kh.spring.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.spring.member.model.dto.Member;

//가상으로 만들어지는 web.xml을 사용해 테스트환경을 구축
@WebAppConfiguration
//Junit을 실행할 방법
//테스트 때 사용할 가상의 applicationContext를 생성하고 관리
@RunWith(SpringJUnit4ClassRunner.class)
//가상의 applicationContext를 생성할 때 사용할 spring bean 설정파일의 위치를 지정
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"}) //spring아래의 모든 폴더 아래의 모든파일(context.xml로 끝나는)
public class MybatisTest {
	//Junit annotation
	//@Before : 테스트 전에 실행될 메서드
	//@Test : 테스트 메서드
	//@After : 테스트 이후에 실행될 메서드
	
	//SqlSessionTemplate의 주요 메서드
	//selectOne : 단일행 select문 실행
	//selectList : 다중행 select문 실행
	//insert : 메서드의 결과값은 쿼리에 의해 영향을 받은 row의 수
	//update : 메서드의 결과값은 쿼리에 의해 영향을 받은 row의 수 
	//delete : 메서드의 결과값은 쿼리에 의해 영향을 받은 row의 수
	//**procedure 호출은 dml 쿼리메서드 중에서 선택
	
	
	@Autowired
	private SqlSessionTemplate session;
	private final String NAMESPACE = "com.kh.spring.member.mybatis.MybatisMapper.";
	private String userId = "DEV";
	
	@Test
	public void selectOneTest() {
		String password = session.selectOne(NAMESPACE + "selectPasswordByUserId", userId); 
		System.out.println(password);
	}
	
	@Test
	public void selectOneAsDto() {
		Member member = session.selectOne(NAMESPACE + "selectMemberByUserId", userId);
		System.out.println(member);
	}
	
	@Test
	public void selectListAsMap() {
		List<Map<String,Object>> res = session.selectList(NAMESPACE + "selectRentAndMemberByUserId", userId);
		for (Map<String,Object> map : res) {
			System.out.println(map);
		}
	}
	
	@Test
	public void selectListUsingResultMap() {
		List<Map<String,Object>> res = session.selectList(NAMESPACE + "selectRentBookByUserId", userId);
		for (Map<String,Object> map : res) {
			System.out.println(map);
		}
	}
	
	@Test
	public void insertWithDto() {
		Member member = new Member();
		member.setUserId("mybatis2");
		member.setPassword("abcdefg");
		member.setTell("010-0000-0000");
		member.setEmail("pclass@naver.com");
		
		session.insert(NAMESPACE + "inserWithDto", member);
	}
	
	@Test
	public void insertWithMap() {
		Member member = new Member();
		member.setUserId("DEV");
		
		Map<String,Object> commandMap = new HashMap<String, Object>();
		commandMap.put("member", member);
		commandMap.put("title", "세션과 일곱 쿠키들");
		commandMap.put("rentBookCnt", 1);
		
		session.insert(NAMESPACE + "insertWithMap", commandMap);
	}
	
	@Test
	public void delete() {
		session.delete(NAMESPACE + "delete", "DEV");
	}
	
	@Test
	public void update() {
		Member member = new Member();
		member.setUserId("DEV");
		member.setPassword("ppppp");
		session.update(NAMESPACE + "update", member);
	}
	
	@Test
	public void procedure() {
		session.update(NAMESPACE + "procedure","100000");
	}
	
	//mybatis mapper escape 처리
	// <![CDATA[escape처리할 내용]]>
	//비교연산자 escape
	//&lt; &gt; 
	
	//1. 도서명 : 쿠키와 세션, 
    //    작가   : 김영아
    //    도서번호 : 시퀀스 사용
    //   인 도서를 BOOK 테이블에 저장하기
    //   메서드 이름 : test01
   @Test
   public void test01() {
	   Map<String,Object> commandMap = new HashMap<String, Object>();
	   commandMap.put("author", "김영아");
	   commandMap.put("title", "쿠키와 세션");
		
	   session.insert(NAMESPACE + "test01", commandMap);
   }
   
    //2. 연장횟수가 2회 이상인 모든 대출도서 정보를
    //    연장횟수 0회로 초기화 해주세요.
    //  메서드 이름 : test02
   @Test
   public void test02() {
	   Map<String,Object> commandMap = new HashMap<String, Object>();
	   commandMap.put("num1", 0);
	   commandMap.put("num2", 2);
		
	   session.insert(NAMESPACE + "test02", commandMap);
   }
    //3. 2021년 9월 이전에 가입된 회원정보를 삭제
    //  메서드 이름 : test03
   @Test
   public void test03() {
	   Map<String,Object> commandMap = new HashMap<String, Object>();
	   session.insert(NAMESPACE + "test03", Map.of("date","21/09/01"));
   }
    //4. 대출 횟수가 가장 많은 3권의 도서를 조회
    //  메서드 이름 : test04
   @Test
	public void test04() {
		List<Map<String,Object>> res = session.selectList(NAMESPACE + "test04");
		for (Map<String,Object> map : res) {
			System.out.println(map);
		}
	}
   
   
   //동적쿼리
   @Test
   public void dynamicIf() {
	   //사용자가 도서 검색필터에 info를 선택하고 검색하면, 사용자가 입력한 키워드가 info에 포함된 도서 검색
	   //사용자가 도서 검색필터에 author를 선택하고 검색하면, 사용자가 입력한 키워드가 author에 포함된 도서 검색
	   session.selectList(NAMESPACE + "dynamicIf", Map.of("filter","author","keyword","김애란"));
   }
   
   @Test
   public void dynamicChoose() {
	   //사용자가 별도의 필터를 선택하지 않을 경우 제목으로 검색
	   session.selectList(NAMESPACE + "dynamicChoose", Map.of("keyword","비행운"));
   }
   
   @Test
   public void dynamicForeachAndSetTagWithList() {
	   //사용자가 검색조건을 여러개 선택할 경우
	   //해당 조건들을 or연산하여 검색되는 도서를 반환
	   //사용자가 제목,내용,작가 검색조건을 선택하고
	   //키워드에 김애란을 입력할 경우 제목,내용,작가 중에서 하나다도 김애란이 조회되면 해당 도서 반환
	   String[] filters = {"info","author"};
	   session.selectList(NAMESPACE + "dynamicForeachAndSetTagWithList", Map.of("filters",filters,"keyword","김애란"));
	   
   }
   
   @Test
   public void dynamicFordachWithList() {
	   //사용자가 선택한 도서명 중에서 DB에 존재하는 도서를 모두 반환
	   session.selectList(NAMESPACE + "dynamicForeachWithList", List.of("비행운","남한산성","오징어게임"));
   }
   
   @Test
   public void insertTemplate() {
	   //사용자로부터 데이터를 입력할
	   //테이블명,컬럼명,값을 전달받아 해당 테이블에 사용자가 원하는 데이터를 입력하는 쿼리
	   
	   session.insert(NAMESPACE + "insertTemplate"
			   ,Map.of("tableName","member"
					   ,"data",Map.of("user_id","dynamic","password","1234","tell","010-0000-0000","email","dynamic@pclass.com")
					   )
			   );
   }
   
   @Test
   public void insertTemplate2() {
	   //사용자로부터 데이터를 입력할
	   //테이블명,컬럼명,값을 전달받아 해당 테이블에 사용자가 원하는 데이터를 입력하는 쿼리
	   
	   session.insert(NAMESPACE + "insertTemplate"
			   ,Map.of("tableName","book"
					   ,"sec",Map.of("colName","bk_idx","val","sc_bk_idx.nextval")
					   ,"data",Map.of("title","비행운","author","김애란")
					   )
			   );
   }
   
   @Test
   public void dynamicSet() {
	   Member member = new Member();
	   member.setUserId("DEV");
	   member.setPassword("00009999");
	   session.update(NAMESPACE + "dynamicSet",member);
   }
}
