package category.service;

import java.util.ArrayList;

import category.dao.ICatDao;
import category.dao.ICatDaoImpl;
import vo.CatVO;

public class ICatServiceImpl implements ICatService {

	private static ICatServiceImpl cserv;
	private ICatDao cdao;
	private ICatServiceImpl(){
		cdao=ICatDaoImpl.getInstance();
	};
	
	public static ICatService getInstance(){
		if(cserv==null){
			cserv=new ICatServiceImpl();
		}
		return cserv;
	}

	@Override
	public boolean insertCat(CatVO cv) {
		return cdao.insertCat(cv);
	}

	@Override
	public ArrayList<CatVO> getAllCatList() {
		return cdao.getAllCatList();
	}

	@Override
	public CatVO getCatbyCatId(int id) {
		for(CatVO cv:getAllCatList()){
			if(id==cv.getCatId())
				return cv;
		}
		return null;
	}

	@Override
	public CatVO getCatbyCatName(String n) {
		for(CatVO cv:getAllCatList()){
			if(n.equals(cv.getCatName())){
				return cv;
			}
		}
		return null;
	}

	@Override
	public boolean deleteCat(CatVO bv) {
		return cdao.deleteCat(bv);
	}

	@Override
	public int countAllCat() {
		return cdao.countAllCat();
	}

	@Override
	public boolean isValidateCat(int catId) {
		for(CatVO cv:getAllCatList()){
			if(cv.getCatId()==catId)
				return true;
		}
		return false;
	}
}
