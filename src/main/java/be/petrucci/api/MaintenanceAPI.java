package be.petrucci.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;
import be.petrucci.javabeans.Maintenance;

@Path("/maintenance")
public class MaintenanceAPI {
	@DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMaintenance(String jsonData) {
		try {
	        JSONObject json = new JSONObject(jsonData);
	        int id = json.getInt("id");
	        if (id == 0){
	            return Response
	            		.status(Status.BAD_REQUEST)
	            		.build();
	        }
	        Maintenance maintenance = new Maintenance();
	        maintenance.setId(json.getInt("id"));

	        if (!maintenance.deleteMaintenance()) {
	        	return Response
	        			.status(Status.NO_CONTENT)
	        			.build();
	        } else {
	        	return Response
	        			.status(Status.OK)
	        			.build();
	        }
	    }
	    catch (JSONException ex) {
	        return Response
	        		.status(Status.BAD_REQUEST)
	        		.entity("Invalid JSON format")
	        		.build();
	    }
    }
}
