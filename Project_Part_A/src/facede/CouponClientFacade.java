package facede;

import Exception.ClientNotFoundException;
import Exception.CompanyNotFoundException;

public interface CouponClientFacade {
	public CouponClientFacade login(String name, String password, ClientType type) throws ClientNotFoundException, CompanyNotFoundException;
}
