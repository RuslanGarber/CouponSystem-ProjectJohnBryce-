package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Connection.BasicConnectionPool;
import Exception.CouponNotFoundException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;

public class CouponDBDAO implements CouponDAO {
	
	BasicConnectionPool cPool = BasicConnectionPool.getInstance();
	
	
	/**
	 * @see DAO.CouponDAO#createCoupon(JavaBeans.Coupon)
	 * 
	 * @param coupon
	 * 			create a new coupon
	 * 
	 */
	@Override
	public void createCoupon(Coupon coupon) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement(
					"INSERT INTO coupon (ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE)"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			prStmt.setLong(1, coupon.getId());
			prStmt.setString(2, coupon.getTitle());
			prStmt.setDate(3, new Date(coupon.getStartDate().getTime()));
			prStmt.setDate(4, new Date(coupon.getEndDate().getTime()));
			prStmt.setInt(5, coupon.getAmount());
			prStmt.setString(6, coupon.getType().name());
			prStmt.setString(7, coupon.getMessage());
			prStmt.setDouble(8, coupon.getPrice());
			prStmt.setString(9, coupon.getImage());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		System.out.println("Coupon "+coupon.getTitle() +" successfully established");
	}
	/**
	 * @see DAO.CouponDAO#removeCoupon(JavaBeans.Coupon)
	 * 
	 * @param coupon
	 * 			remove a new coupon
	 * 
	 */
	@Override
	public void removeCoupon(Coupon coupon) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("DELETE FROM coupon WHERE ID = ?");
			prStmt.setLong(1, coupon.getId());
			prStmt.executeUpdate();
			
			prStmt = con.prepareStatement("DELETE FROM customer_coupon WHERE coupon_ID = ?");
			prStmt.setLong(1, coupon.getId());
			prStmt.executeUpdate();
			
			prStmt = con.prepareStatement("DELETE FROM company_coupon WHERE coupon_ID = ?");
			prStmt.setLong(1, coupon.getId());
			prStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		System.out.println("Coupon "+coupon.getTitle() +" successfully delete");
	}
	
	/**
	 * @see DAO.CouponDAO#updateCoupon(JavaBeans.Coupon)
	 * 
	 * @param coupon
	 * 			update a new coupon
	 * 
	 */
	@Override
	public void updateCoupon(Coupon coupon) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement(
					"UPDATE coupon SET " + "START_DATE = ?," + "END_DATE = ?," + "AMOUNT = ?,"
							+ "TYPE = ?," + "MESSAGE = ?," + "PRICE = ?," + "IMAGE = ?" + " WHERE ID = ?;");
			prStmt.setDate(1, new Date(coupon.getStartDate().getTime()));
			prStmt.setDate(2, new Date(coupon.getEndDate().getTime()));
			prStmt.setInt(3, coupon.getAmount());
			prStmt.setString(4, coupon.getType().name());
			prStmt.setString(5, coupon.getMessage());
			prStmt.setDouble(6, coupon.getPrice());
			prStmt.setString(7, coupon.getImage());
			prStmt.setLong(8, coupon.getId());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		System.out.println("Coupon "+coupon.getTitle() +" successfully changed");
	}

	/**
	 * @see DAO.CouponDAO#getCoupon(long)
	 * 
	 * @param id
	 * 			looking for a database of the coupon with the appropriate id
	 * 
	 * @return the coupon according to the given id
	 * 
	 */
	@Override
	public Coupon getCoupon(long id) {
		Connection con = null;
		Coupon coupon = null; 
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("SELECT * FROM coupon WHERE ID =?");
			prStmt.setLong(1, id);
			ResultSet rSet = prStmt.executeQuery();
			while (rSet.next()) {
				coupon = new Coupon(rSet.getLong("ID"), rSet.getString("TITLE"), rSet.getDate("START_DATE"),
						rSet.getDate("END_DATE"), rSet.getInt("AMOUNT"), rSet.getString("TYPE"),
						rSet.getString("MESSAGE"), rSet.getDouble("PRICE"), rSet.getString("IMAGE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return coupon;
	}

	/**
	 * @see DAO.CouponDAO#getAllCoupon()
	 * 
	 * @return all existing coupons in the collection
	 */
	@Override
	public Collection<Coupon> getAllCoupon() {
		Connection con = null;
		List<Coupon> coupons = new ArrayList<>();
		try {
			con = cPool.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rSet = stmt.executeQuery("SELECT * FROM coupon");
			while (rSet.next()) {
				coupons.add(new Coupon(rSet.getLong("ID"), rSet.getString("TITLE"), rSet.getDate("START_DATE"),
						rSet.getDate("END_DATE"), rSet.getInt("AMOUNT"), rSet.getString("TYPE"),
						rSet.getString("MESSAGE"), rSet.getDouble("PRICE"), rSet.getString("IMAGE")));
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
	 * @see DAO.CouponDAO#getCouponsByType(JavaBeans.CouponType)
	 * 
	 * @param type
	 * 			get all the coupons according to the given type
	 * 
	 * @return all coupons according to this type
	 * 
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) {
		Connection con = null;
		List<Coupon> coupons = new ArrayList<>();
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("SELECT * FROM coupon WHERE TYPE =?");
			prStmt.setString(1, type.name());
			prStmt.executeUpdate();
			ResultSet rSet = prStmt.executeQuery();
			while (rSet.next()) {
				coupons.add(new Coupon(rSet.getLong("ID"), rSet.getString("TITLE"), rSet.getDate("START_DATE"),
						rSet.getDate("END_DATE"), rSet.getInt("AMOUNT"), rSet.getString("TYPE"),
						rSet.getString("MESSAGE"), rSet.getDouble("PRICE"), rSet.getString("IMAGE")));
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
	 * @param company_Id
	 * 			looking for all coupons by company
	 * 
	 * @return all coupons of this company
	 * 
	 */
	public Collection<Coupon> getAllCompanyCoupons(long company_Id) {
		Connection con = null;
		List<Coupon> coupons = new ArrayList<>();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("SELECT coupon_ID FROM company_coupon WHERE company_ID = ?");
			prStmt.setLong(1, company_Id);
			ResultSet rSet = prStmt.executeQuery();
			while(rSet.next()) {
				Coupon coupon = couponDBDAO.getCoupon(rSet.getLong("coupon_ID"));
				coupons.add(coupon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return coupons;
	}
	
	/**
	 * @param coupon
	 * 			check the coupon for existence
	 * 
	 * @return true if the coupon exists
	 * 
	 */
	public boolean isCouponExist(Coupon coupon) throws CouponNotFoundException {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement pStmt = con.prepareStatement("SELECT TITLE FROM coupon WHERE ID = ?");
			pStmt.setLong(1, coupon.getId());
			ResultSet result = pStmt.executeQuery();
			if(result.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponNotFoundException("Coupon already exists");
		} catch (NullPointerException npe) {
			throw new CouponNotFoundException("Coupon not exists");
		} finally {
			cPool.releaseConnection(con);
		}
		return false;
	}
	
	/**
	 * @param coupon
	 * 			coupon which is checked for availability
	 * 
	 * @param customer
	 * 			customer who wants to buy a coupon
	 * 
	 * @return true if the coupon was not purchased earlier
	 */
	public boolean isCouponPurchased(Coupon coupon, Customer customer) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("SELECT coupon_ID FROM customer_coupon WHERE customer_ID = ? and coupon_ID = ?");
			prStmt.setLong(1, customer.getId());
			prStmt.setLong(2, coupon.getId());
			ResultSet rSet = prStmt.executeQuery();
			if (rSet.next()) {
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
	 * @param company
	 * 			a company that creates a coupon
	 * 
	 * @param coupon
	 * 			coupon created by the company
	 * 
	 */
	public void addToCompanyCoupon(Company company, Coupon coupon) throws CouponNotFoundException {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("insert into company_coupon (company_ID, coupon_ID) values (?, ?)");
			prStmt.setLong(1, company.getId());
			prStmt.setLong(2, coupon.getId());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponNotFoundException("You can not create two identical coupons");
		} finally {
			cPool.releaseConnection(con);
		}
	}
	
	/**
	 * @param customerID
	 * 			receive all coupons of this client
	 * 
	 * @return all coupons of this company
	 * 
	 */
	public Collection<Coupon> getAllCustomerCoupons(long customerId) {
		Connection con = null;
		List<Coupon> coupons = new ArrayList<>();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("SELECT coupon_ID FROM customer_coupon WHERE customer_ID = ?");
			prStmt.setLong(1, customerId);
			ResultSet rSet = prStmt.executeQuery();
			while (rSet.next()) {
				coupons.add(couponDBDAO.getCoupon(rSet.getLong("coupon_ID")));
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
}

	
