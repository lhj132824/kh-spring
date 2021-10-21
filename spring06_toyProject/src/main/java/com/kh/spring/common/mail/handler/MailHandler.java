package com.kh.spring.common.mail.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MailHandler {
	
	@PostMapping("mail")
	public String writeMailTemplate(MailTemplate template) {
		
		return "mail-template/" + template.getTemplateName();
	}
	
}