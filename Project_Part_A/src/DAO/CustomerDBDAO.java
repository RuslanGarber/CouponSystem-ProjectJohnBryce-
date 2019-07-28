package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Connection.BasicConnectionPool;
import JavaBeans.Coupon;
import JavaBeans.Customer;

public class CustomerDBDAO implements CustomerDAO {

	BasicConnectionPool cPool = BasicConnectionPool.getInstance();
	
	/**
	 * @see DAO.CustomerDAO#createCustomer(JavaBeans.Customer)
	 * 
	 * @param customer
	 * 			create a new customer
	 * 
	 */
	@Override
	public void createCustomer(Customer customer) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			String sql = "INSERT INTO customer (ID,CUST_NAME,PASSWORD) VALUE(?,?,?)";
			PreparedStatement prStmt = con.prepareStatement(sql);
			prStmt.setLong(1, customer.getId());
			prStmt.setString(2, customer.getCustName());
			prStmt.setString(3, customer.getPassword());
			prStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}

	/**
	 * @see DAO.CustomerDAO#removeCustomer(JavaBeans.Customer)
	 * 
	 * @param customer
	 * 			remove a customer 
	 * 
	 */
	@Override
	public void removeCustomer(Customer customer) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			String sql = "DELETE FROM customer WHERE ID = ?;";
			PreparedStatement prStmt = con.prepareStatement(sql);
			prStmt.setLong(1, customer.getId());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}

	/**
	 * @see DAO.CustomerDAO#updateCustomer(JavaBeans.Customer)
	 * 
	 * @param customer
	 * 			update a customer
	 * 
	 */
	@Override
	public void updateCustomer(Customer customer) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("UPDATE customer SET CUST_NAME = ? , PASSWORD = ?  WHERE ID = ?");
			prStmt.setString(1, customer.getCustName());
			prStmt.setString(2, customer.getPassword());
			prStmt.setLong(3, customer.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}

	/**
	 * @see DAO.CustomerDAO#getCustomer(long)
	 * 
	 * @param id
	 * 			Look for a customer on his id
	 */
	@Override
	public Customer getCustomer(long id) {
		Connection con = null;
		Customer customer = null;
		try {
			con = cPool.getConnection();
			PreparedStatement  prStmt = con.prepareStatement("SELECT * FROM customer WHERE ID = ?");
			prStmt.setLong(1, id);
			ResultSet rSet = prStmt.executeQuery();
			rSet.next();			
			customer = new Customer(rSet.getLong("ID"),rSet.getString("CUST_NAME"),rSet.getString("PASSWORD"));	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		System.out.println(customer);
		return customer;
	}
	
	/**
	 * @param name
	 * 			looking for a database of the customer with the appropriate name
	 * 
	 * @return the customer according to the given name
	 */
	public Customer getCustomer(String name) {
		Connection con = null;
		Customer customer = null;
		try {
			con = cPool.getConnection();
			PreparedStatement  prStmt = con.prepareStatement("SELECT * FROM customer WHERE CUST_NAME = ?");
			prStmt.setString(1, name);
			ResultSet rSet = prStmt.executeQuery();
			rSet.next();			
			customer = new Customer(rSet.getLong("ID"),rSet.getString("CUST_NAME"),rSet.getString("PASSWORD"));	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return customer;
	}

	/**
	 * @see DAO.CustomerDAO#getAllCustomer()
	 * 
	 * @return all existing customer in the collection
	 * 		
	 */
	@Override
	public Collection<Customer> getAllCustomer() {
		Connection con = null;
		List<Customer> customer = new ArrayList<>();
		try {
			con = cPool.getConnection();
			Statement stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM customer;");
				while (resultSet.next()) {
					customer.add(new Customer(resultSet.getLong("ID"), resultSet.getString("CUST_NAME"),resultSet.getString("PASSWORD")));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return customer;
	}

	/**
	 * @see DAO.CustomerDAO#getCoupons(JavaBeans.Customer)
	 * 
	 * @param customer
	 * 			looking for all coupons of this customer
	 * 
	 * @return all coupons of this customer
	 * 
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) {
		Connection con = null;
		List<Coupon> coupons = new ArrayList<>();
		CouponDBDAO couponDb = new CouponDBDAO();
		try {
			con = cPool.getConnection();
			PreparedStatement pStm = con.prepareStatement("SELECT coupon_ID FROM customer_coupon WHERE customer_ID = ?");
			pStm.setLong(1, customer.getId());
			ResultSet resultSet = pStm.executeQuery();
			while (resultSet.next()) {
				coupons.add(couponDb.getCoupon(resultSet.getLong("ID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return coupons;
	}
	
	/**
	 * @see DAO.CustomerDAO#isCustomerExist(JavaBeans.Customer)
	 * 
	 * @param customer
	 * 			check the customer for existence
	 * 
	 * @return true if the customer exists
	 * 
	 */
	@Override
	public boolean isCustomerExist(Customer customer) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement pStmt = con.prepareStatement("SELECT CUST_NAME FROM customer WHERE CUST_NAME = ?");
			pStmt.setString(1, customer.getCustName());
			ResultSet result = pStmt.executeQuery();
			if(result.next()) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return false;
	}
	
	/**
	 * @param customer
	 * 			customer who bought the coupon
	 * 
	 * @param coupon
	 * 			coupon purchased by this customer
	 */
	public void addToCustomerCoupon(Customer customer, Coupon coupon) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("insert into customer_coupon (customer_ID, coupon_ID) values (?, ?)");
			prStmt.setLong(1, customer.getId());
			prStmt.setLong(2, coupon.getId());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}
	
	/**
	 * @see DAO.CustomerDAO#login(java.lang.String, java.lang.String)
	 * 
	 * @param custName 
	 * 			customer name to login
	 * 
	 * @param password
	 * 			customer password to login
	 * 
	 * return is logged in if the name and password are correct
	 */
	@Override
	public boolean login(String custName, String password) {
		boolean ret = false;
		List<Customer> allCustomers = (ArrayList<Customer>) getAllCustomer();
		for (Customer curr : allCustomers) {
			if (curr.getCustName().equalsIgnoreCase(custName) && curr.getPassword().equals(password)) {
				ret = true;
			}
		}
		return ret;
	}

	

}
