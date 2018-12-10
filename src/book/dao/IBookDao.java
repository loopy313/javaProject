package book.dao;

import java.util.ArrayList;

import vo.BookVO;

public interface IBookDao {
	/**
	 * BookVO에 도서 추가하기
	 * @param bv - 도서
	 * @return 도서 추가 성공여부
	 */
	public boolean insertBook(BookVO bv);
	
	/**
	 * 도서목록 모두 가져오기
	 * @return	도서리스트
	 */
	public ArrayList<BookVO> getAllBookList();
	
	/**
	 * 도서 삭제하기
	 * @param bv - 삭제할 도서
	 * @return 도서 삭제 성공여부
	 */
	public boolean deleteBook(BookVO bv);
	
	/**
	 * 모든 도서 개수
	 * @return int 
	 */
	public int countAllBook();
	
	/**
	 * 도서수정 - 도서명 업데이트
	 * @param idx - 입력받은 수정할 책 번호
	 * @param str 입력받은 수정할 책 이름
	 */
	void updateBookName(int idx, String str);

	/**
	 * 도서수정 - 출판사 업데이트
	 *@param idx - 입력받은 수정할 책 번호
	 *@param str 입력받은 수정할 출판사 이름
	 */
	void updateBookPublisher(int idx, String str);
	
	/**
	 * 도서수정 - 저자 업데이트
	 *@param idx - 입력받은 수정할 책 번호
	 *@param str 입력받은 수정할 저자 이름
	 */
	void updateBookAuthor(int idx, String str); 
}
