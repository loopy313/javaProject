package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import pdf.Pdf;

import com.itextpdf.text.DocumentException;

import rent.service.IRentService;
import rent.service.IRentServiceImpl;
import vo.BookVO;
import vo.CatVO;
import vo.MemberVO;
import vo.RentVO;
import mail.Mail;
import member.service.IMemberService;
import member.service.IMemberServiceImpl;
import category.service.ICatService;
import category.service.ICatServiceImpl;
import database.DBClass;
import book.service.IBookService;
import book.service.IBookServiceImpl;

public class View {
	private IBookService bs = IBookServiceImpl.getInstance();
	private ICatService cs = ICatServiceImpl.getInstance();
	private IMemberService ms = IMemberServiceImpl.getInstance();
	private IRentService rs = IRentServiceImpl.getInstance();
	private DBClass db = DBClass.getInstance();
	private static MemberVO memInfo;
	private static boolean debugmode = false;
	
	/**
	 * 프로그램 시작하기 1.회원가입 -> 회원가입 화면 2.로그인 -> 로그인 화면
	 */
	public void start() {
		while (true) {
			System.out.println("┌───────────────────────────────┐");
			System.out.println("│\t    대덕 도서관 입장   \t\t│");
			System.out.println("│\t  1. 회원 가입\t\t│");
			System.out.println("│\t  2. 로그인\t\t│");
			System.out.println("└───────────────────────────────┘");

			int val = inputInt();
			if (!isRangeValidate(val, 1, 3)) {
				continue;
			}
			switch (val) {
			case 1:
				signUp();
				break;
			case 2:
				signIn();
				break;
			default:
				continue;
			}
		}
	}

	/**
	 * 로그아웃하기
	 */
	private void logout() {
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t    로그아웃\t\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		memInfo = null;
	}

	/**
	 * 로그인 화면 관리자 로그인 -> 관리자화면 이동 사용자 로그인 -> 사용자화면 이동
	 */
	private void signIn() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("┌───────────────────────────────┐");
			System.out.println("│\t대덕 도서관 로그인\t\t│");
			System.out.println("└───────────────────────────────┘");
			System.out.print("▷ 아이디 입력 : ");
			String id = scan.next();
			System.out.print("▷ 패스워드 입력 : ");
			String pw = scan.next();
			Map<String, String> param = new HashMap<String, String>();
			param.put("memId", id);
			param.put("memPw", pw);
			memInfo = ms.authorize(param);
			if (memInfo.isAdmin()) {
				System.out.print("관리자 이메일 주소 입력 : ");
				String adminAddr=scan.next();
				System.out.print("관리자 이메일 패스워드 입력 : ");
				String adminPw=scan.next();
				Mail.getInstance().setFrom(adminAddr, adminPw);
			}
			if (memInfo != null) {
				System.out.println("로그인 성공");
				break;
			}
		}
			if (memInfo.isAdmin()) {
				adminService();
			} else {
				userService();
			}
	}

	/**
	 * 회원가입 화면 아이디 입력은 중복확인 필요하므로 메서드로 처리
	 */
	private void signUp() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		System.out.println("┌───────────────────────────────┐");
		System.out.println("│\t대덕 도서관 회원 가입	\t│");
		System.out.println("└───────────────────────────────┘");

		while (true) {
			String id = inputId();
			System.out.println();
			String pw = inputPw();
			System.out.println();
			System.out.print("▷ 이름 입력 : ");
			String name = scan.next();

			String email = inputEmail();
			boolean isAdmin=false;
			//관리자가 회원가입시 권한부여 기능 추가.
			if(memInfo!=null && memInfo.isAdmin()){
				while (true) {
					System.out.println("▷ 권한 부여(1:관리자 2:회원)");
					int val = inputInt();
					if (isRangeValidate(val, 1, 3)) {
						if (val == 1) {
							isAdmin = true;
						} else {
							isAdmin = false;
						}
						break;
					} else {
						continue;
					}
				}
			}
			MemberVO mv = setMemberVO(id, pw, name, email, isAdmin);
			if (ms.insertMember(mv)) {
				System.out.println("회원 가입 완료.");
				System.out.println("\n--------정보 확인--------");
				System.out.println("ID\tpw\t이름\t메일주소\t\t\t권한");
				System.out.printf("%s\t%s\t%s\t%15s%10s \n\n",id,pw,name,email,isAdmin?"관리자":"회원");
				break;
			} else {
				System.out.println("DB 입력 실패. 다시 입력 해주세요.");
			}
		}
	}

	private String inputEmail() {
		@SuppressWarnings("resource")
		Scanner scan=new Scanner(System.in);
		String email=null;
		//
		Pattern p=Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@((naver\\.com)|(gmail\\.com))$");
		System.out.println("▷ 이메일 입력시 신간 도서 알림 메일을 받을 수 있습니다.");
		System.out.println("▷ 이메일을 입력하시겠습니까?");
		while(true){
			System.out.print("▷ 이메일 입력(원하지 않으면 엔터) : ");
			email=scan.nextLine();
			email.replace("\t\n", "");

			if(email.isEmpty() || p.matcher(email).find()){
				break;
			}else{
				System.out.println("잘못된 메일 형식입니다. 다시 입력해주세요.");
				continue;
			}
		}
		return email;
	}

	/**
	 * 해당 메뉴 계속 하시겠습니까?
	 * 
	 * @return 계속 진행 여부
	 */
	boolean conOrnot() {
		boolean outflag = false;
		while (true) {
			int val = inputInt();
			if (isRangeValidate(val, 1, 3)) {
				if (val == 1)
					break;
				else {
					outflag = true;
					break;
				}
			} else {
				continue;
			}
		}
		return outflag;
	}

	/**
	 * MemberVO 생성 MemberVO 생성 후, DB에 insert 작업할 메서드 호출
	 */
	private MemberVO setMemberVO(String id, String pw, String name, String email, boolean isAdmin) {
		MemberVO mv = new MemberVO();
		mv.setMemId(id);
		mv.setMemPw(pw);
		mv.setMemName(name);
		mv.setEmail(email);
		mv.setAdmin(isAdmin);
		return mv;
	}

	/**
	 * 아이디 입력받기 아이디 중복체크 메서드 포함하고 있음
	 * 
	 * @return 입력받은 아이디
	 */
	private String inputId() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String id;
		while (true) {
			System.out.print("1.아이디 입력 : ");
			id = scan.next();
			if (ms.isEqualMemId(id)) {
				System.out.println(" 이미 존재하는 아이디 입니다.");
			} else {
				break;
			}
		}
		return id;
	}

	/**
	 * 비밀번호 입력받기 정규식 포함하고 있음
	 * 
	 * @return 입력받은 비밀번호
	 */
	private String inputPw() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		String pw;
		while (true) {
			System.out
			.print("▷ 패스워드 입력 (6자 이상 12글자 이하, 영문 대 소문자, 숫자, 특수문자를 사용하세요.): ");
			pw = scan.next();

			if (ms.isPossiblePw(pw)) {
				return pw;
			} else {
				System.out.println("패스워드 패턴 오류.");
				continue;
			}
		}
	}

	/**
	 * 관리자 메인 화면 회원관리,도서관리,도서통계 서비스 제공
	 */
	private void adminService() {
		while (true) {
			System.out
			.println("┌───────────────────────────────────────────────────────────┐");
			System.out.println("│     1.회원관리  2.도서관리  3.도서통계  4.PDF 5.엑셀저장  6.로그아웃\t    │");
			System.out
			.println("└───────────────────────────────────────────────────────────┘");

			int val = inputInt();
			switch (val) {
			case 1:
				ad_MemManage();
				break;
			case 2:
				ad_BookManage();
				break;
			case 3:
				ad_StatBook();
				break;
			case 4:
				try {
					Pdf.main(null);
				} catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
				System.out.println("저장완료");
				continue;
			case 5:
				db.ce.createExcell(db);
				break;
			case 6:
				logout();
				return;
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
		}
	}

	/**
	 * 회원관리화면 1.사용자정보 2.블랙리스트관리 3.회원가입(권한부여) 4.뒤로가기 메뉴가 있음
	 */
	private void ad_MemManage() {
		System.out
		.println("┌───────────────────────────────────────────────────────────┐");
		System.out.println("│\t\t\t       회원관리\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────────────┘");
		while (true) {
			System.out.println("│\t1.사용자정보   2.블랙리스트 관리   3.회원가입(권한부여) 4.뒤로가기\t    │");
			System.out
			.println("└───────────────────────────────────────────────────────────┘");
			int val = inputInt();
			switch (val) {
			case 1:
				mm_MemInfo();
				continue;
			case 2:
				mm_BlackList();
				continue;
			case 3:
				signUp();
				break;
			case 4:
				break;
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
			break;
		}

	}

	/**
	 * 사용자 정보 관리화면 사용자 정보 목록을 5개씩 끊어서 보여주기
	 */
	private void mm_MemInfo() {
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t       사용자정보\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		int cnt = 1;
		System.out.println("아이디\t|패스워드\t|이름\t|마일리지\t|블랙여부\t|관리자\t|");
		System.out.println("------------------------------------------------");
		for (MemberVO mv : ms.getAllMemberList()) {
			System.out.print(mv.getMemId() + "\t|");
			System.out.print(" " + mv.getMemPw() + "\t|");
			System.out.print(" " + mv.getMemName() + "\t|");
			System.out.print(" " + mv.getMemMileage() + "\t|");
			System.out.print(" " + (mv.isMemBlack() ? "O" : "X") + "\t|");
			System.out.println(" " + (mv.isAdmin() ? "O" : "X") + "\t|");
			System.out
			.println("------------------------------------------------");
			if (cnt % 5 == 0) {
				System.out.println("---Enter---");
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				scan.nextLine();
			}
			cnt++;
		}

	}

	/**
	 * 블랙리스트 관리 화면 DB로부터 블랙리스트 회원 select
	 */
	private void mm_BlackList() {
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t       블랙리스트관리\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		System.out.println("아이디\t\t|이름\t\t|마일리지");
		System.out
		.println("--------------------------------------------------");
		int cnt = 1;
		for (MemberVO mv : ms.getMemberbyBlack()) {
			System.out.print(mv.getMemId() + "\t\t|");
			System.out.print(mv.getMemName() + "\t\t|");
			System.out.print(mv.getMemMileage() + "\n");
			waitFunc(cnt);
			cnt++;
		}
	}

	/**
	 * 도서관리화면 12월07일 오후3시 break를 return으로 수정 12월07일 오후7시 '4.도서 수정' 추가
	 */
	private void ad_BookManage() {
		System.out
		.println("┌───────────────────────────────────────────────────────┐");
		System.out.println("│\t\t           도서관리\t\t\t        │");
		System.out
		.println("└───────────────────────────────────────────────────────┘");
		while (true) {
			System.out
			.println("┌───────────────────────────────────────────────────────┐");
			System.out.println("│\t1.신규 등록  2.도서 삭제  3.대여관리 4.도서 수정 5.뒤로가기\t│");
			System.out
			.println("└───────────────────────────────────────────────────────┘");
			switch (inputInt()) {
			case 1:
				bm_AddBook();
				continue;
			case 2:
				bm_DeleteBook();
				continue;
			case 3:
				bm_RentManage();
				continue;
			case 4:
				bm_UpdateBook();
				break;
			case 5:
				// break;
				return;
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
		}
	}

	/**
	 * 도서수정하기
	 * 
	 */
	private void bm_UpdateBook() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		ArrayList<BookVO> bv = bs.getAllBookList();
		for (BookVO list : bv) {
			System.out.println(list.getBookId() + "\t" + list.getName() + "\t"
					+ list.getPublish() + "\t" + list.getAuthor() + "\t"
					+ list.getCatId());
		}

		System.out.print("수정할 책번호 입력: ");
		int idx = Integer.parseInt(scan.nextLine());

		System.out.print("이름(수정을 원하지 않으면 엔터) :");
		String str = scan.nextLine();
		bs.updateBookName(idx, str);

		System.out.print("출판사(수정을 원하지 않으면 엔터) :");
		str = scan.nextLine();
		bs.updateBookPublisher(idx, str);

		System.out.print("저자(수정을 원하지 않으면 엔터) :");
		str = scan.nextLine();
		bs.updateBookAuthor(idx, str);
	}

	/**
	 * 도서 신규 등록하기
	 */
	private void bm_AddBook() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.print("책 이름 입력 : ");
			String Name = scan.next();
			System.out.print("출판사 입력 : ");
			String publish = scan.next();
			System.out.print("작가 입력 : ");
			String author = scan.next();
			showCatNames();
			int catId = inputCatId();

			BookVO bv = setBookVO(bs.getLastBooktId() + 1, Name, publish,
					author, catId, true);
			if (bs.insertBook(bv)) {
				insertBooktoFile(bv);
				showAddedBook(bv);
				System.out.println("▷계속 등록하시겠습니까?(1:예 2:아니오)");
				boolean outflag = conOrnot();
				if (outflag == true) {
					sendMail();
					return;
				}
			} else {
				System.out.println("DB 입력 실패. 다시 입력 해주세요.");
				continue;
			}
		}
	}

	public void sendMail() {
		ArrayList<String> al=new ArrayList<String>();
		for(MemberVO mv:ms.getAllMemberList()){
			if(mv.getEmail()!=null&&!mv.getEmail().isEmpty()){
				al.add(mv.getEmail());
			}
		}
		if(al.isEmpty()){
			System.out.println("이메일을 등록한 회원이 없습니다.");
			return;
		}
		ArrayList<File> alf=new ArrayList<File>();
		alf.add(new File(System.getProperty("user.dir")+"/신간도서"));
		Mail.getInstance().addFile(alf);
		
		Mail.getInstance().sendMail(al, "신간도서 목록", "신간도서 목록입니다");
	}

	private void insertBooktoFile(BookVO bv) {
		try {
			FileWriter fw=new FileWriter(System.getProperty("user.dir")+"/신간도서",true);
			BufferedWriter bos=new BufferedWriter(fw);
			bos.append(bv.toString());
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 카테고리 입력하기
	 * 
	 * @return 입력받은 카테고리 아이디
	 */
	private int inputCatId() {
		int catId;
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("카테고리 입력 : ");
				catId = scan.nextInt();
				if (isRangeValidate(catId, 0, cs.countAllCat())) {
					return catId;
				} else {
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				scan.next();
				continue;
			}
		}
	}

	/**
	 * 추가된 서적 보여주기
	 * 
	 * @param bv
	 *            - 추가된 서적
	 */
	private void showAddedBook(BookVO bv) {
		System.out.println("서적이 추가되었습니다.");
		System.out.println("--------등록 정보 확인--------");
		System.out.println("카테고리\t서적명\t글쓴이\t출판사\t대여가능여부");
		System.out.println(bv.getCatId() + "\t" + bv.getName() + "\t"
				+ bv.getAuthor() + "\t" + bv.getPublish() + "\t"
				+ bv.isRentable());
	}

	/**
	 * 카테고리 이름 보여주기
	 */
	private void showCatNames() {
		for (CatVO cv : cs.getAllCatList()) {
			System.out.print(cv.getCatId() + ":" + cv.getCatName() + "|");
		}
		System.out.println();
	}

	/**
	 * BookVO에 추가된 서적 추가하기
	 * 
	 * @param b
	 *            - bookId
	 * @param n
	 *            - 책이름
	 * @param p
	 *            - 출판사 이름
	 * @param a
	 *            - 저자 이름
	 * @param c
	 *            - 카테고리id
	 * @param r
	 *            - 대여가능여부
	 * @return
	 */
	private BookVO setBookVO(int b, String n, String p, String a, int c,
			boolean r) {
		BookVO bv = new BookVO();
		bv.setBookId(b);
		bv.setName(n);
		bv.setPublish(p);
		bv.setAuthor(a);
		bv.setCatId(c);
		bv.setRentable(r);
		return bv;
	}

	/**
	 * 서적 삭제 화면 대여중인 책은 삭제 불가
	 */
	private void bm_DeleteBook() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		ArrayList<Integer> removableIdList;
		while (true) {
			System.out.print("삭제할 책이름 검색 : ");
			String name = scan.next();
			removableIdList = showDeletableBooks(name);
			if (removableIdList == null || removableIdList.isEmpty()) {
				System.out.println("삭제 가능한 서적이 없습니다. 다른 서적을 입력하세요.");
				continue;
			}
			System.out.print("삭제를 원하는 ID 입력 : ");
			try {
				int bid = scan.nextInt();
				if (isRandValidate(bid, removableIdList)) {
					if (bs.deleteBook(bs.getBookbyBookId(bid))) {
						System.out.println("삭제 완료.");
						System.out.println("▷계속 삭제하시겠습니까?(1:예 2:아니오)");
						boolean outflag = conOrnot();
						if (outflag) {
							return;
						}
					} else {
						System.out.println("DB 입력 실패. 다시 입력 해주세요.");
						continue;
					}
				} else {
					System.out.println("유효하지 않은 입력. 다시 입력하세요.");
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				scan.next();
				continue;
			}
		}
	}

	/**
	 * 관리자가 도서 삭제할 때, 입력받은 삭제할 책 이름에 해당되는 도서목록 보여주기
	 * 
	 * @param name
	 *            삭제할 책 이름
	 * @return 삭제할 책 목록
	 */
	private ArrayList<Integer> showDeletableBooks(String name) {
		ArrayList<BookVO> bvl = bs.getRentableBookByName(name);
		if (bvl == null || bvl.isEmpty()) {
			return null;
		} else {
			ArrayList<Integer> il = new ArrayList<Integer>();
			System.out
			.println("┌───────────────────────────────────────────────────────────────────────────────────┐");
			System.out
			.println("│  책ID\t카테고리\t대여상태\t책 이름\t\t출판사\t\t글쓴이\t\t\t    │");
			System.out
			.println("└───────────────────────────────────────────────────────────────────────────────────┘");
			for (BookVO bv : bvl) {
				il.add(bv.getBookId());
				System.out.print(bv.getBookId() + "\t");
				System.out.print(bv.getCatId() + "\t");
				System.out.print((bv.isRentable() ? "대여가능" : "대여중") + "\t");
				System.out.print(bv.getName() + "\t");
				System.out.print(bv.getPublish() + "\t");
				System.out.println(bv.getAuthor());
				System.out
				.println("--------------------------------------------------------------");
			}
			return il;
		}
	}

	/**
	 * 대여관리 화면 대여목록 전체 출력
	 */
	private void bm_RentManage() {
		ArrayList<RentVO> rvl = rs.getAllRentList();
		System.out
		.println("┌──────────────────────────────────────────────────────────────────────────────────┐");
		System.out
		.println("│\t대여Id\t│\t 대여일자\t     │\t\t   책이름\t       │\t회원이름   │");
		System.out
		.println("└──────────────────────────────────────────────────────────────────────────────────┘");
		int cnt = 1;
		for (RentVO rv : rvl) {
			System.out.print("\t" + rv.getRentId() + "\t");
			System.out.print("\t" + rv.getRentDate() + "\t");
			System.out.print("\t"
					+ bs.getBookbyBookId(rv.getBookId()).getName() + "\t");
			System.out.print("\t"
					+ ms.getMemberbyId(rv.getMemId()).getMemName() + "\n");
			System.out
			.println("----------------------------------------------------------------------------");
			waitFunc(cnt);
			cnt++;
		}
	}

	/**
	 * 도서통계화면 case 3 에서 break를 return으로 수정 (12월07일)
	 */
	private void ad_StatBook() {
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t        도서통계\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		while (true) {
			System.out.println("도서통계");
			System.out.println("1.독서왕Top3 | 2.많이 읽은 책Top3 | 3.나가기");
			switch (inputInt()) {
			case 1:
				sb_best3Reader();
				continue;
			case 2:
				sb_best3Book();
				continue;
			case 3:
				// break;
				return; // 안 나가져서 return으로 수정
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				break;
			}
		}
	}

	/**
	 * 베스트북 3권 보여주기 도서ID, 카테고리, 도서이름, 출판사, 글쓴이, 대여횟수 보여줌
	 */
	private void sb_best3Book() {
		System.out
		.println("┌───────────────────────────────────────────────────────────────────────────────────────┐");
		System.out
		.println("│\t도서ID\t카테고리\t   도서이름\t\t출판사\t글쓴이\t대여횟수    \t\t\t│");
		System.out
		.println("└───────────────────────────────────────────────────────────────────────────────────────┘");
		ArrayList<BookVO> bvl = bs.getTop3RankBook();
		for (BookVO bv : bvl) {
			System.out.print("\t" + bv.getBookId() + "\t"
					+ cs.getCatbyCatId(bv.getCatId()).getCatName() + "\t");
			System.out.print(bv.getName() + "  \t" + bv.getPublish() + "\t\t"
					+ bv.getAuthor() + "\t");
			System.out.print(bv.getRentCount());
			System.out.println();
		}
	}

	/**
	 * 독서왕 3명 보여주기 회원ID, 회원이름, 회원마일리지를 보여줌
	 */
	private void sb_best3Reader() {
		System.out
		.println("┌─────────────────────────────────────────────────┐");
		System.out.println("│\t회원ID\t회원이름\t회원마일리지\t\t  │");
		System.out
		.println("└─────────────────────────────────────────────────┘");
		ArrayList<MemberVO> mvl = ms.getTop3RankMember();
		for (MemberVO mv : mvl) {
			System.out.print("\t " + mv.getMemId() + "  \t" + mv.getMemName()
					+ "\t   " + mv.getMemMileage() + "\n");
		}
	}

	/**
	 * 사용자 메인 화면 대여,연장,반납,검색,조회 서비스 제공
	 */
	private void userService() {
		while (true) {
			System.out
			.println("┌───────────────────────────────────────────────┐");
			System.out.println("│\t1.대여  2.연장  3.반납  4.검색  5.조회 6.타자검정 7.로그아웃\t│");
			System.out
			.println("└───────────────────────────────────────────────┘");

			int val = inputInt();
			switch (val) {
			case 1:
				us_Rent();
				break;
			case 2:
				us_Extension();
				break;
			case 3:
				us_Return();
				break;
			case 4:
				us_Search();
				break;
			case 5:
				us_Inquiry();
				break;
			case 6:
				MFrame t = new MFrame();
				break;
			case 7:
				logout();
				return;
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
		}
	}

	/**
	 * 도서대여화면
	 */
	private void us_Rent() {
		System.out.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t        도서대여\t\t\t    │");
		System.out.println("└───────────────────────────────────────────────────┘");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		ArrayList<Integer> rentableIdList;
		int bookId;
		while (true) {
			System.out.println("대여할 책을 검색해주세요");
			System.out.print("대여할 책 이름 입력 : ");
			String name = scan.next();
			rentableIdList = showSearchBook(name);
			if (rentableIdList == null || rentableIdList.isEmpty()) {
				System.out.println("대여할 서적이 없거나 대여중입니다");
				continue;
			}
			System.out.print("대여할 책id 입력 :");
			try {
				bookId = scan.nextInt();
				if (isRandValidate(bookId, rentableIdList)) {
					RentVO rv = setRentVo(memInfo.getMemId(), bookId,
							rs.getLastRentId() + 1, getCurrentDate(),
							getScheduledDate());
					if (rs.insertRent(rv)) {
						bs.updateRentCount(bookId);
						bs.updateRentable(bookId);
						System.out.println("대여되었습니다");
						System.out.println("▷계속 대여하시겠습니까?(1:예 2:아니오)");
						boolean outflag = conOrnot();
						if (outflag == true) {
							return;
						}
					} else {
						System.out.println("DB 입력 실패. 다시 입력 해주세요.");
						continue;
					}
				} else {
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				scan.next();
				continue;
			}
		}
	}

	/**
	 * 대여한 서적 rentVO에 추가하기
	 * 
	 * @param memId
	 *            - 로그인한 사용자 id
	 * @param bookId
	 *            - 대여할 서적 id
	 * @param rentId
	 *            - rentVO에 있는 마지막 도서의 rentId + 1
	 * @param rentDate
	 *            - 대여한 당일 날짜
	 * @param scheduleDate
	 *            - 대여한 당일 날짜 + 14
	 * @return
	 */
	private RentVO setRentVo(String memId, int bookId, int rentId,
			String rentDate, String scheduleDate) {
		RentVO rv = new RentVO();
		rv.setMemId(memId);
		rv.setBookId(bookId);
		rv.setRentId(rentId);
		rv.setRentDate(rentDate);
		rv.setScheduleDate(scheduleDate);
		return rv;
	}

	/**
	 * 도서삭제 시, 입력받은 '삭제할 책이름'에 해당되는 서적 보여주기
	 * 
	 * @param name
	 *            - 삭제할 책이름
	 * @return 삭제할 책이름에 해당하는 서적 목록
	 */
	private ArrayList<Integer> showSearchBook(String name) {
		ArrayList<BookVO> bvl = bs.getBookbyName(name);
		ArrayList<Integer> il = new ArrayList<Integer>();
		if (bvl == null || bvl.isEmpty()) {
			return null;
		}
		System.out
		.println("┌───────────────────────────────────────────────────────────────────────────────────┐");
		System.out.println("│  책ID\t카테고리\t대여상태\t책 이름\t\t출판사\t\t글쓴이\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────────────────────────────────────┘");
		for (BookVO bv : bvl) {
			System.out.print("   " + bv.getBookId() + "\t");
			System.out.print("  " + bv.getCatId() + "\t");
			System.out.print((bv.isRentable() ? "대여가능" : "대여불가") + "\t");
			System.out.print(bv.getName() + "\t\t");
			System.out.print(bv.getPublish() + "\t\t");
			System.out.println(bv.getAuthor());
			System.out
			.println("----------------------------------------------------------------------");
			if (bv.isRentable()) {
				il.add(bv.getBookId());
			}
		}
		return il;
	}

	/**
	 * 사용자 - 도서 연장 화면
	 */
	private void us_Extension() {
		if (debugmode) {
			memInfo = new MemberVO();
			memInfo.setMemId("a");
		}
		System.out
		.println("┌─────────────────────────────────────────────────────────────┐");
		System.out.println("│\t\t             도서연장\t\t\t         │");
		System.out
		.println("└─────────────────────────────────────────────────────────────┘");

		ArrayList<Integer> rentedIdList = new ArrayList<Integer>();
		ArrayList<RentVO> rvl = rs.getRentbyMemId(memInfo.getMemId());
		if (rvl == null || rvl.isEmpty()) {
			System.out.println("대여중인 서적이 없습니다.");
			return;
		}
		System.out.println("대여ID\t\t\t|서적이름\t\t\t|대여일\t\t\t|반납예정일\t\t\t");
		for (RentVO rv : rvl) {
			if (!(rs.isDelayed(rv.getRentId()))) {
				rentedIdList.add(rv.getRentId());
			}
			showExtensionBook(rv);
		}
		/*
		 * 연체시 연장 불가. 연장시 7일 추가.
		 */
		while (true) {
			System.out.println("연장을 원하는 대여ID 선택(연체서적은 불가)");
			int rid = inputInt();
			if (isRandValidate(rid, rentedIdList)) {
				if (!rs.isDelayed(rid)) { // 연체되지 않았다면
					rs.updateRentDate(rs.getRentbyRentId(rid));
					System.out.println("연장 완료.");
					System.out
					.println("대여ID\t\t\t|서적이름\t\t\t|대여일\t\t\t|반납예정일\t\t\t");
					showExtensionBook(rs.getRentbyRentId(rid));
					System.out.println("▷계속 다른 서적을 연장 하시겠습니까?(1:예 2:아니오)");
					boolean outflag = conOrnot();
					if (outflag) {
						return;
					}
				} else {
					System.out.println("DB 입력 실패. 다시 입력 해주세요.");
					continue;
				}
			} else {
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
			System.out.println("▷계속 다른 서적을 연장 하시겠습니까?(1:예 2:아니오)");
		}
	}

	/**
	 * 도서연장 시 대여한 책 보여주기 위해서 한 권씩 가져오기
	 * 
	 * @param rv
	 *            - 대여한 책
	 */
	void showExtensionBook(RentVO rv) {
		System.out.print(rv.getRentId() + "\t\t\t");
		System.out.print("  " + bs.getBookbyBookId(rv.getBookId()).getName()
				+ "\t\t");
		System.out.print(" " + rv.getRentDate() + "\t\t");
		System.out.print(" " + rv.getScheduleDate() + "\n");
		System.out
		.println("-------------------------------------------------------------------------------------------");
	}

	/**
	 * 사용자-도서반납 대여한 도서 목록 보여준 후 반납할 도서 id 입력시 반납해주기
	 */
	private void us_Return() {
		if (debugmode) {
			memInfo = new MemberVO();
			memInfo.setMemId("a");
		}
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t        도서반납\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		while (true) {
			ArrayList<Integer> rentIds = new ArrayList<Integer>();
			ArrayList<RentVO> rvl = rs.getRentbyMemId(memInfo.getMemId());
			if (rvl == null || rvl.isEmpty()) {
				System.out.println("반납할 서적이 없으므로 회원 메뉴로 돌아갑니다.");
				return;
			}
			System.out.println("대여ID\t\t\t|서적이름\t\t\t|대여일\t\t\t|반납예정일\t\t\t");
			for (RentVO rv : rvl) {
				rentIds.add(rv.getRentId());
				System.out.print(rv.getRentId() + "\t\t\t");
				System.out.print(bs.getBookbyBookId(rv.getBookId()).getName()
						+ "\t\t\t");
				System.out.print(rv.getRentDate() + "\t\t\t");
				System.out.print(rv.getScheduleDate() + "\n");
				System.out
				.println("------------------------------------------------------------------------------------------------------------");
			}

			int rid = inputInt();
			if (isRandValidate(rid, rentIds)) {

				if (rs.deleteRent(rs.getRentbyRentId(rid))) {
					bs.updateRentable(rs.getRentbyRentId(rid).getBookId());
					ms.updateMileage(rid);
					ms.updateBlackList(memInfo.getMemId());
					System.out.println("반납 완료.");
					System.out.println("▷계속 대여하시겠습니까?(1:예 2:아니오)");
					boolean outflag = conOrnot();
					if (outflag) {
						return;
					}
				} else {
					System.out.println("DB 입력 실패. 다시 입력 해주세요.");
					continue;
				}
			}
		}
	}

	/**
	 * 사용자-도서검색 제목, 저자, 출판사, 분류별로 검색하기
	 * 
	 */
	private void us_Search() {
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t        도서검색\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out
			.println("┌───────────────────────────────────────────────────────┐");
			System.out.println("│\t1.제목검색 2.저자검색 3.출판사검색 4.분류검색 5.나가기   \t│");
			System.out
			.println("└───────────────────────────────────────────────────────┘");
			ArrayList<BookVO> tmp;

			switch (inputInt()) {
			case 1:
				System.out
				.println("─────────────────────────────────────────────────────────");
				System.out.print("\t1.제목 : ");
				String title = scan.next();
				System.out
				.println("\n─────────────────────────────────────────────────────────");
				tmp = bs.getBookbyName(title);
				printBook(tmp);
				break;
			case 2:
				System.out
				.println("─────────────────────────────────────────────────────────");
				System.out.print("\t저자 : ");
				String writer = scan.next();
				System.out
				.println("\n─────────────────────────────────────────────────────────");
				tmp = bs.getBookbyAuthor(writer);
				printBook(tmp);
				break;
			case 3:
				System.out
				.println("─────────────────────────────────────────────────────────");
				System.out.print("\t출판사 : ");
				String publish = scan.next();
				System.out
				.println("\n─────────────────────────────────────────────────────────");
				tmp = bs.getBookbyPublish(publish);
				printBook(tmp);
				break;
			case 4:
				System.out
				.println("─────────────────────────────────────────────────────────");
				System.out.print("\t분류 : ");
				for (CatVO c : cs.getAllCatList()) {
					System.out.print(c.getCatId() + ":" + c.getCatName()
							+ " | ");
				}
				System.out.println("");
				int catId;
				while (true) {
					catId = inputInt();
					if (isRangeValidate(catId, 0, cs.countAllCat())) {
						break;
					} else {
						continue;
					}
				}
				System.out
				.println("\n─────────────────────────────────────────────────────────");
				tmp = bs.getBookbyCatId(catId);
				printBook(tmp);
				break;
			case 5:
				return;
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
		}
	}

	/**
	 * 사용자-검색한 책 목록 보여주기
	 * 
	 * @param bvl - 검색조건에 맞는 책 목록
	 *            
	 */
	private void printBook(ArrayList<BookVO> bvl) {
		if (bvl == null) {
			System.out.println("검색한 서적이 없습니다.");
			return;
		}
		System.out
		.println("┌───────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
		System.out
		.println("│  책ID\t카테고리\t대여상태\t책 이름\t\t출판사\t글쓴이\t\t\t\t\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
		int cnt = 1;
		for (BookVO bv : bvl) {
			System.out.print(bv.getBookId() + "\t" + bv.getCatId() + "\t"
					+ bv.getName() + "\t");
			System.out.print(bv.getPublish() + "\t" + bv.getAuthor() + "\n");
			waitFunc(cnt);
		}
	}

	/**
	 * 사용자-마이페이지 화면
	 */
	private void us_Inquiry() {
		System.out
		.println("┌───────────────────────────────────────────────────┐");
		System.out.println("│\t\t        마이페이지\t\t\t    │");
		System.out
		.println("└───────────────────────────────────────────────────┘");
		while (true) {
			System.out
			.println("┌───────────────────────────────────────────────────────┐");
			System.out.println("│\t\t1.사용자정보 2.대여정보 3.나가기   \t\t│");
			System.out
			.println("└───────────────────────────────────────────────────────┘");
			switch (inputInt()) {
			case 1:
				MemberVO mv = ms.getMemberbyId(memInfo.getMemId());
				System.out.println("아이디   회원이름   패스워드   마일리지 권한 ");
				System.out.print(mv.getMemId() + "\t");
				System.out.print(mv.getMemName() + "\t");
				System.out.print(mv.getMemPw() + "\t");
				System.out.print(mv.getMemMileage() + "\t");
				System.out.print(mv.isAdmin() ? "관리자" : "회원" + "\n");
				break;
			case 2:
				ArrayList<RentVO> rvl = rs.getRentbyMemId(memInfo.getMemId());
				if (rvl.isEmpty()) {
					System.out.println("대여 정보가 없습니다.");
					continue;
				}
				System.out
				.println("대여ID\t\t\t|서적이름\t\t\t|대여일\t\t\t|반납예정일\t\t\t");
				for (RentVO rv : rvl) {
					System.out.print(rv.getRentId() + "\t\t\t");
					System.out.print(bs.getBookbyBookId(rv.getBookId())
							.getName() + "\t\t\t");
					System.out.print(rv.getRentDate() + "\t\t\t");
					System.out.print(rv.getScheduleDate() + "\n");
					System.out
					.println("----------------------------------------------------------------------");
				}
				break;
			case 3:
				return;
			default:
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				continue;
			}
		}
	}

	/*
	 * ==========================================================================
	 * ==
	 */
	/**
	 * 해당 메뉴 계속 진행하고자 할 경우
	 * 
	 * @param val
	 * @param random
	 * @return
	 */
	boolean isRandValidate(int val, ArrayList<Integer> random) {
		for (int i : random) {
			if (i == val) {
				return true;
			}
		}
		System.out.println("유효하지 않은 입력. 다시 입력하세요.");
		return false;
	}

	/**
	 * 입력받은 권한부여 번호가 1과 2사이에 있는지 판단하기
	 * 
	 * 
	 * @param val  - 사용자가 입력한 번호
	 * @param first
	 * @param last
	 * @return
	 */
	boolean isRangeValidate(int val, int first, int last) {
		if (val >= first && val < last) {
			return true;
		}
		System.out.println("유효하지 않은 입력. 다시 입력하세요.");
		return false;
	}

	/**
	 * 사용자가 입력한 번호
	 * 
	 * @return
	 */
	private int inputInt() {
		int val;
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.print("▷번호 입력 : ");
			try {
				val = scan.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("유효하지 않은 입력. 다시 입력하세요.");
				scan.next();
				continue;
			}
			break;
		}
		return val;
	}

	/**
	 * 목록보여줄 때, 5개씩 끊어서 보여주기
	 * @param cnt - 목록 줄 개수   
	 */
	private void waitFunc(int cnt) {
		if (cnt % 5 == 0) {
			System.out.println("---Enter---");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			scan.nextLine();
		}
	}

	/**
	 * 현재날짜 가져오기
	 * 
	 * @return 현재 날짜 반환
	 */
	private String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar curDate = Calendar.getInstance();
		return dateFormat.format(curDate.getTime());
	}

	/**
	 * 현재날짜에 14일 더한 날짜
	 */
	private String getScheduledDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date date = dateFormat.parse(getCurrentDate());
			Calendar scheduledDate = Calendar.getInstance();
			scheduledDate.setTime(date);
			scheduledDate.add(Calendar.DATE, 14);
			return dateFormat.format(scheduledDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
