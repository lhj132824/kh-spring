package com.kh.spring.common;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Date;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
public class MailSenderTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@Autowired
	RestTemplate http;
	
	@Test
	public void sendEmail() throws Exception{
		
		MimeMessage msg = mailSender.createMimeMessage();
		msg.setFrom("lhj132824@gmail.com");
		msg.setRecipients(Message.RecipientType.TO, "lhj132824@naver.com");
		msg.setSubject("메일테스트");
		msg.setSentDate(new Date());
		msg.setText("<h1>Email Test</h1>", "UTF-8", "html");
		mailSender.send(msg);
	}
	
	@Test
	public void restTemplateTest() {
		//String naver = http.getForObject("http://www.naver.com", String.class);
		MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
		body.add("userId", "test");
		body.add("password", "1234");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(body,headers);
		
		String login = http.postForObject("http://localhost:9090/member/login",entity,String.class);
		logger.debug(login);
	}
}
