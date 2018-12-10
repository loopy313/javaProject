package rent.dao;

import java.util.ArrayList;

import vo.RentVO;
import database.DBClass;

public class IRentDaoImpl implements IRentDao {
	
	private static IRentDaoImpl rdao;
	private DBClass db;
	private IRentDaoImpl(){
		db=DBClass.getInstance();
	};
	
	public static IRentDao getInstance(){
		if(rdao==null){
			rdao=new IRentDaoImpl();
		}
		return rdao;
	}

	@Override
	public boolean insertRent(RentVO rv) {
		return db.insertRent(rv);
	}

	@Override
	public ArrayList<RentVO> getAllRentList() {
		return db.getAllRentList();
	}

	@Override
	public boolean deleteRent(RentVO rv) {
		return db.deleteRent(rv);
	}

	@Override
	public int countAllRent() {
		return db.countAllRent();
	}
}
