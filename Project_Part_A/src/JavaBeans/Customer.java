package JavaBeans;
import java.util.Collection;

public class Customer {
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	public Customer (long id, String custName, String password) {
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "\nCustomer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}
	
	

}
