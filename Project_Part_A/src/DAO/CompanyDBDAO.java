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


//import com.mysql.jdbc.Connection;

import JavaBeans.Company;
import JavaBeans.Coupon;

public class CompanyDBDAO implements CompanyDAO{

	BasicConnectionPool cPool = BasicConnectionPool.getInstance();

	/**
	 * @see DAO.CompanyDAO#createCompany(JavaBeans.Company)
	 * 
	 * @param company
	 * 			create a new company
	 */
	@Override
	public void createCompany(Company company) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			String sql = "INSERT INTO company (ID,COMP_NAME,PASSWORD,EMAIL) VALUE(?,?,?,?)";
			PreparedStatement prStmt = con.prepareStatement(sql);
			prStmt.setLong(1, company.getId());
			prStmt.setString(2, company.getCompName());
			prStmt.setString(3, company.getPassword());
			prStmt.setString(4, company.getEmail());
			prStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}

	/**
	 * @see DAO.CompanyDAO#removeCompany(JavaBeans.Company)
	 * 
	 * @param company
	 * 			remove a new company
	 */
	@Override
	public void removeCompany(Company company) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			String sql = "DELETE FROM company WHERE ID = ?;";
			PreparedStatement prStmt = con.prepareStatement(sql);
			prStmt.setLong(1, company.getId());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}

	/**
	 * @see DAO.CompanyDAO#updateCompany(JavaBeans.Company)
	 * 
	 * @param company
	 * 			update a new company
	 */
	@Override
	public void updateCompany(Company company) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement prStmt = con.prepareStatement("UPDATE company SET PASSWORD = ? , EMAIL = ? WHERE ID = ?");
			prStmt.setString(1, company.getPassword());
			prStmt.setString(2, company.getEmail());
			prStmt.setLong(3, company.getId());
			prStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
	}

	/**
	 * @see DAO.CompanyDAO#getCompany(long)
	 *
	 * @param id
	 * 			looking for a database of the company with the appropriate id
	 * 
	 * @return the company according to the given id
	 */
	@Override
	public Company getCompany(long id) {
		Connection con = null;
		Company company = null;
		try {
			con = cPool.getConnection();
			PreparedStatement  prStmt = con.prepareStatement("SELECT * FROM company WHERE ID = ?");
			prStmt.setLong(1, id);
			ResultSet rSet = prStmt.executeQuery();
			rSet.next();			
			company = new Company(rSet.getLong("ID"),rSet.getString("COMP_NAME"),rSet.getString("PASSWORD"),rSet.getString("EMAIL"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return company;
	}

	/**
	 * @see DAO.CompanyDAO#getAllCompanies()
	 * 
	 * @return all existing companies in the collection
	 * 
	 */
	@Override
	public Collection<Company> getAllCompanies() {
		Connection con = null;
		List<Company> companies = new ArrayList<>();
		try {
			con = cPool.getConnection();
			Statement stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM company;");
			while (resultSet.next()) {
				companies.add(new Company(resultSet.getLong("ID"), resultSet.getString("COMP_NAME"),
						resultSet.getString("PASSWORD"), resultSet.getString("EMAIL")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return companies;
	}

	/**
	 * @see DAO.CompanyDAO#getCoupons(JavaBeans.Company)
	 * 
	 * @param company
	 * 			looking for all coupons of this company
	 * 
	 * @return all coupons of this company
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) {
		Connection con = null;
		List<Coupon> coupons = new ArrayList<>();
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		try {
			con = cPool.getConnection();
			PreparedStatement prepStm = con.prepareStatement("SELECT coupon_ID FROM company_coupon WHERE company_ID = ?");
			prepStm.setLong(1, company.getId());
			ResultSet resultSet = prepStm.executeQuery();
			while (resultSet.next()) {
				coupons.add(couponDBDAO.getCoupon(resultSet.getLong("ID")));
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
	 * @see DAO.CompanyDAO#login(java.lang.String, java.lang.String)
	 * 
	 * @param compName 
	 * 			company name to login
	 * 
	 * @param password
	 * 			company password to login
	 * 
	 * @return is logged in if the name and password are correct
	 */
	@Override
	public boolean login(String compName, String password) {
		boolean ret = false;
		List<Company> allCompanies = (ArrayList<Company>) getAllCompanies();
		for (Company curr : allCompanies) {
			if (curr.getCompName().equalsIgnoreCase(compName) && curr.getPassword().equals(password)) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * @see DAO.CompanyDAO#isCompanyExist(JavaBeans.Company)
	 * 
	 * @param company
	 * 			check the company for existence
	 * 
	 * @return true if the company exists
	 * 
	 */
	@Override
	public boolean isCompanyExist(Company company) {
		Connection con = null;
		try {
			con = cPool.getConnection();
			PreparedStatement pStmt = con.prepareStatement("SELECT COMP_NAME FROM company WHERE ID = ?");
			pStmt.setLong(1, company.getId());
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
	 * 
	 * @param name
	 * 			get the company by name
	 * 
	 * @return the company according to the given name
	 * 
	 */
	public Company getCompany(String name) {
		Connection con = null;
		Company company = null;
		try {
			con = cPool.getConnection();
			PreparedStatement  prStmt = con.prepareStatement("SELECT * FROM company WHERE COMP_NAME = ?");
			prStmt.setString(1, name);
			ResultSet rSet = prStmt.executeQuery();
			rSet.next();			
			company = new Company(rSet.getLong("ID"),rSet.getString("COMP_NAME"),rSet.getString("PASSWORD"),rSet.getString("EMAIL"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cPool.releaseConnection(con);
		}
		return company;
	}
	
	

}
