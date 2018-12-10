package member.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rent.service.IRentService;
import rent.service.IRentServiceImpl;
import member.dao.IMemberDao;
import member.dao.IMemberDaoImpl;
import vo.MemberVO;
import vo.RentVO;

public class IMemberServiceImpl implements IMemberService {

	private static IMemberServiceImpl msi;
	private IMemberDao mdao;
	private IRentService rserv=IRentServiceImpl.getInstance();

	private IMemberServiceImpl(){
		mdao=IMemberDaoImpl.getInstance();
	};

	public static IMemberService getInstance(){
		if(msi==null){
			msi=new IMemberServiceImpl();
		}
		return msi;
	}

	@Override
	public boolean insertMember(MemberVO mv) {
		return mdao.insertMember(mv);
	}

	@Override
	public ArrayList<MemberVO> getAllMemberList() {
		return mdao.getAllMemberList();
	}

	public boolean deleteMember(MemberVO mv){
		return mdao.deleteMember(mv);
	}

	@Override
	public MemberVO getMemberbyId(String id){
		for(MemberVO mv:getAllMemberList()){
			if(mv.getMemId()==id){
				return mv;
			}
		}
		return null;
	}

	public int countAllMember(){
		return mdao.countAllMember();
	}

	@Override
	public ArrayList<MemberVO> getMemberbyName(String n) {
		ArrayList<MemberVO> mvl=getAllMemberList();
		ArrayList<MemberVO> temp=new ArrayList<MemberVO>();
		for(MemberVO mv:mvl){
			if(n.equals(mv.getMemName())){
				temp.add(mv);
			}
		}
		if(temp.isEmpty()){
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<MemberVO> getMemberbyBlack() {
		ArrayList<MemberVO> mvl=new ArrayList<MemberVO>();
		for(MemberVO mv:getAllMemberList()){
			if(mv.isMemBlack()){
				mvl.add(mv);
			}
		}
		if(mvl.isEmpty()){
			return null;
		}
		return mvl;
	}

	@Override
	public MemberVO authorize(Map<String, String> param) {
		String memId = param.get("memId");
		String memPw = param.get("memPw");
		ArrayList<MemberVO> mvl=getAllMemberList();
		for(MemberVO mv:mvl){
			if(memId.equals(mv.getMemId()) && memPw.equals(mv.getMemPw())){
				return mv;
			}
		}
		return null;
	}

	@Override
	public boolean isEqualMemId(String id) {
		boolean result = false; 
		ArrayList<MemberVO> mvl=getAllMemberList();
		for(MemberVO mv:mvl){
			if(id.equals(mv.getMemId())) { //memList에서 회원아이디를 가져와서 입력받은 아이디와 같은지 비교
				result = true;
				break;
			} else {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean isPossiblePw(String pw){
		/* at least 1 small-case letter, 1 Capital letter, 1 digit, 1 special character and the length should be between 6-10 characters.*/
		String pattern="(?=^.{6,14}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$";
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(pw);
		if(m.find()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getMemMaxMileageName() {
		ArrayList<MemberVO> mvl=getAllMemberList();
		Collections.sort(mvl,new Comparator<MemberVO>() {
			@Override
			public int compare(MemberVO o1, MemberVO o2) {
				if(o1.getMemMileage()<o1.getMemMileage())
					return 1;
				else if(o1.getMemMileage()>o1.getMemMileage()){
					return -1;
				}else{
					return 0;
				}
			}
		});
		return mvl.get(0).getMemName();
	}

	@Override
	public void updateMileage(int rentId){
		RentVO rv=rserv.getRentbyRentId(rentId);
		if(rv==null){
			return;
		}
		
		MemberVO mv=getMemberbyId(rv.getMemId());

		if(rserv.isDelayed(rentId)){
			int mm = mv.getMemMileage()-5;
			mv.setMemMileage(mm);   
		} else {
			int mm = mv.getMemMileage()+5;
			mv.setMemMileage(mm);   
		}
	}

	@Override
	public void updateBlackList(String memId) {
		MemberVO mv=getMemberbyId(memId);
		if(mv.getMemMileage()<0){
			mv.setMemBlack(true);
		}
	}

	@Override
	public ArrayList<MemberVO> getTop3RankMember() {
		List<MemberVO> mvl=getAllMemberList();
		Collections.sort(mvl,new Comparator<MemberVO>() {
			@Override
			public int compare(MemberVO o1, MemberVO o2) {
				if(o1.getMemMileage()<o2.getMemMileage()){
					return 1;
				}else if(o1.getMemMileage()>o2.getMemMileage()){
					return -1;
				}else{
					return 0;
				}
			}
		});
		return new ArrayList<MemberVO>(mvl.subList(0, 3));
	}
}
