package book.dao;

import java.util.ArrayList;

import database.DBClass;
import vo.BookVO;

public class IBookDaoImpl implements IBookDao {

	private static IBookDaoImpl bdao;
	private DBClass db;

	private IBookDaoImpl() {
		db = DBClass.getInstance();
	};

	public static IBookDao getInstance() {
		if (bdao == null) {
			bdao = new IBookDaoImpl();
		}
		return bdao;
	}

	@Override
	public boolean insertBook(BookVO bv) {
		return db.insertBook(bv);
	}

	@Override
	public ArrayList<BookVO> getAllBookList() {
		return db.getAllBookList();
	}

	@Override
	public boolean deleteBook(BookVO bv) {
		return db.deleteBook(bv);
	}

	@Override
	public int countAllBook() {
		return db.countAllBook();
	}

	// daoImpl
	@Override
	public void updateBookName(int idx, String str) {
		db.updateBookName(idx, str);
	}

	@Override
	public void updateBookPublisher(int idx, String str) {
		db.updateBookPublisher(idx, str);
	}

	@Override
	public void updateBookAuthor(int idx, String str) {
		db.updateBookAuthor(idx, str);
	}

}
