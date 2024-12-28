package be.petrucci.api;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import be.petrucci.connection.FabricToutConnection;

@ApplicationPath("/api")
public class Root extends Application {
	
	@Context
	public void setServletContext(ServletContext context) {
		FabricToutConnection.initContext(context);
	}
}
