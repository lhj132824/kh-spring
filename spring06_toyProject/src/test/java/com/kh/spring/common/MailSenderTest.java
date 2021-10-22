package com.kh.spring.common;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.Date;
import java.util.Map;

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
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	@Autowired
	ObjectMapper mapper;
	
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
	public void restTemplateTest() throws JsonMappingException, JsonProcessingException, RestClientException {
		RequestEntity<Void> request = RequestEntity
				.get("https://dapi.kakao.com/v3/search/book?query=java")
				.header("Authorization", "KakaoAK af648918ac82ccea3b9f14f8737f3632")
				.build();
		
		JsonNode root = mapper.readTree(http.exchange(request, String.class).getBody());
		for (JsonNode jsonNode : root.findValues("title")) {
			logger.debug(jsonNode.asText());
		}
			
	}
	
	@Test
	public void restTemplatePostTest() throws Exception{
		MultiValueMap<String,String> body = new LinkedMultiValueMap<String,String>();
		body.add("source", "en");
		body.add("target", "ko");
		body.add("text", "hellow my name is guhyeon");
		
		RequestEntity<MultiValueMap<String,String>> request =
				RequestEntity.post("https://openapi.naver.com/v1/papago/n2mt")
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.header("X-Naver-Client-Id", "0ezD3I2miPMQkSifKUi9")
				.header("X-Naver-Client-Secret","N4K8BAplv8")
				.body(body);
				
		JsonNode root = mapper.readTree(http.exchange(request, String.class).getBody());
		logger.debug(root.findValue("translatedText").asText());
		
				
	}
	
}
