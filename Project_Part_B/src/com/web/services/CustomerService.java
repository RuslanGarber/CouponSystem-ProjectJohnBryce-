package com.web.services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.web.model.Income;
import com.web.model.IncomeType;

import DAO.CouponDBDAO;
import Exception.CouponNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;
import facede.ClientType;
import facede.CompanyFacade;
import facede.CustomerFacade;


@Path("/customer")
public class CustomerService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	public CustomerFacade getFacade() {
		CustomerFacade customer = null;
		customer = (CustomerFacade) request.getSession(false).getAttribute("facade");
		return customer;
	}
	
	@GET
	@Path("getAll_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllCoupons() {
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		Collection<Coupon> coupon = couponDBDAO.getAllCoupon();
		return new Gson().toJson(coupon);
	}
	
	@GET
	@Path("purch_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String PurchaseCoupon(@QueryParam("idP") long id) throws CouponNotFoundException {
		CustomerFacade customer = getFacade();
		CouponDBDAO couponDAO = new CouponDBDAO();
		Coupon coupon1 = couponDAO.getCoupon(id);
		customer.purchaseCoupon(coupon1);
		
		//Part 3
		Income income = new Income();
		income.setName(getCustomer().getCustName());
		income.setDescription(IncomeType.CUSTOMER_PURCHASE);
		income.setAmount(coupon1.getPrice());
		
		BussinessDelegate bussinessDelegate = new BussinessDelegate();
		Response response = bussinessDelegate.storeIncome(income);
		//The end
		
		String Msg = " Coupon "+ coupon1.getTitle()+" Purchased ";
		return new Gson().toJson(Msg);
	}
	
	@GET
	@Path("getAllpurch_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllPurchaseCoupons() {
		CustomerFacade customer = getFacade();
		Collection<Coupon> coupons = customer.getAllPurchasedCoupons();
		return new Gson().toJson(coupons);
	}
	
	@GET
	@Path("getAllpurchByType_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllPurchaseCouponsByType(@QueryParam("type") String type) {
		CustomerFacade customer = getFacade();
		CouponType n = CouponType.valueOf(type);
//		CouponType cType = CouponType.valueOf(type.toUpperCase());
		Collection<Coupon> coupons = customer.getAllPurchasedCouponsType(n);
		return new Gson().toJson(coupons);
	}
	
	@GET
	@Path("getAllpurchByPrice_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllPurchaseCouponsByPrice(@QueryParam("price") double price) {
		CustomerFacade customer = getFacade();
		Collection<Coupon> coupons = customer.getAllPurchasedCouponsPrice(price);
		return new Gson().toJson(coupons);
	}
	
	@GET
	@Path("custname")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer() {
		Customer customer = (Customer) request.getSession(false).getAttribute("customer");
		return customer;
	}
	
	
	
	
	
	
	
}
