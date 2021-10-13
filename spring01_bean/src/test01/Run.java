package test01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {

	public static void main(String[] args) {
		
		ApplicationContext context 
			= new ClassPathXmlApplicationContext("test01/applicationContext.xml");

		Address ha = (Address) context.getBean("ha");
		Address lee = context.getBean("lee",Address.class);
		Address kim = context.getBean("kim",Address.class);
		System.out.println(ha);
		System.out.println(lee);
		System.out.println(kim);
		
	}

}
