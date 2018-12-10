package rent.dao;

import java.util.ArrayList;

import vo.RentVO;

public interface IRentDao {
	
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
	 * 반납 시, 대여한 책 삭제하기
	 * @param bv
	 * @return 삭제 성공여부
	 */
	public boolean deleteRent(RentVO bv);
	
	/**
	 * 대여된 책의 수
	 * @return int
	 */
	public int countAllRent();
}
