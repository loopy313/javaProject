package category.dao;

import java.util.ArrayList;

import vo.CatVO;

public interface ICatDao {
	/**
	 * 카테고리 추가하기
	 * @param cv
	 * @return 추가 성공여부
	 */
	public boolean insertCat(CatVO cv);
	
	/**
	 * 전체 카테고리 목록 가져오기
	 * @return 카테고리 목록
	 */
	public ArrayList<CatVO> getAllCatList();
	
	/**
	 * 카테고리 삭제하기
	 * @param bv
	 * @return 삭제 성공 여부
	 */
	public boolean deleteCat(CatVO bv);
	
	/**
	 * 카테고리 총 개수
	 * @return 총 개수
	 */
	public int countAllCat();
}
