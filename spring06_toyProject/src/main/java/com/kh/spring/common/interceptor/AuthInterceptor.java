package com.kh.spring.common.interceptor;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.code.MemberGrade;
import com.kh.spring.common.exception.HandlableException;
import com.kh.spring.member.model.dto.Member;

public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) {
		
		String[] uriArr = httpRequest.getRequestURI().split("/");
		
		if(uriArr.length != 0) {
			
			switch (uriArr[1]) {
			case "member":
				memberAuthorize(httpRequest, httpResponse, uriArr);
				break;
			case "admin":
				adminAuthorize(httpRequest, httpResponse, uriArr);
				break;
			case "board":
				boardAuthorize(httpRequest, httpResponse, uriArr);
				break;

			default:
				break;
			}
			
		}
		//다음 인터셉터 또는 컨트롤러
		return true;
	}
	
	private void boardAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) {
		HttpSession session = httpRequest.getSession();
		Member member = (Member) session.getAttribute("authentication");
		
		switch (uriArr[2]) {
		case "board-form":
			if(member == null) {
				throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR);
			}
			break;
		case "upload":
			if(member == null) {
				throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR);
			}
			break;
		default:
			break;
		}
	}


	private void adminAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) {
		HttpSession session = httpRequest.getSession();
		Member member = (Member) session.getAttribute("authentication");
		//비회원과, 사용자 회원인지를 판단
		if(member == null || MemberGrade.valueOf(member.getGrade()).ROLE.equals("user")) {
			throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR);
		}
		//멤버인지 확인하고 비회원이거나 멤버의 역할이 user인지 판단하여 admin으로의 접근 차단
		
		
		//슈퍼관리자인지 판단해 슈퍼관리자라면 모든 admin페이지에 접근할 수 있다.
		if(MemberGrade.valueOf(member.getGrade()).DESC.equals("super")) {
			return;
		}
		//super관리자는 별도의 인증없이 바로 넘어가게끔 return
		
		switch (uriArr[2]) {
		case "member":
			if(!MemberGrade.valueOf(member.getGrade()).DESC.equals("member")) {
				throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR);
			}
			break;
		case "board":
			if(!MemberGrade.valueOf(member.getGrade()).DESC.equals("board")) {
				throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR);
			}
			break;

		default:
			break;
		}
		
	}


	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uriArr) {
		HttpSession session = httpRequest.getSession();
		
		switch (uriArr[2]) {
		
		case "mypage":
			if(session.getAttribute("authentication") == null) {
				throw new HandlableException(ErrorCode.UNAUTHORIZED_PAGE_ERROR);
			}
			break;

		default:
			break;
		}
		
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
