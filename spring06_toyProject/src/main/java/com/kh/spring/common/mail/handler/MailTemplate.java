package com.kh.spring.common.mail.handler;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

public class MailTemplate {

	private String templateName;
	private Map<String,String> component = new HashMap<>();
	
	public MailTemplate(String templateName) {
		this.templateName = templateName;
	}
	
	public void addMailComponent(String name, String comp) {
		component.put(name, comp);
	}
	
	public String getTemplateName() {
		return templateName;
	}

	public Map<String,String> getMailComp() {
		return component;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setComponent(Map<String, String> component) {
		this.component = component;
	}
	
}
