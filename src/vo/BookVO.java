package vo;

public class BookVO implements Cloneable{
	private int bookid;
	private String name;
	private String publish;
	private String author;
	private int catid;
	private boolean rentable;
	private int rentcount;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public int getBookId() {
		return bookid;
	}
	public void setBookId(int bookId) {
		this.bookid = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getCatId() {
		return catid;
	}
	public void setCatId(int catId) {
		this.catid = catId;
	}
	public boolean isRentable() {
		return rentable;
	}
	public void setRentable(boolean rentable) {
		this.rentable = rentable;
	}
	public int getRentCount() {
		return rentcount;
	}
	public void setRentCount(int rentCount) {
		this.rentcount = rentCount;
	}
	@Override
	public String toString() {
		String s=String.format("bookId : %d\nbookName : %s\nPublish : %s\nAuthor : %s\n", getBookId(),getName(),getPublish(),getAuthor());
		String d="=========================================================================\n";
		return s+d;
	}
	
}
