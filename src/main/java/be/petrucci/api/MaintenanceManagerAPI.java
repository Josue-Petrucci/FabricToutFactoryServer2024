package be.petrucci.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.petrucci.javabeans.MaintenanceManager;

@Path("manager")
public class MaintenanceManagerAPI {

	@GET
	@Path("{id}/work")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getManagerWorkInProgress(@PathParam("id") int manager_id) {
		MaintenanceManager manager = new MaintenanceManager();
		manager.setId(manager_id);
		var maintenances = manager.seeWorkInProgress();
		if (maintenances == null) {
			return Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.build();
		}
		return Response
				.status(Status.OK)
				.entity(maintenances)
				.build();
	}
}
