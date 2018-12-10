package member.service;

import java.util.ArrayList;
import java.util.Map;

import vo.MemberVO;

public interface IMemberService {
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
	 * 회원아이디로 해당 회원 가져오기
	 * @param id
	 * @return MemberVO
	 */
	public MemberVO getMemberbyId(String id);
	
	/**
	 * 사용자이름으로 해당 회원 가져오기
	 * @param n
	 * @return ArrayList<MemberVO>
	 */
	public ArrayList<MemberVO> getMemberbyName(String n);
	
	/**
	 * 블랙리스트 회원 가져오기
	 * @return ArrayList<MemberVO>
	 */
	public ArrayList<MemberVO> getMemberbyBlack();
	
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
	
	/**
	 * 관리자인지 사용자인지 판단하기
	 * @param param
	 * @return MemberVO
	 */
	public MemberVO authorize(Map<String, String> param);
	
	/**
	 * 회원가입 시 입력받은 아이디와 회원 목록의 id와 비교하기
	 * @param id
	 * @return 일치여부
	 */
	public boolean isEqualMemId(String id);
	
	/**
	 * 정규식
	 * 비밀번호 패턴 가능여부
	 * 6자 이상 12글자 이하, 영문 대 소문자, 숫자, 특수문자
	 * @param pw
	 * @return 비밀번호 패턴 일치 여부
	 */
	public boolean isPossiblePw(String pw);	
	
	/**
	 * 가장 높은 마일리지를 가진 회원이름 가져오기
	 * @return 최고 마일리지 회원이름
	 */
	public String getMemMaxMileageName();
	
	/**
	 * 마일리지 업데이트하기
	 * @param rentId
	 */
	public void updateMileage(int rentId);
	
	/**
	 * 블랙리스트 업데이트하기
	 * @param rentId
	 */
	public void updateBlackList(String rentId);
	
	/**
	 * top3 도서왕 가져오기
	 * @return 3명의 도서왕이 담긴 리스트
	 */
	public ArrayList<MemberVO> getTop3RankMember();
	
	
	
	
	
	
	
	
	
	
	
}
