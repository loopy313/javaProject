package category.service;

import java.util.ArrayList;

import vo.CatVO;

public interface ICatService {
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
	 * 카테고리 아이디로 해당 카테고리 가져오기
	 * @param id 입력받은 카테고리 아이디
	 * @return CatVO
	 */
	public CatVO getCatbyCatId(int id);
	
	/**
	 * 카테고리 이름 가져오기
	 * @param n
	 * @return CatVO
	 */
	public CatVO getCatbyCatName(String n);
	
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
	
	/**
	 * 해당 카테고리가 있는지 없는 지 판단
	 * @param catId
	 * @return
	 */
	public boolean isValidateCat(int catId);
}
