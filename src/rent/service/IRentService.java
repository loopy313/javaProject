package rent.service;

import java.util.ArrayList;

import vo.RentVO;

public interface IRentService {
	/**
	 * 대여한 책 RentVO에 추가하기
	 * @param rv
	 * @return 추가 성공여부
	 */
	public boolean insertRent(RentVO rv);

	/**
	 * 대여목록 모두 가져오기
	 * @return ArrayList<RentVO>
	 */
	public ArrayList<RentVO> getAllRentList();
	
	/**
	 * bookId로 대여한 책 가져오기
	 * @param id 입력받은 bookId
	 * @return RentVO
	 */
	public RentVO getRentbyBookId(int id);
	
	/**
	 * 회원의 id로 대여한 책 목록 가져오기
	 * @param id
	 * @return ArrayList<RentVO>
	 */
	public ArrayList<RentVO> getRentbyMemId(String id);
	
	/**
	 * rentId로 대여한 책 가져오기
	 * @param id
	 * @return RentVO
	 */
	public RentVO getRentbyRentId(int id);  

	/**
	 * 반납 시, 대여한 책 삭제하기
	 * @param bv
	 * @return 삭제 성공여부
	 */
	public boolean deleteRent(RentVO rv);
	
	/**
	 * 대여된 책의 수
	 * @return int
	 */
	public int countAllRent();
	
	/**
	 * 대여목록의 맨 마지막 rentId 가져오기
	 * @return int
	 */
	public int getLastRentId();
	
	/**
	 * rentId로 연체여부 판단하기
	 * @param rentId
	 * @return 연체되면 false
	 */
	public boolean isDelayed(int rentId);
	
	/**
	 * 연장시 반납날짜 업데이트, 7일 연장 
	 * @param rv
	 */
	public void updateRentDate(RentVO rv);

}
