package vo;

public class MemberVO implements Cloneable{
	private String memid;
	private String mempw;
	private String memname;
	private int memmileage;			//연체여부에 따른 서적 반납시 마일리지 지급
	private boolean ismemblack;		//불량 회원(마일리지<0)
	private boolean isadmin;		//관리자권한
	private String email;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public String getMemId() {
		return memid;
	}
	public void setMemId(String memId) {
		this.memid = memId;
	}
	public String getMemPw() {
		return mempw;
	}
	public void setMemPw(String memPw) {
		this.mempw = memPw;
	}
	public String getMemName() {
		return memname;
	}
	public void setMemName(String memName) {
		this.memname = memName;
	}
	public int getMemMileage() {
		return memmileage;
	}
	public void setMemMileage(int memMileage) {
		this.memmileage = memMileage;
	}
	public boolean isMemBlack() {
		return ismemblack;
	}
	public void setMemBlack(boolean isMemBlack) {
		this.ismemblack = isMemBlack;
	}
	public boolean isAdmin() {
		return isadmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isadmin = isAdmin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
