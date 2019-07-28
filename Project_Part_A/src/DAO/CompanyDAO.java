package DAO;

import java.util.Collection;

import Exception.CompanyExistsException;
import JavaBeans.Company;
import JavaBeans.Coupon;

public interface CompanyDAO {

	public void createCompany(Company company);
	public void removeCompany(Company company);
	public void updateCompany(Company company);
	public Company getCompany(long id);
	public Collection<Company> getAllCompanies();
	public Collection<Coupon> getCoupons(Company company);
	public boolean login(String compName, String password);
	
	public boolean isCompanyExist(Company company) throws CompanyExistsException;
	
	
	
}
