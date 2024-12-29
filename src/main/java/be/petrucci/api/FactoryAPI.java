package be.petrucci.api;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.petrucci.javabeans.Factory;

@Path("/factory")
public class FactoryAPI {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getAllFactories() {
        ArrayList<Factory> factoryList = Factory.getAllFactories();
        if (factoryList == null) {
            return Response
            		.status(Status.NOT_FOUND)
            		.build();
        }
        return Response
        		.status(Status.OK)
        		.entity(factoryList)
        		.build();
    }
}
