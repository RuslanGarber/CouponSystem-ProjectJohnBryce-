package CouponSystem;
import java.util.Collection;
import java.util.Date;

import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerDBDAO;
import JavaBeans.Coupon;

public class DailyCouponExpirationTask implements Runnable {

	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CouponDBDAO couponDBDAO = new CouponDBDAO();
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();	 
	boolean quit = true;
	
	public DailyCouponExpirationTask() {

	}

	@Override
	public void run() {
		Collection<Coupon> coupons = couponDBDAO.getAllCoupon();
		while (quit) {
			for (Coupon curr : coupons) {
				if (curr.getEndDate().before(new Date(System.currentTimeMillis()))) {
					couponDBDAO.removeCoupon(curr);
					
				}
			}
			try {
				Thread.sleep(86400000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
	public void StopTask() {
		quit = false;
	}
	
}
