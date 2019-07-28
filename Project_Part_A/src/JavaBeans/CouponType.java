package JavaBeans;

public enum CouponType {
	
	RESTURANS ("Resturans"),
	ELECTRICITY ("Electricity"),
	FOOD ("Food"),
	HEALTH ("Health"),
	SPORTS ("Sports"),
	CAMPING ("Camping"),
	TRAVELLING ("Travelling"),
	GAMES ("Games");

	
	private String type;

	private CouponType (String type) {
		this.type = type;	
	}
	
}
