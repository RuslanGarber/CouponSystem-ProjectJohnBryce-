package DAO;

import java.util.Collection;

import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;

public interface CustomerDAO {
	
	public void createCustomer(Customer customer);
	public void removeCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public Customer getCustomer(long id);
	public Collection<Customer> getAllCustomer();
	public Collection<Coupon> getCoupons(Customer customer);
	public boolean login(String custName, String password);
	boolean isCustomerExist(Customer customer);
}
