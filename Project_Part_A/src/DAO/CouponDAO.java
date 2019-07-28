package DAO;

import java.util.Collection;

import JavaBeans.Coupon;
import JavaBeans.CouponType;

public interface CouponDAO {
	
	public void createCoupon(Coupon coupon);
	public void removeCoupon(Coupon coupon);
	public void updateCoupon(Coupon coupon);
	public Coupon getCoupon(long id);
	public Collection<Coupon> getAllCoupon();
	public Collection<Coupon> getCouponsByType(CouponType type);
}
