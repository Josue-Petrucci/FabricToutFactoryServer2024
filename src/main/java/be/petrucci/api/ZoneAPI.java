package be.petrucci.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.petrucci.javabeans.Zone;

@Path("/zone")
public class ZoneAPI {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllZones() {
		ArrayList<Zone> zoneList = Zone.getAllZones();
		if (zoneList == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).entity(zoneList).build();
	}
}
