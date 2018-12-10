package book.service;

import java.util.ArrayList;

import vo.BookVO;

public interface IBookService {
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
	 * 도서 검색 시
	 * 저자명으로 도서목록 가져오기
	 * @param a - 저자명
	 * @return 저자명에 해당되는 도서리스트
	 */
	public ArrayList<BookVO> getBookbyAuthor(String a);
	
	/**
	 * 사용자가 선택한(입력한) bookId에 해당하는 도서 가져오기
	 * @param id - 사용자가 선택한(입력한) bookId
	 * @return 사용자가 선택한 도서
	 */
	public BookVO getBookbyBookId(int id);
	
	/**
	 * 카테고리 번호에 해당되는 도서 가져오기 
	 * @param id - 사용자가 입력한 번호
	 * @return 해당 카테고리에 대한 도서목록
	 */
	public ArrayList<BookVO> getBookbyCatId(int id);
	/**
	 * 도서명으로 도서 가져오기
	 * @param n 사용자가 입력한 도서명
	 * @return 해당 도서명에 대한 도서목록
	 */
	public ArrayList<BookVO> getBookbyName(String n);
	/**
	 * 출판사명으로 도서 가져오기
	 * @param p 사용자가 입력한 출판사명
	 * @return 해당 출판사에 대한 도서목록
	 */
	public ArrayList<BookVO> getBookbyPublish(String p);
	
	/**
	 * 대여 시, 대여가능여부 업데이트
	 * @param bookid
	 */
	public void updateRentable(int bookid);
	/**
	 * 베스트북 추출을 위해 대여횟수 업데이트
	 * @param bookid
	 */
	public void updateRentCount(int bookid);

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
	 * 대여가능여부
	 * @param bid
	 * @return boolean
	 */
	public boolean isBookRentable(int bid);
	
	/**
	 * 도서목록에 맨 마지막 도서의 id 가져오기
	 * @return int
	 */
	public int getLastBooktId();
	
	/**
	 * 대여횟수 가장 많은 1,2,3위 도서 가져오기
	 * @return 3개의 도서
	 */
	public ArrayList<BookVO> getTop3RankBook();
	
	/**
	 * 도서 삭제할 때, 대여한 도서는 삭제하면 안되니까
	 * 대여가능한 도서만 가져오기
	 * @param name 삭제할 책 이름
	 * @return 대여 가능한 도서목록
	 */
	public ArrayList<BookVO> getRentableBookByName(String name);

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
