package facede;

import java.util.Collection;

import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import Exception.ClientNotFoundException;
import Exception.CompanyNotFoundException;
import Exception.CouponAlreadyExistException;
import Exception.CouponNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

public class CompanyFacade implements CouponClientFacade {
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	Company company;

	public CompanyFacade() {
		
	}
	
	/**
	 * 
	 * @param coupon
	 * @throws CouponAlreadyExistException
	 * @throws CouponNotFoundException
	 */
	public void createCoupon(Coupon coupon) throws CouponAlreadyExistException, CouponNotFoundException {
		if(!couponDBDAO.isCouponExist(coupon)) {
			couponDBDAO.createCoupon(coupon);
			couponDBDAO.addToCompanyCoupon(this.company, coupon);
		} else {
			throw new CouponAlreadyExistException("The Coupon already exist");
		}
	}

	/**
	 * 
	 * @param coupon
	 * @throws CouponNotFoundException
	 */
	public void removeCoupon(Coupon coupon) throws CouponNotFoundException {
		if(couponDBDAO.isCouponExist(coupon)) {
			couponDBDAO.removeCoupon(coupon);
		} else {
			throw new CouponNotFoundException("The coupon that you want to remove is not exists in the system!");
		}
	}
	
	/**
	 * 
	 * @param coupon
	 * @throws CouponNotFoundException
	 */
	public void updateCoupon(Coupon coupon) throws CouponNotFoundException {
		if(couponDBDAO.isCouponExist(coupon)) {
			couponDBDAO.updateCoupon(coupon);
		} else {
			throw new CouponNotFoundException("The coupon that you want to update is not exists in the system!");
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return id
	 */
	public Coupon getCoupon(long id) {
		return couponDBDAO.getCoupon(id);
		
	}
	
	/**
	 * 
	 * @return coupon
	 */
	public Collection<Coupon> getAllCoupon() {
		Collection<Coupon> coupon = couponDBDAO.getAllCoupon();
		return coupon;	
	}
	
	/**
	 * 
	 * @param couponType
	 * @return coupon
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType) {
		Collection<Coupon> coupon = couponDBDAO.getCouponsByType(couponType);
		return coupon;
		
	}

	/**
	 * @see facede.CouponClientFacade#login(java.lang.String, java.lang.String, facede.ClientType)
	 * @param name , password , type
	 * @return boolean
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws CompanyNotFoundException{
		CompanyFacade ret = null;
		if(companyDBDAO.login(name, password)) {
			System.out.println("Welcom Company: "+name);
			this.company = companyDBDAO.getCompany(name);
			return ret = this;
		} else {
			throw new CompanyNotFoundException("user name or password is incorrect!");
		}
	}
	
	
}