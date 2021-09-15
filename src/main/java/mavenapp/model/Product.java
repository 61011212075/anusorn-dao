package mavenapp.model;

import java.util.Date;

public class Product {
	private int pid;
	private String pname;
	private String pdetail;
	private int price;
	private Date expire;
	private String image;
	
	public Product() {}
	
	public Product(int pid, String pname, String pdetail, int price, Date expire, String image) {
		this.pid = pid;
		this.pname = pname;
		this.pdetail = pdetail;
		this.price = price;
		this.expire = expire;
		this.image = image;
	}
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pnamej) {
		this.pname = pnamej;
	}
	public String getPdetail() {
		return pdetail;
	}
	public void setPdetail(String pdetail) {
		this.pdetail = pdetail;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getExpire() {
		return expire;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
