package com.kh.member.model.service;

import java.sql.Connection;

import static com.kh.common.JDBCTemplate.*;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {
	public Member loginMember(String userId, String userPwd) {
		
		// connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		// dao 호출
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		/*JDBCTemplate.*/close(conn);
		
		return m;
		
	}
	
	public int insertMember(Member m) {
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		int result = new MemberDao().insertMember(conn,m);
		
		// 트랜젝션 처리
		if(result > 0) {
			// 성공
			/*JDBCTemplate.*/commit(conn);
		}else {
			// 실패
			/*JDBCTemplate.*/rollback(conn);
		}
		
		/*JDBCTemplate.*/close(conn);
		
		return result;
	}
	
	public Member updateMember(Member m) {
		Connection conn = getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		Member updateMem = null;
		
		if(result > 0) { // 성공
			commit(conn);
			// 갱신된 회원 객체 다시 조회												// 수정 이전의 값이 세션에 남아있으므로 다시 조회해야함.
			updateMem = new MemberDao().selectMember(conn, m.getUserId()); 		// 수정한 그 회원의 아이디가 같이 넘어간다.
		}else { // 실패
			rollback(conn);
		}
		
		close(conn);
		
		return updateMem;			// 갱신된 행 수보다는 그 내용이 세션에 저장되어야하므로 result<int>가 아닌 updateMem<Member> 객체를 반환한다.
	}

	public Member updatePwd(String userId, String userPwd, String updatePwd) {
		Connection conn = getConnection();
		int result = new MemberDao().updatePwd(conn, userId, userPwd, updatePwd);
		Member updateMem = null;
		
		if(result > 0) { // 성공
			commit(conn);
			// 갱신된 회원 객체 다시 조회해오기
			updateMem = new MemberDao().selectMember(conn, userId);
			
		}else { // 실패
			rollback(conn);
		}
		
		close(conn);
		return updateMem;
	}
	
	public int deleteMember(String userId, String userPwd) {
		Connection conn = getConnection();
		int result = new MemberDao().deleteMember(conn, userId, userPwd);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	}
	
}
