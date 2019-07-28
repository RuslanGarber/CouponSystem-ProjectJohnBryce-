package facede;

import java.util.Collection;

import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerDBDAO;
import Exception.ClientNotFoundException;
import Exception.CompanyExistsException;
import Exception.CompanyNotFoundException;
import Exception.CustomerAlreadyExistsException;
import Exception.CustomerNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;


public class AdminFacade implements CouponClientFacade {
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	
	public AdminFacade() {
		
	}
	
	// Create a company on condition that it is not in the system
	/**
	 * 
	 * @param company
	 * 
	 * @throws CompanyExistsException
	 */
	public void createCompany(Company company) throws CompanyExistsException {
		if (!companyDBDAO.isCompanyExist(company)) {
			companyDBDAO.createCompany(company);
			System.out.println("Company "+company.getCompName() +" successfully established");
		} else {
			throw new CompanyExistsException("The company already exists "+ company.getCompName());
		}
	}
	
	// Remove the company and all its coupons
	/**
	 * 
	 * @param company
	 * @throws CompanyNotFoundException
	 */
	public void removeCompany(Company company) throws CompanyNotFoundException {
		if (companyDBDAO.isCompanyExist(company)) {
			Collection<Coupon> coupons = couponDBDAO.getAllCompanyCoupons(company.getId());
				if(!coupons.isEmpty()) {
					for(Coupon c: coupons) {
						couponDBDAO.removeCoupon(c);
					} 
				}
			companyDBDAO.removeCompany(company);
			System.out.println("Company "+company.getCompName() +" successfully delete");
		} else {
			throw new CompanyNotFoundException("The company that you want to remove is not exists in the system!");
		}
	}
	
	// Change the information about the company, except for the name
	/**
	 * 
	 * @param company
	 * @throws CompanyNotFoundException
	 */
	public void updateCompany(Company company) throws CompanyNotFoundException {
		if (companyDBDAO.isCompanyExist(company)) {
			companyDBDAO.updateCompany(company);
			System.out.println("Company "+company.getCompName() +" successfully changed");
		} else {
			throw new CompanyNotFoundException("The company that you want to update is not exists in the system!");
		}
	}
	
	// Get the company by id
	/**
	 * 
	 * @param id
	 * @return company
	 * @throws CompanyNotFoundException
	 */
	public Company getCompany(long id) throws CompanyNotFoundException {
		Company company = companyDBDAO.getCompany(id);
		if (company != null) {
			return company;
		} else {
			throw new CompanyNotFoundException("The company is not exists in the system!");
		}
	}
	
	// Get all the companies
	/**
	 * 
	 * @return company
	 */
	public Collection<Company> getAllCompanies() {
		Collection<Company> company = companyDBDAO.getAllCompanies();
		return company;	
	}
	
	// Create a customer on condition that it is not in the system
	/**
	 * 
	 * @param customer
	 * @throws CustomerAlreadyExistsException
	 */
	public void createCustomer(Customer customer) throws CustomerAlreadyExistsException {
		if(!customerDBDAO.isCustomerExist(customer)) {
			customerDBDAO.createCustomer(customer);
			System.out.println("Customer "+customer.getCustName() +" successfully established");
		}else {
			throw new CustomerAlreadyExistsException("The customer already exists");
		}
	}
	
	// Remove the customer
	/**
	 * 
	 * @param customer
	 * @throws CustomerNotFoundException
	 */
	public void removeCustomer(Customer customer) throws CustomerNotFoundException {
		if(customerDBDAO.isCustomerExist(customer)) {
			customerDBDAO.removeCustomer(customer);
			System.out.println("Customer "+customer.getCustName() +" successfully delete");
		}else {
			throw new CustomerNotFoundException("The customer that you want to remove is not exists in the system!");
		}
	}
	
	// Update the customer
	/**
	 * 
	 * @param customer
	 * @throws CustomerNotFoundException
	 */
	public void updateCustomer(Customer customer) throws CustomerNotFoundException {
		if(customerDBDAO.isCustomerExist(customer)) {
			customerDBDAO.updateCustomer(customer);
			System.out.println("Customer "+customer.getCustName() +" successfully changed");
		}else {
			throw new CustomerNotFoundException("The customer that you want to update is not exists in the system!");
		}
	}
	
	// Get customer by id
	/**
	 * 
	 * @param id
	 * @return customer
	 */
	public Customer getCustomer(long id) {
		Customer customer = customerDBDAO.getCustomer(id);
		return customer;
	}
	
	// Get all customer 
	/**
	 * 
	 * @return customers
	 */
	public Collection<Customer> getAllCustomer() {
		Collection<Customer> customers = customerDBDAO.getAllCustomer();
		return customers;
	}
	
	/**
	 * @see facede.CouponClientFacade#login(java.lang.String, java.lang.String, facede.ClientType)
	 * @param name , password , type
	 * @return boolean
	 */
	public CouponClientFacade login(String name, String password, ClientType type) throws ClientNotFoundException {
		System.out.println("Welcom Admin");
		AdminFacade ret = null;
		if (name.equalsIgnoreCase("Admin") && password.equals("1234")) {
			ret = this;
		} else {
			throw new ClientNotFoundException("User name or password is incorrect!");
		}
		return ret;
	}

}