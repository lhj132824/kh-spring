package factory.client;

import factory.framework.ConnectorFactory;
import factory.framework.SMTPConnector;

public class Run {

	public static void main(String[] args) {
		
		//네이버 SMTP서버에 연결을 해보자
		try {
			SMTPConnector conn = ConnectorFactory.builder()
					.url("smtp.naver.com")
					.id("lhj132824@naver.com")
					.password("wjscoals102$")
					.build()
					.getConnector();
			
			conn.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
