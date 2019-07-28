package CouponSystem;

import Connection.BasicConnectionPool;
import DAO.CompanyDBDAO;
import DAO.CustomerDBDAO;
import Exception.ClientNotFoundException;
import Exception.CompanyNotFoundException;
import Exception.CouponSystemException;
import facede.AdminFacade;
import facede.ClientType;
import facede.CompanyFacade;
import facede.CouponClientFacade;
import facede.CustomerFacade;


public class CouponSystem {
	CompanyDBDAO companyDBDAO = new CompanyDBDAO();
	CustomerDBDAO customerDBDAO = new CustomerDBDAO();
	private static CouponSystem instance;
	DailyCouponExpirationTask dailyCoupon = new DailyCouponExpirationTask();
	Thread t = new Thread(dailyCoupon);
	

	public static synchronized CouponSystem getInstance() {
        if (instance == null)
            synchronized (CouponSystem.class) {
                if (instance == null)
                    instance = new CouponSystem();
            }
        return instance;
	}
	
	public CouponSystem () {
		t.start();
	}
	
	public CouponClientFacade login (String name, String password, ClientType type) throws ClientNotFoundException, CompanyNotFoundException {
		switch(type) {
		    case ADMIN: 
			    return new AdminFacade().login(name, password, type);
		    case COMPANY: 
			    return new CompanyFacade().login(name, password, type);
			case CUSTOMER: 
			    return new CustomerFacade().login(name, password, type);
			default: 
			    throw new ClientNotFoundException("Сlient type does not exist");    
		}	
	}
	
	public void shutdown() throws CouponSystemException, InterruptedException {
		dailyCoupon.StopTask();
		t.interrupt();
		BasicConnectionPool.getInstance().closeAllConnections();
		System.out.println("The system is shutdown");	
	}
	
	
	
}
