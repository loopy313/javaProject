package rent.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rent.dao.IRentDao;
import rent.dao.IRentDaoImpl;
import vo.RentVO;

public class IRentServiceImpl implements IRentService {
	
	private static IRentServiceImpl rserv;
	private IRentDao rdao;
	private IRentServiceImpl(){
		rdao=IRentDaoImpl.getInstance();
	};
	
	public static IRentService getInstance(){
		if(rserv==null){
			rserv=new IRentServiceImpl();
		}
		return rserv;
	}

	@Override
	public boolean insertRent(RentVO rv) {
		return rdao.insertRent(rv);
	}

	@Override
	public ArrayList<RentVO> getAllRentList() {
		return rdao.getAllRentList();
	}

	@Override
	public RentVO getRentbyBookId(int id) {
		for(RentVO rv:getAllRentList()){
			if(id==rv.getBookId()){
				return rv;
			}
		}
		return null;
	}

	@Override
	public ArrayList<RentVO> getRentbyMemId(String id) {
		ArrayList<RentVO> rvl=new ArrayList<RentVO>();
		for(RentVO rv:getAllRentList()){
			if(id.equals(rv.getMemId())){
				rvl.add(rv);
			}
		}
		return rvl;
	}

	@Override
	public RentVO getRentbyRentId(int id) {
		for(RentVO rv:getAllRentList()){
			if(id==rv.getRentId()){
				return rv;
			}
		}
		return null;
	}

	@Override
	public boolean deleteRent(RentVO rv) {
		return rdao.deleteRent(rv);
	}

	@Override
	public int countAllRent() {
		return rdao.countAllRent();
	}

	@Override
	public int getLastRentId() {
		ArrayList<RentVO> rvl=rdao.getAllRentList();
		return rvl.get(rvl.size()-1).getRentId();
	}
	
	@Override
	public boolean isDelayed(int rentId){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try{
			Calendar curDate = Calendar.getInstance();
			Calendar rentDate = Calendar.getInstance();
			Date sDate = dateFormat.parse(getRentbyRentId(rentId).getScheduleDate());
			rentDate.setTime(sDate);
			if(curDate.compareTo(rentDate)>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void updateRentDate(RentVO rv) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try{
			Date date = dateFormat.parse(rv.getScheduleDate());
			Calendar updateDate = Calendar.getInstance();
			updateDate.setTime(date);
			updateDate.add( Calendar.DATE, 7 );
			rv.setScheduleDate( dateFormat.format(updateDate.getTime()) );
		}catch(Exception e){
			System.out.println("updateRentDate() error");
		}
	}
}
