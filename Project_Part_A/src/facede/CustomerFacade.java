package facede;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import DAO.CouponDBDAO;
import DAO.CustomerDBDAO;
import Exception.ClientNotFoundException;
import Exception.CouponNotFoundException;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;

public class CustomerFacade implements CouponClientFacade {
	
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	Customer customer;
	Date date = new Date();
	
	public CustomerFacade() {
		
	}
	
	/**
	 * 
	 * @param coupon
	 * @throws CouponNotFoundException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponNotFoundException {
		if(!couponDBDAO.isCouponExist(coupon)) throw new CouponNotFoundException("Coupon already exists");
		if(couponDBDAO.isCouponPurchased(coupon, customer)) throw new CouponNotFoundException("You can not buy two identical coupons");
		if(!(coupon.getAmount() > 0 && coupon.getEndDate().after(date))) throw new CouponNotFoundException("coupon is not available or expired");
		customerDBDAO.addToCustomerCoupon(customer, coupon);
		coupon.setAmount(coupon.getAmount()-1);
		couponDBDAO.updateCoupon(coupon);
	} 
	
	/**
	 * 
	 * @return coupons
	 */
	public Collection<Coupon> getAllPurchasedCoupons() {
		Collection<Coupon> coupons = couponDBDAO.getAllCustomerCoupons(customer.getId());
		return coupons;
	}
	
	/**
	 * 
	 * @param type
	 * @return couponsByType
	 */
	public Collection<Coupon> getAllPurchasedCouponsType(CouponType type) {
		Collection<Coupon> coupons = couponDBDAO.getAllCustomerCoupons(customer.getId());
		Collection<Coupon> couponsByType = new ArrayList<>();
		for (Coupon curr : coupons) {
			if (curr.getType() == type) {
				couponsByType.add(curr);
			}
		}
		return couponsByType;
	}
	
	/**
	 * 
	 * @param price
	 * @return couponsByPrice
	 */
	public Collection<Coupon> getAllPurchasedCouponsPrice(double price) {
		Collection<Coupon> coupons = couponDBDAO.getAllCustomerCoupons(customer.getId());
		Collection<Coupon> couponsByPrice = new ArrayList<>();
		for (Coupon curr : coupons) {
			if (curr.getPrice() == price) {
				couponsByPrice.add(curr);
			}
		}
		return couponsByPrice;
	}

	/**
	 * @see facede.CouponClientFacade#login(java.lang.String, java.lang.String, facede.ClientType)
	 * @param name , password , type
	 * @return boolean
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType type) throws ClientNotFoundException {
		CustomerFacade ret = null;
		if(customerDBDAO.login(name, password)) {
			System.out.println("Welcom Customer: "+name);
			this.customer = customerDBDAO.getCustomer(name);
			return ret = this;
		} else {
			throw new ClientNotFoundException("user name or password is incorrect!");
		}
	}
	
	

}