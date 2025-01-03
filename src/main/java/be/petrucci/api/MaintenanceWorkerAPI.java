package be.petrucci.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.petrucci.javabeans.MaintenanceWorker;

@Path("/worker")
public class MaintenanceWorkerAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMaintenanceWorker() {
		ArrayList<MaintenanceWorker> workers = MaintenanceWorker.getAllWorkers() ;
		return Response
				.status(Status.OK)
				.entity(workers)
				.build();
	}
}
