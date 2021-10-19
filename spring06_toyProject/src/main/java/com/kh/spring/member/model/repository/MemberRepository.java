package com.kh.spring.member.model.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.member.model.dto.Member;

@Mapper
public interface MemberRepository {

	@Insert("insert into member(user_id, password, email, tell) values(#{userId}, #{password}, #{email}, #{tell})")
	void insertMember(Member member);

}
