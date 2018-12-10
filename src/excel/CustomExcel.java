package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import database.DBClass;
import vo.BookVO;
import vo.CatVO;
import vo.MemberVO;
import vo.RentVO;

public class CustomExcel {
	private static CustomExcel ce;
	public CustomExcel getInstance(){
		if(ce==null){
			ce= new CustomExcel();
		}
		return ce;
	}

	private int cnt=0;
	private ArrayList[] li = new ArrayList[]{new ArrayList<MemberVO>(),new ArrayList<BookVO>(),new ArrayList<CatVO>(),new ArrayList<RentVO>()};
	private String excelPathHead =System.getProperty("user.dir")+"\\Library";
	private String excelPathTale =".xlsx"; // D:\\hnDir\\test"+(this.cnt++)+".xlsx 최근 엑셀파일
	private XSSFWorkbook workBook=null;
	/**
	 * 
	 * @param db (= DBclass  db)
	 */
	public boolean createExcell(DBClass db){
		boolean a;//			HSSFSheet sheet = cwb.createSheet(); 	// sheet 은 현재 MemberVO용이다.
		this.workBook = new XSSFWorkbook(); 
		createMemberSheet(db);
		createBookSheet(db);
		createCatVOSheet(db);
		createRentVOSheet(db);
		File file = new File(excelPathHead+(cnt+=1)+excelPathTale); // 상단에 링크 

		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(file);
			System.out.println();
			this.workBook.write(fos);
		}catch(IOException e){
			System.out.println("cwb.write에러");
			System.out.println(this.cnt);
		}finally{
			try{
				a=true;
				if(this.workBook!=null){this.workBook.close();}
				if(fos!=null){fos.close();}
			}catch(Exception e){
				a=false;
				System.out.println("close() 에러");
			}
		}
		return a;
	}
	private void createMemberSheet(DBClass db){
		String[] memColumn={"회원테이블","회원아이디   ",
				"회원 비밀번호 ","회원이름 ",
				"회원마일리지  ","회원블랙 여부",
				"관리자 여부"
		};
		XSSFSheet sheet2 = this.workBook.createSheet(memColumn[0]);
		XSSFRow   row = sheet2.createRow(0);		//행,로우row,레코드 생성
		XSSFCell  cell;
		//	HSSFCell cell=row.createCell(0);cell.setCellValue("아이디");
		//	가로방향으로 한칸추가				// 셀입력
		for(int i=0;i<memColumn.length-1;i++){
			cell=row.createCell(i);
			cell.setCellValue(memColumn[i+1]);
		}
		for(int i=0;i<db.memList.size();i++){
			int j=0;
			MemberVO mv = db.memList.get(i);
			row = sheet2.createRow(i+1);
			cell = row.createCell(j++);
			cell.setCellValue(mv.getMemId());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getMemPw());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getMemName());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getMemMileage());
			cell = row.createCell(j++);
			cell.setCellValue(mv.isMemBlack());
			cell = row.createCell(j++);
			cell.setCellValue(mv.isAdmin());
			cell = row.createCell(j++);
			cell.setCellValue(mv.isAdmin());
		}
	}
	private void createBookSheet(DBClass db){
		String[] bookColumn={"책테이블","책ID","책이름",
				"책 출판사","책대여횟수",
				"책저자","카테고리ID",
				"책대여여부"
		};
		XSSFSheet sheet2 = this.workBook.createSheet(bookColumn[0]);
		XSSFRow   row = sheet2.createRow(0);		//행,로우row,레코드 생성
		XSSFCell  cell;
		//	HSSFCell cell=row.createCell(0);cell.setCellValue("아이디");
		//	가로방향으로 한칸추가				// 셀입력
		for(int i=0;i<bookColumn.length-1;i++){
			cell=row.createCell(i);
			cell.setCellValue(bookColumn[i+1]);
		}
		for(int i=0;i<db.bookList.size();i++){
			int j=0;
			BookVO mv = db.bookList.get(i);
			row = sheet2.createRow(i+1);
			cell = row.createCell(j++);
			cell.setCellValue(mv.getBookId());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getName());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getPublish());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getRentCount());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getAuthor());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getCatId());
			cell = row.createCell(j++);
			cell.setCellValue(mv.isRentable());
		}
	}
	private void createCatVOSheet(DBClass db){
		String[] categoryColumn={"카테고리","카테고리ID",
				"카고리리이름"
		};
		XSSFSheet sheet2 = this.workBook.createSheet(categoryColumn[0]);
		XSSFRow   row = sheet2.createRow(0);		//행,로우row,레코드 생성
		XSSFCell  cell;
		//	HSSFCell cell=row.createCell(0);cell.setCellValue("아이디");
		//	가로방향으로 한칸추가				// 셀입력
		for(int i=0;i<categoryColumn.length-1;i++){
			cell=row.createCell(i);
			cell.setCellValue(categoryColumn[i+1]);
		}
		for(int i=0;i<db.catList.size();i++){
			int j=0;
			CatVO mv = db.catList.get(i);
			row = sheet2.createRow(i+1);
			cell = row.createCell(j++);
			cell.setCellValue(mv.getCatId());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getCatName());
		}
	}

	private void createRentVOSheet(DBClass db){
		String[] rentColumn={"대여","대여ID",
				"반납예정일자","반납예정일자","책ID","대여회원ID"
		};
		XSSFSheet sheet2 = this.workBook.createSheet(rentColumn[0]);
		XSSFRow   row = sheet2.createRow(0);		//행,로우row,레코드 생성
		XSSFCell  cell;
		//	HSSFCell cell=row.createCell(0);cell.setCellValue("아이디");
		//	가로방향으로 한칸추가				// 셀입력
		for(int i=0;i<rentColumn.length-1;i++){
			cell=row.createCell(i);
			cell.setCellValue(rentColumn[i+1]);
		}
		for(int i=0;i<db.rentList.size();i++){
			int j=0;
			RentVO mv = db.rentList.get(i);
			row = sheet2.createRow(i+1);
			cell = row.createCell(j++);
			cell.setCellValue(mv.getRentId());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getRentDate());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getScheduleDate());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getMemId());
			cell = row.createCell(j++);
			cell.setCellValue(mv.getBookId());
		}
	}
	private void readCommonExcell(){
		for(int i=0;i<workBook.getNumberOfSheets();i++){ //시트의개수
			XSSFSheet sheet =workBook.getSheetAt(i);
			System.out.println("----------------- DB 로딩중 ("+(i+1)+"/4)-----------------");
			for(int j=1;j<sheet.getPhysicalNumberOfRows();j++){
				Object[] obj = new Object[sheet.getRow(j).getPhysicalNumberOfCells()];
				for(int k=0;k<sheet.getRow(j).getPhysicalNumberOfCells();k++){
					obj[k] = cellType(sheet.getRow(j).getCell(k).getCellType(),sheet.getRow(j).getCell(k));
				}
				try{
					if(i==0){//MemberVO
						int l=0;
						MemberVO mv = new MemberVO();
						mv.setMemId((String)obj[l++]);
						mv.setMemPw((String)obj[l++]);
						mv.setMemName((String)obj[l++]);
						mv.setMemMileage((int)obj[l++]);
						mv.setMemBlack((boolean)obj[l++]);
						mv.setAdmin((boolean)obj[l++]);
						mv.setEmail((String)obj[l++]);
						li[0].add(mv);
					}else if(i==1){//BookVO
						int l=0;
						BookVO book=new BookVO();
						book.setBookId((int)obj[l++]);
						book.setName((String)obj[l++]);
						book.setPublish((String)obj[l++]);
						book.setRentCount((int)obj[l++]);
						book.setAuthor((String)obj[l++]);
						book.setCatId((int)obj[l++]);
						book.setRentable((boolean)obj[l++]);
						li[1].add(book);
					}else if(i==2){//RentVO
						int l=0;
						CatVO cat=new CatVO();
						cat.setCatId((int)obj[l++]);
						cat.setCatName((String)obj[l++]);
						li[2].add(cat);
					}else{//CatVO
						int l=0;
						RentVO rv=new RentVO();
						rv.setRentId((int)obj[l++]);
						rv.setRentDate((String)obj[l++]);
						rv.setScheduleDate((String)obj[l++]);
						rv.setMemId((String)obj[l++]);
						rv.setBookId((int)obj[l++]);
						li[3].add(rv);
					}
				}catch(Exception e){
					System.out.println("도서관DB 로딩오류입니다.");
				}
			}
		}
	}
	private Object cellType(int a,XSSFCell k){
		Object obj = null;
		switch(a){
		case XSSFCell.CELL_TYPE_BLANK:  obj = new String("");
		break;

		case XSSFCell.CELL_TYPE_BOOLEAN: obj = new Boolean(k.toString());
		break;

		case XSSFCell.CELL_TYPE_FORMULA: obj =Double.valueOf(k.toString()).intValue();
		break;

		case XSSFCell.CELL_TYPE_STRING:	obj = new String(k.toString());
		break;

		default: obj= Double.valueOf(k.toString()).intValue();// String(k.toString());

		}
		return obj;
	}
	public void readExcell() {
		if(workBook!=null){
			readCommonExcell();
		}
		else{
			try {
				workBook=new XSSFWorkbook(new FileInputStream(new File((excelPathHead+(cnt)+excelPathTale))));
				readCommonExcell();
			} catch (IOException e) {
				System.out.println("입출력 오류 도서관DB 로딩오류입니다.");
			}catch (NullPointerException e) {
				System.out.println("끼야야야야야아ㅓ앙 시간NULL NULL 해요도서관DB 로딩오류입니다.");
			}catch (Exception e) {
				System.out.println("끼야야야야야아ㅓ앙!! 도서관DB 로딩오류입니다.");
			}
		}
	}

	public ArrayList<MemberVO> loadMemberVO(){
		return 	li[0];
	}
	public ArrayList<BookVO> loadBookVO(){
		return 	li[1];
	}
	public ArrayList<CatVO> loadCatVO(){
		return 	li[2];
	}
	public ArrayList<RentVO> loadRentVO(){
		return 	li[3];
	}
}
