package vo;

public class CatVO implements Cloneable {
	private int catid;
	private String catname;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public int getCatId() {
		return catid;
	}
	public void setCatId(int catId) {
		this.catid = catId;
	}
	public String getCatName() {
		return catname;
	}
	public void setCatName(String catName) {
		this.catname = catName;
	}	
}
