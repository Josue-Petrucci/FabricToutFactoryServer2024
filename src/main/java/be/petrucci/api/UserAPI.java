package be.petrucci.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONException;
import org.json.JSONObject;

import be.petrucci.javabeans.User;

@Path("/user")
public class UserAPI {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUserOrLogin(String jsonData) {
		try {
	        JSONObject json = new JSONObject(jsonData);
	        String lastname = json.optString("lastname",null);
	    	String firstname = json.optString("firstname",null);
	    	int age = json.optInt("age",0);
	    	String address = json.optString("address",null);
	    	String matricule = json.getString("matricule");
	    	String password = json.getString("password");
	        User user = User.login(
	        		new User(0,lastname,firstname,age,address,matricule,password));
	        if(user.equals(null)) {
	        	return Response
		        		.status(Status.BAD_REQUEST)
		        		.build();
	        }
	        if(user.getId() != 0) {
		        return Response
		        		.status(Status.OK)
		        		.entity(user)
		        		.build();
	        }
	        
	        //TODO : Place register logic here
	        return Response
	        		.status(Status.CREATED)
	        		.build();
	    }
	    catch (JSONException ex) {
	        return Response
	        		.status(Status.BAD_REQUEST)
	        		.entity("Invalid JSON format")
	        		.build();
	    }
    }
}
