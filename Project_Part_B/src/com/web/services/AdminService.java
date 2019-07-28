package com.web.services;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.web.model.Income;

import Exception.CompanyExistsException;
import Exception.CompanyNotFoundException;
import Exception.CustomerAlreadyExistsException;
import Exception.CustomerNotFoundException;
import JavaBeans.Company;
import JavaBeans.Customer;
import facede.AdminFacade;
import facede.CouponClientFacade;
import javafx.print.Collation;

@Path("/admin")
public class AdminService {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	public AdminFacade getFacade() {
		AdminFacade admin = null;
		admin = (AdminFacade) request.getSession(false).getAttribute("facade");
		return admin;
	}

//	@GET
//	@Path("show_all_income")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String showAllIncome(@QueryParam("incomIdComp") int id) {
//		AdminFacade admin = getFacade();
//		BussinessDelegate bussinessDelegate = new BussinessDelegate();
//		Income response = bussinessDelegate.getJsonEmployee(id);
//		Company company = admin.getCompany(id);
//		String Msg = "ID: " + company.getId() + "\n" 
//				+ "Name: " + company.getCompName() + "\n"
//			    + "Password: " + company.getPassword() + "\n"
//			    + "E-mail: " + company.getEmail();
//		return new Gson().toJson(Msg);
//	}
	
	@GET
	@Path("create_comp")
	@Produces(MediaType.TEXT_PLAIN)
	public String CreateCompany(@QueryParam("companieID") int id, @QueryParam("COMP_NAME") String compName,
			@QueryParam("Password") String password, @QueryParam("EMAIL") String email) {
		AdminFacade admin = getFacade();
		Company company = new Company(id, compName, password, email);
		try {
			admin.createCompany(company);
		} catch (CompanyExistsException e) {
			System.out.println(e);
		}
		String Msg = "Company "+compName+" is created!!!";
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("remove_comp")
	@Produces(MediaType.TEXT_PLAIN)
	public String RemoveCompany(@QueryParam("idRemove") int id) throws CompanyNotFoundException {
		AdminFacade admin = getFacade();
		Company company = admin.getCompany(id);
		admin.removeCompany(company);
		String Msg =" Company " + company.getCompName()+ " is removed!!!";
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("update_comp")
	@Produces(MediaType.TEXT_PLAIN)
	public String UpdateCompany(@QueryParam("id") int id, @QueryParam("password") String password,
			@QueryParam("email") String email) throws CompanyNotFoundException {
		String Msg = "";
		AdminFacade admin = getFacade();
		Company company = admin.getCompany(id);
		if (!password.isEmpty()) {
			company.setPassword(password);
			Msg = company.getCompName() + " company password successfully changed";
		} else if (!email.isEmpty()) {
			company.setEmail(email);
			Msg = company.getCompName() + " company email successfully changed";
		} else {
			Msg = "You did not specify information to change";
		}
		admin.updateCompany(company);
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("get_comp")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetCompany(@QueryParam("idGet") int id) throws CompanyNotFoundException {
		AdminFacade admin = getFacade();
		Company company = admin.getCompany(id);
		String Msg = "ID: " + company.getId() + "\n" 
				+ "Name: " + company.getCompName() + "\n"
			    + "Password: " + company.getPassword() + "\n"
			    + "E-mail: " + company.getEmail();
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("getAll_comp")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllCompanies() {
		AdminFacade admin = getFacade();
		Collection<Company> company = admin.getAllCompanies();
		return new Gson().toJson(company);
	}

	// ____________________Customer_______________________________________________________

	@GET
	@Path("create_cust")
	@Produces(MediaType.TEXT_PLAIN)
	public String CreateCustomer(@QueryParam("idCust") int id, @QueryParam("name") String name,
			@QueryParam("passwordCust") String password) throws CustomerAlreadyExistsException {
		AdminFacade admin = getFacade();
		Customer customer = new Customer(id, name, password);
		admin.createCustomer(customer);
		String Msg = " Customer "+name+" is created!!! ";
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("remove_cust")
	@Produces(MediaType.TEXT_PLAIN)
	public String RemoveCustomer(@QueryParam("idRemoveCust") int id) throws CustomerNotFoundException {
		AdminFacade admin = getFacade();
		Customer customer = admin.getCustomer(id);
		admin.removeCustomer(customer);
		String Msg =" Customer "+customer.getCustName()+" is removed!!! ";
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("update_cust")
	@Produces(MediaType.TEXT_PLAIN)
	public String UpdateCustomer(@QueryParam("idUp") int id, @QueryParam("password1") String password) throws CustomerNotFoundException {
		String Msg = "";
		AdminFacade admin = getFacade();
		Customer customer = admin.getCustomer(id);
		if (!password.isEmpty()) {
			customer.setPassword(password);
			Msg =" Customer "+customer.getCustName() +" password successfully changed";
		} else {
			Msg = "You did not specify information to change";
		}
		admin.updateCustomer(customer);
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("get_cust")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetCustomer(@QueryParam("idGetCust") int id)  {
		AdminFacade admin = getFacade();
		Customer customer = admin.getCustomer(id);
		String Msg = "ID: " + customer.getId() + "\n" 
				+ "Name: " + customer.getCustName() + "\n"
			    + "Password: " + customer.getPassword();
		return new Gson().toJson(Msg);
	}

	@GET
	@Path("getAll_cust")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllCustomer() {
		AdminFacade admin = getFacade();
		Collection<Customer> customer = admin.getAllCustomer();
		return new Gson().toJson(customer);
	}
	
	//part3
	@GET
	@Path("get_all_income")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllIncome() {
		BussinessDelegate bussinessDelegate = new BussinessDelegate();
		List<Income> incomes = bussinessDelegate.getAllIncome();
		return new Gson().toJson(incomes);
	}
	

	
	@GET
	@Path("all_customer_income/{CUST_NAME}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllCustomerIncome(@PathParam("CUST_NAME") String custumerName) {
		BussinessDelegate bussinessDelegate = new BussinessDelegate();
		List<Income> incomes = bussinessDelegate.getAllIncomeByCustomer(custumerName);
		return new Gson().toJson(incomes);
	}
	
	@GET
	@Path("all_company_income")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllCompanyIncome(@QueryParam("COMP_NAME") String companyName) {
		BussinessDelegate bussinessDelegate = new BussinessDelegate();
		List<Income> incomes = bussinessDelegate.getAllIncomeByCompany(companyName);
		return new Gson().toJson(incomes);
	}
	//the end


}