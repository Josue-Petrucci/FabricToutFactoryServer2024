package be.petrucci.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;

import javax.swing.JOptionPane;

public class FabricToutConnection {
	private static Connection instance = null;
	
	private FabricToutConnection(ServletContext context) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
            String ip = context.getInitParameter("db.ip");
            String port = context.getInitParameter("db.port");
            String service_name = context.getInitParameter("db.serviceName");
            String chaineConnexion = "jdbc:oracle:thin:@//" + ip + ":" + port + "/" + service_name;                                
            String username = context.getInitParameter("db.username");
 			String password = context.getInitParameter("db.password");                                                                  
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
	
	public static Connection getInstance(ServletContext context) {
		if(instance == null){
			new FabricToutConnection(context);
		}
		return instance;
	}
}
