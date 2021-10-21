package com.kh.spring.common.code;

public enum ErrorCode {
	
	DATABASE_ACCESS_ERROR("데이터베이스와 통신 중 에러가 발생하였습니다."),
	VALIDATOR_FAIL_ERROR("부적절한 양식의 데이터 입니다."),
	MAIL_SEND_FAIL_ERROR("이메일 발송 중 에러가 발생하였습니다."),
	HTTP_CONNECT_ERROR("HTTP 통신 중 에러가 발생하였습니다."),
	AUTHENTICATION_FAILED_ERROR("유효하지 않은 인증입니다."),
	UNAUTHORIZED_PAGE_ERROR("접근 권한이 없는 페이지 입니다."),
	FAILED_FILE_UPLOAD_ERROR("파일 업로드에 실패했습니다."),
	REDIRECT_LOGIN_PAGE("","/member/login-form");
	
	public final String MESSAGE;
	public final String URL;
	
	private ErrorCode(String msg) {
		this.MESSAGE = msg;
		this.URL = "/";
	}
	
	private ErrorCode(String msg, String url) {
		this.MESSAGE = msg;
		this.URL = url;
	}
}
