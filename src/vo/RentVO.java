package vo;


public class RentVO implements Cloneable{
	private int rentid; 
	private String rentdate; 		//대여일자
	private String scheduledate;	//반납예정일자
	private String memid;
    private int bookid;
    
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public int getRentId() {
		return rentid;
	}
	public void setRentId(int rentId) {
		this.rentid = rentId;
	}
	public String getRentDate() {
		return rentdate;
	}
	public void setRentDate(String rentDate) {
		this.rentdate = rentDate;
	}
	public String getMemId() {
		return memid;
	}
	public void setMemId(String memId) {
		this.memid = memId;
	}
	public int getBookId() {
		return bookid;
	}
	public void setBookId(int bookId) {
		this.bookid = bookId;
	}
	public String getScheduleDate() {
		return scheduledate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduledate = scheduleDate;
	}
}
