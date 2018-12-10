package database;

import java.util.ArrayList;

import excel.CustomExcel;
import vo.BookVO;
import vo.CatVO;
import vo.MemberVO;
import vo.RentVO;

public class DBClass {
	private static DBClass db;
	private DBClass(){};
	public static CustomExcel ce;
	public static DBClass getInstance(){
		if(db==null){
			db=new DBClass();
		}
		return db;
	}

	public ArrayList<MemberVO> memList=new ArrayList<MemberVO>(); 
	public ArrayList<CatVO> catList=new ArrayList<CatVO>();
	public ArrayList<BookVO> bookList=new ArrayList<BookVO>();
	public ArrayList<RentVO> rentList=new ArrayList<RentVO>();
//	{	
//		ce= new CustomExcel();
//		ce.readExcell();
//		memList=ce.loadMemberVO();
//		catList=ce.loadCatVO();
//		bookList=ce.loadBookVO();
//		rentList=ce.loadRentVO();
//	}
	//사람정보 초기화 블럭
	{
		Object[][] members={
				{"admin","admin","관리자",0,false,true,""},
				{"user","user","user",0,false,false,"rich113@naver.com"},
				{"a","1234","조현웅", 30, false,false,""},
				{"b", "2222", "정창훈", 35, false,false,""},
				{"c", "3333", "이승한", 35, false,false,""},
				{"d", "8888", "이초연", 30, false,false,""},
				{"e", "5555", "김정훈", 15, false,false,""},
				{"f", "6666", "이현우", -100, true,false,""},
				{"g", "5555", "박수현", 20, false,false,""},
				{"h", "7777", "김정민", -300, true,false,""},
		};
		for(int i=0;i<members.length;++i){
			int n=0;
			MemberVO mv = new MemberVO();
			mv.setMemId((String)members[i][n++]);
			mv.setMemPw((String)members[i][n++]);
			mv.setMemName((String)members[i][n++]);
			mv.setMemMileage((int)members[i][n++]);
			mv.setMemBlack((boolean)members[i][n++]);
			mv.setAdmin((boolean)members[i][n++]);
			mv.setEmail((String)members[i][n++]);
			memList.add(mv);
		}
	}

	//카테고리 초기화 블럭
	{
		Object[][] cats = {{0,"기술"},{1,"문학"},
				{2,"소설"},{3,"역사"},{4,"과학"}
		};

		for(int i=0;i<cats.length;++i){
			int n=0;
			CatVO cat=new CatVO();
			cat.setCatId((int)cats[i][n++]);
			cat.setCatName((String)cats[i][n++]);
			catList.add(cat);
		}
	}

	//책정보 초기화
	{
		Object[][] books = {{1, "Java의 정석", "도우출판", "남궁성", 0, false,1},
				{2, "정보처리기사 실기", "영진닷컴", "남궁성", 0, false,1},
				{3, "언어의 온도", "영만출판", "이기주", 1, false,2},
				{4, "살인자의 기억법", "문학동네", "김영하", 1, false,2},
				{5, "당신의 이름은", " 무늬", "이미숙", 2, false,1},
				{6, "땅의 역사 세트", "  상상출판", "박종인", 3, true,0},
				{7, "dummy","dummy1","dummy2",0,true,0},
				{9,"자바1","자바출판","박자바",0,true,0},
				{10,"자바2","자바출판","박자바",0,true,0},
				{11,"자바3","자바출판","박자바",0,true,0},
		};

		for(int i=0;i<books.length;++i){
			int n=0;
			BookVO book=new BookVO();
			book.setBookId((int)books[i][n++]);
			book.setName((String)books[i][n++]);
			book.setPublish((String)books[i][n++]);
			book.setAuthor((String)books[i][n++]);
			book.setCatId((int)books[i][n++]);
			book.setRentable((boolean)books[i][n++]);
			book.setRentCount((int)books[i][n++]);
			bookList.add(book);
		} 
	}

	//대여정보초기화
	{
		Object[][] rent={{1,"2018/12/01","2018/12/14","a",1},
				{2, "2018/12/01","2018/12/14", "a", 2},
				{3, "2018/12/01","2018/12/14", "b", 3},
				{4, "2018/12/01","2018/12/14", "b", 4},
				{5, "2018/12/01","2018/12/14", "c", 5},
				{6, "2018/11/01","2018/11/14", "c", 6},
				{7, "2018/11/01","2018/11/14", "c", 7},
		};

		for(int i=0;i<rent.length;++i){
			int n=0;
			RentVO rv=new RentVO();
			rv.setRentId((int)rent[i][n++]);
			rv.setRentDate((String)rent[i][n++]);
			rv.setScheduleDate((String)rent[i][n++]);
			rv.setMemId((String)rent[i][n++]);
			rv.setBookId((int)rent[i][n++]);
			rentList.add(rv);
		}
	}
	
	public boolean insertMember(MemberVO mv){
		return memList.add(mv);
	}

	public ArrayList<MemberVO> getAllMemberList(){
		return memList;
	}

	public boolean deleteMember(MemberVO mv){
		return memList.remove(mv);
	}

	public int countAllMember(){
		memList.trimToSize();
		return memList.size();
	}

	public boolean insertCat(CatVO cv){
		return catList.add(cv);
	}
	public ArrayList<CatVO> getAllCatList(){
		return catList;
	}

	public boolean deleteCat(CatVO bv){
		return catList.remove(bv);
	}
	public int countAllCat(){
		catList.trimToSize();
		return catList.size();
	}

	public boolean insertRent(RentVO rv){
		return rentList.add(rv);
	}
	public ArrayList<RentVO> getAllRentList(){
		return rentList;
	}

	public boolean deleteRent(RentVO rv){
		return rentList.remove(rv);
	}

	public int countAllRent(){
		rentList.trimToSize();
		return rentList.size();
	}

	public boolean insertBook(BookVO bv){
		return bookList.add(bv);
	}
	public ArrayList<BookVO> getAllBookList(){
		return bookList;
	}

	
	public boolean deleteBook(BookVO bv){
		return bookList.remove(bv);
	}
	
	public int countAllBook(){
		bookList.trimToSize();
		return bookList.size();
	}
	
	public void updateBookName(int idx, String str) {
		if(!(str.equals(""))){
			bookList.get(idx-1).setName(str);
		}
	}

	public void updateBookPublisher(int idx, String str) {
		if(!(str.equals(""))){
			bookList.get(idx-1).setPublish(str);
		}
	}

	public void updateBookAuthor(int idx, String str) {
		if(!(str.equals(""))){
			bookList.get(idx-1).setAuthor(str);
		}
	}
}
