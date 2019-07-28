package Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Timer;

import Connection.BasicConnectionPool;
import CouponSystem.CouponSystem;
import DAO.CompanyDBDAO;
import DAO.CouponDBDAO;
import DAO.CustomerDBDAO;
import Exception.ClientNotFoundException;
import Exception.CompanyExistsException;
import Exception.CompanyNotFoundException;
import Exception.CouponAlreadyExistException;
import Exception.CouponNotFoundException;
import Exception.CouponSystemException;
import Exception.CustomerAlreadyExistsException;
import Exception.CustomerNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;
import facede.AdminFacade;
import facede.ClientType;
import facede.CompanyFacade;
import facede.CustomerFacade;

		public class Main {
			private static final ClientType ADMIN = null;

			public static void main(String[] arg) 
			{
				
				CouponSystem cs =CouponSystem.getInstance();
				
				//create start Date for coupons
				
				Calendar startDate1 = Calendar.getInstance();
				Calendar startDate2 = Calendar.getInstance();
				Calendar startDate3 = Calendar.getInstance();
				Calendar startDate4 = Calendar.getInstance();
				Calendar startDate5 = Calendar.getInstance();
				
				startDate1.set(2018, 1, 1);
				startDate2.set(2018, 5, 1);
				startDate3.set(2018, 10, 1);
				startDate4.set(2017, 8, 10);
				startDate5.set(2018, 7, 1);
				
				//create finish Date for coupons
				
				Calendar endDate1 = Calendar.getInstance();
				Calendar endDate2 = Calendar.getInstance();
				Calendar endDate3 = Calendar.getInstance();
				Calendar endDate4 = Calendar.getInstance();
				Calendar endDate5 = Calendar.getInstance();
				
				endDate1.set(2018, 12, 9);
				endDate2.set(2019, 5, 1);
				endDate3.set(2019, 10, 1);
				endDate4.set(2019, 8, 10);
				endDate5.set(2021, 11, 23);
				
				// initialize the company
				
				ArrayList<Company> listCompany = new ArrayList<>();
				listCompany.add(new Company(1, "1", "1", "1@email.com"));
				listCompany.add(new Company(2, "2", "2", "2@email.com"));
				listCompany.add(new Company(3, "3", "3", "3@email.com"));
				listCompany.add(new Company(4, "4", "4", "4@email.com"));
				listCompany.add(new Company(5, "5", "5", "5@email.com"));
								
				//initialize the customer
				
				ArrayList<Customer> listCustomer = new ArrayList<>();
				listCustomer.add(new Customer (11,"11" , "11"));
				listCustomer.add(new Customer (21,"21" , "21"));
				listCustomer.add(new Customer (31,"31" , "31"));
				listCustomer.add(new Customer (41,"41" , "41"));
				listCustomer.add(new Customer (51,"51" , "51"));
				
				//initialize the coupon
				
				ArrayList<Coupon> listCoupon = new ArrayList<>();
				listCoupon.add(new Coupon (11, "Kovbasa", startDate1.getTime(), endDate1.getTime(), 15, "FOOD", "message1", 44.5, "html1"));
				listCoupon.add(new Coupon (22, "Cup", startDate2.getTime(), endDate2.getTime(), 10, "FOOD", "message2", 26.7, "html2"));
				listCoupon.add(new Coupon (33, "Moloko", startDate3.getTime(), endDate3.getTime(), 5, "FOOD", "message3", 10.2, "html3"));
				listCoupon.add(new Coupon (44, "Cup1", startDate4.getTime(), endDate4.getTime(), 12, "FOOD", "message4", 29.7, "html4"));
				listCoupon.add(new Coupon (55, "Moloko2", startDate5.getTime(), endDate5.getTime(), 7, "FOOD", "message5", 14.2, "html5"));
				
				// coupon from data base
				
				CouponDBDAO couponDBDAO = new CouponDBDAO();
				ArrayList<Coupon> listPurchase = new ArrayList<>();
					for(int i = 0; i < listCoupon.size(); i++){
						listPurchase.add(couponDBDAO.getCoupon(listCoupon.get(i).getId()));
					}
//_______________________________________________________________________________________________________
					
					
				//initialize login as administrator
				
					try {
						AdminFacade ad = (AdminFacade) cs.login("Admin", "1234", ClientType.ADMIN);
					
					//create the company as administrator
						
					for (Company c : listCompany) {
						try {
							ad.createCompany(c);
						} catch (CompanyExistsException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					}
					

					//update the company as administrator
					
					for (int i=1; i<=listCompany.size(); i++) {
						try {
							listCompany.get(i-1).setEmail("update"+i+"@email.com");
							ad.updateCompany(listCompany.get(i-1));
						} catch (CompanyNotFoundException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					}

					//create the customer as administrator
					
					for (Customer c : listCustomer) {
						try {
							ad.createCustomer(c);
						} catch (CustomerAlreadyExistsException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					}
					
					//update the customer as administrator
					
					for(int i = 1; i<=listCustomer.size(); i++) {
						try {
							listCustomer.get(i-1).setPassword("update"+i+"1");
							ad.updateCustomer(listCustomer.get(i-1));
						} catch (CustomerNotFoundException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					}
					
					//initialize login as Company
					
					CompanyFacade CompanyFacade1 = (CompanyFacade) cs.login("1", "1", ClientType.COMPANY);
					CompanyFacade CompanyFacade2 = (CompanyFacade) cs.login("2", "2", ClientType.COMPANY);
					
					//create the coupon
					
					for(int i = 0; i <3; i++) {
						try {
							CompanyFacade1.createCoupon(listCoupon.get(i));
						} catch (CouponAlreadyExistException | CouponNotFoundException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					} 
					for(int i = 3; i <5; i++) {
						try {
							CompanyFacade2.createCoupon(listCoupon.get(i));
						} catch (CouponAlreadyExistException | CouponNotFoundException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					} 
					
					//update the coupon
					
					for(int i = 1; i<=listCoupon.size(); i++) {
						try {
							listCoupon.get(i-1).setImage("updateCoupon"+i+"_C1");
							CompanyFacade1.updateCoupon(listCoupon.get(i-1));
						} catch (CouponNotFoundException e) {
							System.out.println(e);
						} finally {
							System.out.println("\n------------------------------");
						}
					}						
						
					} catch (ClientNotFoundException | CompanyNotFoundException e) {
						System.out.println(e);
					}
					
						try {
							CustomerFacade customerFacade = (CustomerFacade) cs.login("11", "11", ClientType.CUSTOMER);
							CustomerFacade customerFacade2 = (CustomerFacade) cs.login("21", "21", ClientType.CUSTOMER);
							
							// purchase coupon
						
							for (Coupon c : listPurchase) {
								try {
									customerFacade.purchaseCoupon(c);
									customerFacade2.purchaseCoupon(c);
								} catch (CouponNotFoundException e) {
									System.out.println(e);
								} finally {
									System.out.println("\n------------------------------");
								}
							}
						
						Collection<Coupon> purchaseCoupon = customerFacade.getAllPurchasedCoupons();
						System.out.println(purchaseCoupon);
						
						Collection<Coupon> purchaseCouponByPrice = customerFacade.getAllPurchasedCouponsPrice(50);
						System.out.println(purchaseCouponByPrice);
					} catch (ClientNotFoundException | CompanyNotFoundException e1) {
						e1.printStackTrace();
					}
				
			try {
				cs.shutdown();
			} catch (CouponSystemException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
				
			
				
			}
			
			

		}



