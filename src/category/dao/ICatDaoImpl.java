package category.dao;

import java.util.ArrayList;

import vo.CatVO;
import database.DBClass;

public class ICatDaoImpl implements ICatDao {

	private static ICatDaoImpl cdao;
	private DBClass db;
	private ICatDaoImpl(){
		db=DBClass.getInstance();
	};
	
	public static ICatDao getInstance(){
		if(cdao==null){
			cdao=new ICatDaoImpl();
		}
		return cdao;
	}

	@Override
	public boolean insertCat(CatVO cv) {
		return db.insertCat(cv);
	}

	@Override
	public ArrayList<CatVO> getAllCatList() {
		return db.getAllCatList();
	}

	@Override
	public boolean deleteCat(CatVO bv) {
		return db.deleteCat(bv);
	}

	@Override
	public int countAllCat() {
		return db.countAllCat();
	}

}
