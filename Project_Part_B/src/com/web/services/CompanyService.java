package com.web.services;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

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

import Exception.CompanyExistsException;
import Exception.CompanyNotFoundException;
import Exception.CouponAlreadyExistException;
import Exception.CouponNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import facede.AdminFacade;
import facede.CompanyFacade;
@Path("/company")
public class CompanyService {
			
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	public CompanyFacade getFacade() {
			CompanyFacade company = null;
			company = (CompanyFacade) request.getSession(false).getAttribute("facade");
			return company;
		}
	
	@GET
	@Path("create_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String CreateCoupon(@QueryParam("idCreate") long id, @QueryParam("titleCreate") String title,@QueryParam("startDateCreate") Date startDate,
			@QueryParam("endDateCreate") Date endDate, @QueryParam("amountCreate") int amount, @QueryParam("typeCreate") String type,
			@QueryParam("messageCreate") String message, @QueryParam("priceCreate") double price, @QueryParam("imageCreate") String image) {
		CompanyFacade company = getFacade();
		Coupon coupon = new Coupon(id,title,startDate, endDate, amount, type, message, price, image);
			try {
				company.createCoupon(coupon);
				
				//Part 3
				Income income = new Income();
				income.setName(getCompany().getCompName());
				income.setDescription(IncomeType.COMPANY_NEW_COUPON);
				income.setAmount(100.00);
				
				BussinessDelegate bussinessDelegate = new BussinessDelegate();
				Response response = bussinessDelegate.storeIncome(income);
				
				//the end
			} catch (CouponAlreadyExistException | CouponNotFoundException e) {
				System.out.println(e);
			}
			String Msg = " Coupon "+coupon.getTitle()+" is created!!! ";
		return new Gson().toJson(Msg);
	}
	
	@GET
	@Path("remove_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String RemoveCompany(@QueryParam("idRemoveCoup") long id) {
		CompanyFacade company = getFacade();
		Coupon coupon = company.getCoupon(id);	
		try {
			company.removeCoupon(coupon);
		} catch (CouponNotFoundException e) {
			System.out.println(e);
		}
		String Msg = " Coupon " + coupon.getTitle()+" is remove!!!";
	return new Gson().toJson(Msg);
	}

	@GET
	@Path("update_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String UpdateCoupon(@QueryParam("idUpdate") long id, @QueryParam("startDateUpdate") Date startDate,
			@QueryParam("endDateUpdate") Date endDate, @QueryParam("amountUpdate") int amount, @QueryParam("typeUpdate") CouponType type,
			@QueryParam("messageUpdate") String message, @QueryParam("priceUpdate") double price, @QueryParam("imageUpdate") String image) {
		CompanyFacade company = getFacade();
		Coupon coupon = company.getCoupon(id);
		
		if(amount >= 0 || !message.isEmpty() 
				|| price >= 0 || !image.isEmpty() || startDate != null 
				|| endDate != null) {
			
			coupon.setStartDate(startDate);
			coupon.setEndDate(endDate);
			coupon.setAmount(amount);
			coupon.setType(type);
			coupon.setMessage(message);
			coupon.setPrice(price);
			coupon.setImage(image);
		} else {
			System.out.println("You have not entered data to change.");
		}
		try {
			company.updateCoupon(coupon);
			
			//Part 3
			Income income = new Income();
			income.setName(getCompany().getCompName());
			income.setDescription(IncomeType.COMPANY_UPDATE_COUPON);
			income.setAmount(10.00);
			
			BussinessDelegate bussinessDelegate = new BussinessDelegate();
			Response response = bussinessDelegate.storeIncome(income);
			
			//the end
		} catch (CouponNotFoundException e) {
			System.out.println(e);
		}
		String Msg = " Coupon is update!!!";
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("get_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetCompany(@QueryParam("idGetCoup") long id)  {
		CompanyFacade company = getFacade();
		Coupon coupon = company.getCoupon(id);
		String Msg = "ID: " + coupon.getId() + "\n" 
				+ "Title: " + coupon.getTitle() + "\n"
			    + "StartDate: " + coupon.getStartDate() + "\n"
			    + "EndDate: " + coupon.getEndDate() + "\n"
			    + "Amount: " + coupon.getAmount() + "\n" 
			    + "Type: " + coupon.getType() + "\n"
			    + "Message: " + coupon.getMessage() + "\n" 
			    + "Price: " + coupon.getPrice() + "\n"
			    + "Image: " + coupon.getImage();
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("getAll_coup")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllCompanies() {
		CompanyFacade company = getFacade();
		Collection<Coupon> coupon = company.getAllCoupon();
		return new Gson().toJson(coupon);
	}
	
	//_________________________________________________________________
	//Part 3
	@GET
	@Path("compname")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany() {
		Company company = (Company) request.getSession(false).getAttribute("company");
		return company;
	}
	
	@GET
	@Path("view_all_company_incomes")
	@Produces(MediaType.TEXT_PLAIN)
	public String viewAllCompanyIncomes() {
		BussinessDelegate bussinessDelegate = new BussinessDelegate();
		List<Income> incomes = bussinessDelegate.getAllIncomeByCompany(getCompany().getCompName());
		return new Gson().toJson(incomes);
	}
	
	//end of Part 3
	//__________________________________________________________________________
}
