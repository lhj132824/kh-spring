package com.kh.spring.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.spring.member.model.dto.Member;

@Mapper
public interface MybatisRepository {

	@Select("select password from member where user_id = #{userId}")
	String selectPasswordByUserId(String userId);
	
	@Select("select * from member where user_id = #{userId}")
	Member selectMemberByUserId(String userId);
	
	@Select("select *"
			+ " from member"
			+ " inner join rent_master using(user_id)"
			+ " where user_id = #{userId}")
	List<Map<String,Object>> selectRentAndMemberByUserId(String userId);
	
	//쿼리가 긴 친구들은 선언만하고 mapper에 남겨둠
	List<Map<String,Object>> selectRentBookByUserId(String userId);
	
	@Insert("insert into member(user_id, password, tell, email)"
			+ " values(#{userId}, #{password}, #{tell}, #{email})")
	int insertWithDto(Member member);
	
	@Insert("insert into rent_master(rm_idx, user_id, title, rent_book_cnt)"
			+ " values(sc_rm_idx.nextval, #{member.userId}, #{title}, #{rentBookCnt})")
	int insertWithMap(Map<String,Object> commandMap);
	
	@Delete("delete from rent_master where user_id = #{userId}")
	int delete(String userId);
	
	@Update("update member"
			+ " set password = #{password}"
			+ " where user_id = #{userId}")
	int update(Member member);
	
	@Update("{call sp_rent_extend(#{rbIdx, mode=IN})}")
	int procedure(String String);
}
