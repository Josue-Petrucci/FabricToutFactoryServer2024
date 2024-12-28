package be.petrucci.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.petrucci.javabeans.Machine;

@Path("/machine")
public class MachineAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMachine() {
		ArrayList<Machine> machines = Machine.getAllMachine();
		return Response
				.status(Status.OK)
				.entity(machines)
				.build();
	}
}
