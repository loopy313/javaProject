package pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rent.service.IRentService;
import rent.service.IRentServiceImpl;
import member.service.IMemberService;
import member.service.IMemberServiceImpl;
import book.service.IBookService;
import book.service.IBookServiceImpl;
import category.service.ICatService;
import category.service.ICatServiceImpl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import vo.BookVO;
import vo.CatVO;
import vo.MemberVO;
import vo.RentVO;

public class Pdf {
	public static void main(String[] args) throws DocumentException, IOException {
		bookListPdf();
		memberListPdf();
		rentListPdf();
		catListPdf();
	}


	private static void catListPdf() throws DocumentException, IOException {
		ICatService cs = ICatServiceImpl.getInstance();

		//맑은고딕 폰트와 폰트 경로
		String fontname = "c:\\Windows\\Fonts\\MALGUN.ttf";
		//저장될 파일경로 , 파일이름
		//				String filename = "C:\\새 폴더\\test001.pdf";
		String filename = "d:\\대덕도서관 카테고리 조회.pdf";

		// 용지 및 여백 설정(좌, 우 , 상, 하)
		Document document = new Document(PageSize.A4, 0, 0, 20, 20); 

		//발생하는 예외 main()처리
		PdfWriter.getInstance(document, new FileOutputStream(filename)); 

		//document를 열어 PDF문서를 사용할 수 있도록 한다.
		document.open();

		//한글폰트를 사용하기 위한 셋팅
		BaseFont bfont = BaseFont.createFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		//(폰트, 폰트크기)
		Font font = new Font(bfont, 12);
		Font font2  = new Font(bfont, 14, Font.BOLD);

		//타이틀 단락추가
		Paragraph title = new Paragraph("대덕도서관 카테고리 목록",font2);
		//타이틀 중앙정렬
		title.setAlignment(Element.ALIGN_CENTER);

		//PDF문서 title 추가
		document.add(title);
		//단락추가
		document.add(new Paragraph("\r\n"));


		//테이블생성 생성자에 컬럼수를 써준다
		PdfPTable table = new PdfPTable(2);
		//각각의 컬럼에 width너비 세팅
		table.setWidths(new int[]{120,120});
		table.getRowHeight(100);

		//컬럼 타이틀
		PdfPCell title1 = new PdfPCell(new Paragraph("카테고리번호",font));
		PdfPCell title2 = new PdfPCell(new Paragraph("카테고리이름",font));

		//타이틀 컬럼 바탕색 설정
		title1.setBackgroundColor(BaseColor.GRAY);
		title2.setBackgroundColor(BaseColor.GRAY);

		//가로정렬
		title1.setHorizontalAlignment(Element.ALIGN_CENTER);
		title2.setHorizontalAlignment(Element.ALIGN_CENTER);

		//세로정렬
		title1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title2.setVerticalAlignment(Element.ALIGN_MIDDLE);

		//테이블에 추가
		table.addCell(title1);
		table.addCell(title2);
		//셀에 책 전체 정보를 담기위한 List 생성
		ArrayList<CatVO> alr = cs.getAllCatList();
		//리스트 전체를 담기위한 반복문
		for(CatVO bv : alr) {
			//첫번째 셀에 책번호저장
			PdfPCell cell1 = new PdfPCell(new Paragraph(bv.getCatId() +"",font));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			//테이블에 첫번째 셀 저장
			table.addCell(cell1);
			PdfPCell cell2 = new PdfPCell(new Paragraph(bv.getCatName() +"",font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
		}


		//PDF문서에 만든 테이블 추가
		document.add(table);
		//PDF종료
		document.close();
	}


	private static void rentListPdf() throws DocumentException, IOException {
		IRentService rs = IRentServiceImpl.getInstance();

		//맑은고딕 폰트와 폰트 경로
		String fontname = "c:\\Windows\\Fonts\\MALGUN.ttf";
		//저장될 파일경로 , 파일이름
		//				String filename = "C:\\새 폴더\\test001.pdf";
		String filename = "d:\\대덕도서관 책 대여 조회.pdf";

		// 용지 및 여백 설정(좌, 우 , 상, 하)
		Document document = new Document(PageSize.A4, 0, 0, 20, 20); 

		//발생하는 예외 main()처리
		PdfWriter.getInstance(document, new FileOutputStream(filename)); 

		//document를 열어 PDF문서를 사용할 수 있도록 한다.
		document.open();

		//한글폰트를 사용하기 위한 셋팅
		BaseFont bfont = BaseFont.createFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		//(폰트, 폰트크기)
		Font font = new Font(bfont, 12);
		Font font2  = new Font(bfont, 14, Font.BOLD);

		//타이틀 단락추가
		Paragraph title = new Paragraph("대덕도서관 책 대여 목록",font2);
		//타이틀 중앙정렬
		title.setAlignment(Element.ALIGN_CENTER);

		//PDF문서 title 추가
		document.add(title);
		//단락추가
		document.add(new Paragraph("\r\n"));


		//테이블생성 생성자에 컬럼수를 써준다
		PdfPTable table = new PdfPTable(5);
		//각각의 컬럼에 width너비 세팅
		table.setWidths(new int[]{40,90,90,60,50});
		table.getRowHeight(100);

		//컬럼 타이틀
		PdfPCell title1 = new PdfPCell(new Paragraph("대여번호",font));
		PdfPCell title2 = new PdfPCell(new Paragraph("대여날짜",font));
		PdfPCell title3 = new PdfPCell(new Paragraph("반납예정일자",font));
		PdfPCell title4 = new PdfPCell(new Paragraph("회원아이디",font));
		PdfPCell title5 = new PdfPCell(new Paragraph("책번호",font));

		//타이틀 컬럼 바탕색 설정
		title1.setBackgroundColor(BaseColor.GRAY);
		title2.setBackgroundColor(BaseColor.GRAY);
		title3.setBackgroundColor(BaseColor.GRAY);
		title4.setBackgroundColor(BaseColor.GRAY);
		title5.setBackgroundColor(BaseColor.GRAY);

		//가로정렬
		title1.setHorizontalAlignment(Element.ALIGN_CENTER);
		title2.setHorizontalAlignment(Element.ALIGN_CENTER);
		title3.setHorizontalAlignment(Element.ALIGN_CENTER);
		title4.setHorizontalAlignment(Element.ALIGN_CENTER);
		title5.setHorizontalAlignment(Element.ALIGN_CENTER);

		//세로정렬
		title1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title5.setVerticalAlignment(Element.ALIGN_MIDDLE);

		//테이블에 추가
		table.addCell(title1);
		table.addCell(title2);
		table.addCell(title3);
		table.addCell(title4);
		table.addCell(title5);
		//셀에 책 전체 정보를 담기위한 List 생성
		ArrayList<RentVO> alr = rs.getAllRentList();
		//리스트 전체를 담기위한 반복문
		for(RentVO bv : alr) {
			//첫번째 셀에 책번호저장
			PdfPCell cell1 = new PdfPCell(new Paragraph(bv.getRentId() +"",font));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			//테이블에 첫번째 셀 저장
			table.addCell(cell1);
			PdfPCell cell2 = new PdfPCell(new Paragraph(bv.getRentDate() +"",font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			PdfPCell cell3 = new PdfPCell(new Paragraph(bv.getScheduleDate() +"",font));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);
			PdfPCell cell4 = new PdfPCell(new Paragraph(bv.getMemId() +"",font));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);
			PdfPCell cell5 = new PdfPCell(new Paragraph(bv.getBookId() +"",font));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);
		}


		//PDF문서에 만든 테이블 추가
		document.add(table);
		//PDF종료
		document.close();
	}


	private static void memberListPdf() throws DocumentException, IOException {
		IMemberService ms = IMemberServiceImpl.getInstance();

		//맑은고딕 폰트와 폰트 경로
		String fontname = "c:\\Windows\\Fonts\\MALGUN.ttf";
		//저장될 파일경로 , 파일이름
		String filename = "d:\\대덕도서관 회원 조회.pdf";
		// 용지 및 여백 설정(좌, 우 , 상, 하)
		Document document = new Document(PageSize.A4, 0, 0, 20, 20); 

		//발생하는 예외 main()처리
		PdfWriter.getInstance(document, new FileOutputStream(filename)); 

		//document를 열어 PDF문서를 사용할 수 있도록 한다.
		document.open();

		//한글폰트를 사용하기 위한 셋팅
		BaseFont bfont = BaseFont.createFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		//(폰트, 폰트크기)
		Font font = new Font(bfont, 12);
		Font font2  = new Font(bfont, 14, Font.BOLD);

		//타이틀 단락추가
		Paragraph title = new Paragraph("대덕도서관 전체 회원 조회",font2);
		//타이틀 중앙정렬
		title.setAlignment(Element.ALIGN_CENTER);

		//PDF문서 title 추가
		document.add(title);
		//단락추가
		document.add(new Paragraph("\r\n"));


		//테이블생성 생성자에 컬럼수를 써준다
		PdfPTable table = new PdfPTable(6);
		//각각의 컬럼에 width너비 세팅
		table.setWidths(new int[]{80,80,60,60,60,60});
		table.getRowHeight(100);

		//컬럼 타이틀
		PdfPCell title1 = new PdfPCell(new Paragraph("회원ID",font));
		PdfPCell title2 = new PdfPCell(new Paragraph("비밀번호",font));
		PdfPCell title3 = new PdfPCell(new Paragraph("회원이름",font));
		PdfPCell title4 = new PdfPCell(new Paragraph("마일리지",font));
		PdfPCell title5 = new PdfPCell(new Paragraph("블랙리스트",font));
		PdfPCell title6 = new PdfPCell(new Paragraph("관리자여부",font));

		//타이틀 컬럼 바탕색 설정
		title1.setBackgroundColor(BaseColor.GRAY);
		title2.setBackgroundColor(BaseColor.GRAY);
		title3.setBackgroundColor(BaseColor.GRAY);
		title4.setBackgroundColor(BaseColor.GRAY);
		title5.setBackgroundColor(BaseColor.GRAY);
		title6.setBackgroundColor(BaseColor.GRAY);

		//가로정렬
		title1.setHorizontalAlignment(Element.ALIGN_CENTER);
		title2.setHorizontalAlignment(Element.ALIGN_CENTER);
		title3.setHorizontalAlignment(Element.ALIGN_CENTER);
		title4.setHorizontalAlignment(Element.ALIGN_CENTER);
		title5.setHorizontalAlignment(Element.ALIGN_CENTER);
		title6.setHorizontalAlignment(Element.ALIGN_CENTER);

		//세로정렬
		title1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title6.setVerticalAlignment(Element.ALIGN_MIDDLE);

		//테이블에 추가
		table.addCell(title1);
		table.addCell(title2);
		table.addCell(title3);
		table.addCell(title4);
		table.addCell(title5);
		table.addCell(title6);
		//셀에 책 전체 정보를 담기위한 List 생성
		ArrayList<MemberVO> alm = ms.getAllMemberList();
		//리스트 전체를 담기위한 반복문
		for(MemberVO bv : alm) {
			//첫번째 셀에 책번호저장
			PdfPCell cell1 = new PdfPCell(new Paragraph(bv.getMemId() +"",font));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			//테이블에 첫번째 셀 저장
			table.addCell(cell1);
			PdfPCell cell2 = new PdfPCell(new Paragraph(bv.getMemPw() +"",font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			PdfPCell cell3 = new PdfPCell(new Paragraph(bv.getMemName() +"",font));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);
			PdfPCell cell4 = new PdfPCell(new Paragraph(bv.getMemMileage() +"",font));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);
			if(bv.isMemBlack()==false) {
				PdfPCell cell5 = new PdfPCell(new Paragraph("",font));
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell5);
			} else {
				PdfPCell cell5 = new PdfPCell(new Paragraph("BLACK",font));
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell5);
			}

			if(bv.isAdmin()==false) {
				PdfPCell cell6 = new PdfPCell(new Paragraph("일반회원",font));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell6);
			} else {
				PdfPCell cell6 = new PdfPCell(new Paragraph("관리자",font));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell6);
			}
		}

		//PDF문서에 만든 테이블 추가
		document.add(table);
		//PDF종료
		document.close();
	}


	private static void bookListPdf() throws DocumentException, IOException {
		IBookService bs = IBookServiceImpl.getInstance();

		//맑은고딕 폰트와 폰트 경로
		String fontname = "c:\\Windows\\Fonts\\MALGUN.ttf";
		//저장될 파일경로 , 파일이름
		//				String filename = "C:\\새 폴더\\test001.pdf";
		String filename = "d:\\대덕도서관 책 전제 조회.pdf";

		// 용지 및 여백 설정(좌, 우 , 상, 하)
		Document document = new Document(PageSize.A4, 0, 0, 20, 20); 

		//발생하는 예외 main()처리
		PdfWriter.getInstance(document, new FileOutputStream(filename)); 

		//document를 열어 PDF문서를 사용할 수 있도록 한다.
		document.open();

		//한글폰트를 사용하기 위한 셋팅
		BaseFont bfont = BaseFont.createFont(fontname, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		//(폰트, 폰트크기)
		Font font = new Font(bfont, 12);
		Font font2  = new Font(bfont, 14, Font.BOLD);

		//타이틀 단락추가
		Paragraph title = new Paragraph("대덕도서관 전체 책 목록 조회",font2);
		//타이틀 중앙정렬
		title.setAlignment(Element.ALIGN_CENTER);

		//PDF문서 title 추가
		document.add(title);
		//단락추가
		document.add(new Paragraph("\r\n"));


		//테이블생성 생성자에 컬럼수를 써준다
		PdfPTable table = new PdfPTable(7);
		//각각의 컬럼에 width너비 세팅
		table.setWidths(new int[]{40,100,80,60,50,50,50});
		table.getRowHeight(100);

		//컬럼 타이틀
		PdfPCell title1 = new PdfPCell(new Paragraph("책번호",font));
		PdfPCell title2 = new PdfPCell(new Paragraph("책이름",font));
		PdfPCell title3 = new PdfPCell(new Paragraph("출판사",font));
		PdfPCell title4 = new PdfPCell(new Paragraph("저자",font));
		PdfPCell title5 = new PdfPCell(new Paragraph("카테고리",font));
		PdfPCell title6 = new PdfPCell(new Paragraph("대여여부",font));
		PdfPCell title7 = new PdfPCell(new Paragraph("대여횟수",font));

		//타이틀 컬럼 바탕색 설정
		title1.setBackgroundColor(BaseColor.GRAY);
		title2.setBackgroundColor(BaseColor.GRAY);
		title3.setBackgroundColor(BaseColor.GRAY);
		title4.setBackgroundColor(BaseColor.GRAY);
		title5.setBackgroundColor(BaseColor.GRAY);
		title6.setBackgroundColor(BaseColor.GRAY);
		title7.setBackgroundColor(BaseColor.GRAY);

		//가로정렬
		title1.setHorizontalAlignment(Element.ALIGN_CENTER);
		title2.setHorizontalAlignment(Element.ALIGN_CENTER);
		title3.setHorizontalAlignment(Element.ALIGN_CENTER);
		title4.setHorizontalAlignment(Element.ALIGN_CENTER);
		title5.setHorizontalAlignment(Element.ALIGN_CENTER);
		title6.setHorizontalAlignment(Element.ALIGN_CENTER);
		title7.setHorizontalAlignment(Element.ALIGN_CENTER);

		//세로정렬
		title1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		title7.setVerticalAlignment(Element.ALIGN_MIDDLE);

		//테이블에 추가
		table.addCell(title1);
		table.addCell(title2);
		table.addCell(title3);
		table.addCell(title4);
		table.addCell(title5);
		table.addCell(title6);
		table.addCell(title7);
		//셀에 책 전체 정보를 담기위한 List 생성
		ArrayList<BookVO> alb = bs.getAllBookList();
		//리스트 전체를 담기위한 반복문
		for(BookVO bv : alb) {
			//첫번째 셀에 책번호저장
			PdfPCell cell1 = new PdfPCell(new Paragraph(bv.getBookId() +"",font));
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			//테이블에 첫번째 셀 저장
			table.addCell(cell1);
			PdfPCell cell2 = new PdfPCell(new Paragraph(bv.getName() +"",font));
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell2);
			PdfPCell cell3 = new PdfPCell(new Paragraph(bv.getPublish() +"",font));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell3);
			PdfPCell cell4 = new PdfPCell(new Paragraph(bv.getAuthor() +"",font));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell4);
			PdfPCell cell5 = new PdfPCell(new Paragraph(bv.getCatId() +"",font));
			cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell5);
			if(bv.isRentable()==false) {
				PdfPCell cell6 = new PdfPCell(new Paragraph("대여불가",font));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell6);
			} else {
				PdfPCell cell6 = new PdfPCell(new Paragraph("대여가능",font));
				cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell6);
			}
			PdfPCell cell7 = new PdfPCell(new Paragraph(bv.getRentCount() +"",font));
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell7);
		}


		//PDF문서에 만든 테이블 추가
		document.add(table);
		//PDF종료
		document.close();

	}

}

