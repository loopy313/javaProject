package member.dao;

import java.util.ArrayList;

import vo.MemberVO;

public interface IMemberDao {
	/**
	 * 회원가입한 사용자 MemberVO에 추가하기
	 * @param mv
	 * @return 추가 성공여부
	 */
	public boolean insertMember(MemberVO mv);
	
	/**
	 * 모든 회원 목록 가져오기
	 * @return 모든 회원 목록
	 */
	public ArrayList<MemberVO> getAllMemberList();
	
	/**
	 * 해당 회원 삭제하기
	 * @param bv
	 * @return 삭제 성공여부
	 */
	public boolean deleteMember(MemberVO bv);
	
	/**
	 * 전체 회원의 수 구하기
	 * @return 전체 회원의 수
	 */
	public int countAllMember();
}
