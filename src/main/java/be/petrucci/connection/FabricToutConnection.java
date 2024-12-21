package be.petrucci.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JOptionPane;

public class FabricToutConnection {
	private static Connection instance = null;
	Map<String, String> params = XmlReader.readContextParams("src/main/webapp/WEB-INF/web.xml");
	
	private FabricToutConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
            String ip = params.get("db.ip");
            String port = params.get("db.port");
            String service_name = params.get("db.serviceName");
            String chaineConnexion = "jdbc:oracle:thin:@//" + ip + ":" + port + "/" + service_name;                                
            String username = params.get("db.username");
 			String password = params.get("db.password");                                                                  
 			instance = DriverManager.getConnection(chaineConnexion, username, password);
		} catch(ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Classe de driver introuvable" + ex.getMessage());
			System.exit(0);
		} catch (SQLException ex) { 
			JOptionPane.showMessageDialog(null, "Erreur JDBC : " + ex.getMessage());
		}
		if (instance == null) { 
			JOptionPane.showMessageDialog(null, "La base de données est inaccessible, fermeture du programme.");
			System.exit(0);
		}
	}
	
	public static Connection getInstance() {
		if(instance == null){
			new FabricToutConnection();
		}
		return instance;
	}
}
