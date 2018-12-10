package book.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import book.dao.IBookDao;
import book.dao.IBookDaoImpl;
import vo.BookVO;

public class IBookServiceImpl implements IBookService {
	
	private static IBookServiceImpl bserv;
	private IBookDao bdao;
	private IBookServiceImpl(){
		bdao=IBookDaoImpl.getInstance();
	};
	
	public static IBookService getInstance(){
		if(bserv==null){
			bserv=new IBookServiceImpl();
		}
		return bserv;
	}
	
	@Override
	public boolean insertBook(BookVO bv) {
		return bdao.insertBook(bv);
	}

	@Override
	public ArrayList<BookVO> getAllBookList() {
		return bdao.getAllBookList();
	}

	@Override
	public ArrayList<BookVO> getBookbyAuthor(String a) {
		ArrayList<BookVO> bvl=new ArrayList<BookVO>();
		for(BookVO bv:getAllBookList()){
			if(bv.getAuthor().lastIndexOf(a)!=-1){
				bvl.add(bv);
			}
		}
		if(bvl.isEmpty()){
			return null;
		}
		return bvl;
	}

	@Override
	public BookVO getBookbyBookId(int id) {
		for(BookVO bv:getAllBookList()){
			if(id==bv.getBookId()){
				return bv;
			}
		}
		return null;
	}

	@Override
	public ArrayList<BookVO> getBookbyCatId(int id) {
		ArrayList<BookVO> bvl=new ArrayList<BookVO>();
		for(BookVO bv:getAllBookList()){
			if(id==bv.getCatId()){
				bvl.add(bv);
			}
		}
		if(bvl.isEmpty()){
			return null;
		}
		return bvl;
	}

	@Override
	public ArrayList<BookVO> getBookbyName(String n) {
		ArrayList<BookVO> bvl=new ArrayList<BookVO>();
		for(BookVO bv:getAllBookList()){
			if(bv.getName().lastIndexOf(n)!=-1){
				bvl.add(bv);
			}
		}
		if(bvl.isEmpty()){
			return null;
		}
		return bvl;
	}

	@Override
	public ArrayList<BookVO> getBookbyPublish(String p) {
		ArrayList<BookVO> bvl=new ArrayList<BookVO>();
		for(BookVO bv:getAllBookList()){
			if(bv.getPublish().lastIndexOf(p)!=-1){
				bvl.add(bv);
			}
		}
		if(bvl.isEmpty()){
			return null;
		}
		return bvl;
	}

	@Override
	public void updateRentable(int bookid) {
		for(BookVO bv:getAllBookList()){
			if(bookid==bv.getBookId()){
				bv.setRentable(!bv.isRentable());
			}
		}
	}

	@Override
	public void updateRentCount(int bookid) {
		for(BookVO bv:getAllBookList()){
			if(bookid==bv.getBookId()){
				int cnt=bv.getRentCount();
				bv.setRentCount(cnt++);
			}
		}
	}

	@Override
	public boolean deleteBook(BookVO bv) {
		return bdao.deleteBook(bv);
	}

	@Override
	public int countAllBook() {
		return bdao.countAllBook();
	}

	@Override
	public boolean isBookRentable(int bid) {
		for(BookVO bv:getAllBookList()){
			if(bid==bv.getBookId()){
				if(bv.isRentable()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getLastBooktId() {
		return countAllBook()-1;
	}

	@Override
	public ArrayList<BookVO> getTop3RankBook() {
		ArrayList<BookVO> bvl=getAllBookList();
		Collections.sort(bvl,new Comparator<BookVO>() {
			@Override
			public int compare(BookVO o1, BookVO o2) {
				if(o1.getRentCount()<o2.getRentCount()){
					return 1;
				}else if(o1.getRentCount()>o2.getRentCount()){
					return -1;
				}else{
					return 0;
				}
			}
		});
		return new ArrayList<BookVO>(bvl.subList(0, 3));
	}
	@Override
	public ArrayList<BookVO> getRentableBookByName(String name) {
		ArrayList<BookVO> bvl=new ArrayList<BookVO>();
		ArrayList<BookVO> temp =getBookbyName(name);
		if(temp==null){
			return null;
		}
		for(BookVO bv:temp){
			if(bv.isRentable()){
				bvl.add(bv);
			}
		}
		return bvl;
	}
	
	@Override
	public void updateBookName(int idx, String str) {
		bdao.updateBookName(idx, str);
	}

	@Override
	public void updateBookPublisher(int idx, String str) {
		bdao.updateBookPublisher(idx, str);
	}

	@Override
	public void updateBookAuthor(int idx, String str) {
		bdao.updateBookAuthor(idx, str);
	}
}
