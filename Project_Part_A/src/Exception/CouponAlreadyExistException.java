package Exception;

public class CouponAlreadyExistException extends Exception {

	public CouponAlreadyExistException() {
		super();
	}
	
	public CouponAlreadyExistException(String message) {
		super(message);
	}
}
