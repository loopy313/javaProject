package member.dao;

import java.util.ArrayList;

import database.DBClass;
import vo.MemberVO;

public class IMemberDaoImpl implements IMemberDao {
	
	private static IMemberDaoImpl mdao;
	private DBClass db;
	private IMemberDaoImpl(){
		db=DBClass.getInstance();
	};
	
	public static IMemberDao getInstance(){
		if(mdao==null){
			mdao=new IMemberDaoImpl();
		}
		return mdao;
	}

	@Override
	public boolean insertMember(MemberVO mv) {
		return db.insertMember(mv);
	}

	@Override
	public ArrayList<MemberVO> getAllMemberList() {
		return db.getAllMemberList();
	}

	@Override
	public boolean deleteMember(MemberVO mv) {
		return db.deleteMember(mv);
		
	}

	@Override
	public int countAllMember() {
		return countAllMember();
	}
}
