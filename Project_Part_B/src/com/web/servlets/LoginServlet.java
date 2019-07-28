package com.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CouponSystem.CouponSystem;
import DAO.CompanyDBDAO;
import DAO.CustomerDBDAO;
import Exception.ClientNotFoundException;
import Exception.CompanyNotFoundException;
import JavaBeans.Company;
import JavaBeans.Customer;
import facede.AdminFacade;
import facede.ClientType;
import facede.CouponClientFacade;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CouponSystem system;
	
	public void init() {
		this.system = CouponSystem.getInstance();
		System.out.println("Coupon System on");
		}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

  
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	
    	if(session !=null) {
    		session.invalidate(); //kill the session
    	} 
    	session = request.getSession(true); // create new session
    	String username = request.getParameter("user");
    	String password = request.getParameter("pwd");
    	String userType = request.getParameter("type");
    	
    	System.out.println(userType);
    	ClientType cType = ClientType.valueOf(userType.toUpperCase());
    	CouponClientFacade facade;
    	try {
			facade = system.login(username, password, cType);
			if(facade != null) {
				session.setAttribute("facade", facade);
			}
			
			
		} catch (ClientNotFoundException | CompanyNotFoundException e) {
			request.getRequestDispatcher("err.html").forward(request, response);
			System.out.println(e);
		}
    	
    	switch (userType) {
    		case "ADMIN":
    			request.getRequestDispatcher("html/Admin.html").forward(request, response);
    			break;
    		case "COMPANY":
    			request.getRequestDispatcher("html/Company.html").forward(request, response);
    			CompanyDBDAO companyDBDAO = new CompanyDBDAO();
    			Company company = companyDBDAO.getCompany(username);
    			session.setAttribute("company", company);
    			break;
    		case "CUSTOMER":
    			request.getRequestDispatcher("html/Customer.html").forward(request, response);
    			CustomerDBDAO customerDBDAO = new CustomerDBDAO();
    			Customer customer = customerDBDAO.getCustomer(username);
    			session.setAttribute("customer", customer);
    			break;
    			
    	}
    	
    }
    
}

