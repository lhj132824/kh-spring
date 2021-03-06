package aop02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {

	public static void main(String[] args) {
		
		ApplicationContext context =
				new ClassPathXmlApplicationContext("aop02/applicationContext.xml");
		
		Man man = context.getBean("man",Man.class);
		Woman woman = context.getBean("woman",Woman.class);
		//Woman woman = context.getBean("woman",Woman.class); applicationContext를 통해 프록시객체로 넘어오는게 아니라면 이렇게도 쓸 수 있음
		
		for (Class<?> c : man.getClass().getClasses()) {
			System.out.println(c);
		}
		
		woman.develop();
		System.out.println("============================");
		man.develop();
	}

}
