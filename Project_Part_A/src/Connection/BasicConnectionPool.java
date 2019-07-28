package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Exception.CouponSystemException;


public class BasicConnectionPool {
  
 
    private String url = "jdbc:mysql://localhost:3306/mydbproject?verifyServerCertificate=false&useSSL=true";
    private String user = "root";
    private String password = "96547";
    private List<Connection> connectionPool = new ArrayList<>();
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;
    
    private BasicConnectionPool() {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
            	connectionPool.add(DriverManager.getConnection(url, user, password));
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
    
    private static BasicConnectionPool instance;
	
	public static synchronized BasicConnectionPool getInstance() {
        if (instance == null)
            synchronized (BasicConnectionPool.class) {
                if (instance == null)
                    instance = new BasicConnectionPool();
            }
        return instance;
	}
         
    // standard constructors
  
    public synchronized Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }
     
    public synchronized boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }
    
    public synchronized void closeAllConnections() throws CouponSystemException {
		for(Connection connect : connectionPool) {
			try {
				connect.close();
			} catch (SQLException e) {
				throw new CouponSystemException("Close connections error");	
			}
		}
	}

}