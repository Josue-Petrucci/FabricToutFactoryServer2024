package be.petrucci.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;

import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.Site;

@Path("/manager")
public class MaintenanceManagerAPI {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMaintenanceManager(String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			String lastname = json.getString("lastname");
			String firstname = json.getString("firstname");
			int age = json.getInt("age");
			String address = json.getString("address");
			String matricule = json.getString("matricule");
			String password = json.getString("password");

			JSONObject siteJson = json.getJSONObject("site");
			Site site = new Site();
			site.setId(siteJson.getInt("id"));
			if (lastname == null || firstname == null || age == 0 || address == null || matricule == null
					|| password == null || site.getId() == 0) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			MaintenanceManager maintenanceManager = new MaintenanceManager(0, lastname, firstname, age, address,
					matricule, password, site, null);

			if (!maintenanceManager.addMaintenanceManager()) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			} else {
				return Response.status(Status.CREATED).build();
			}
		} catch (JSONException ex) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid JSON format").build();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManagerDetail(@PathParam("id") int id) {
		MaintenanceManager m = MaintenanceManager.getManagerDetail(new MaintenanceManager(id));
		if (m.getId() == -1) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).entity(m).build();
	}
}
