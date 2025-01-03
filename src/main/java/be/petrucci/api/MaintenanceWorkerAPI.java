package be.petrucci.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

	@GET
	@Path("{id}/work")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorkerWorkInProgress(@PathParam("id") int worker_id) {
		MaintenanceWorker worker = new MaintenanceWorker();
		worker.setId(worker_id);
		return Response
				.status(Status.OK)
				.entity(worker.seeWorkInProgress())
				.build();
	}
}
