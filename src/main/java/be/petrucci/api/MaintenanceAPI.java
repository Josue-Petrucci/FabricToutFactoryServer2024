package be.petrucci.api;

import javax.ws.rs.DELETE;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;
import org.json.JSONObject;
import be.petrucci.javabeans.Maintenance;
import org.json.JSONArray;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.MaintenanceWorker;

@Path("/maintenance")
public class MaintenanceAPI {
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMaintenance() {
	}*/
	
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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProduct(String productJson) {
		try {
			JSONObject json = new JSONObject(productJson);
			
			long timestamp = json.getLong("date");
	        LocalDate localDate = Instant.ofEpochMilli(timestamp)
	                                     .atZone(ZoneId.systemDefault())
	                                     .toLocalDate();
	        Date date = Date.valueOf(localDate);
	        String instructions = json.getString("instructions");
	        int duration = json.getInt("duration");
	        MaintenanceStatus status = MaintenanceStatus.valueOf(json.getString("status"));
	        
	        JSONObject managerJson = json.getJSONObject("manager");
	        MaintenanceManager manager = new MaintenanceManager();
	        manager.setId(managerJson.getInt("id"));
	        manager.setLastname(managerJson.getString("lastname"));
	        manager.setFirstname(managerJson.getString("firstname"));
	        manager.setAge(managerJson.getInt("age"));
	        manager.setAddress(managerJson.getString("address"));
	        manager.setMatricule(managerJson.getString("matricule"));
	        manager.setPassword(managerJson.getString("password"));
	        
	        JSONObject machineJson = json.getJSONObject("machine");
	        Machine machine = new Machine();
	        machine.setId(machineJson.getInt("id"));
	        machine.setType(MachineType.valueOf(machineJson.getString("type")));
	        machine.setSize(machineJson.getDouble("size"));
	        machine.setStatus(MachineStatus.valueOf(machineJson.getString("status")));
	        
	        JSONArray workersArray = json.getJSONArray("workers");
	        ArrayList<MaintenanceWorker> workers = new ArrayList<>();

	        for (int i = 0; i < workersArray.length(); i++) {
	            JSONObject workerJson = workersArray.getJSONObject(i);
	            MaintenanceWorker worker = new MaintenanceWorker();

	            worker.setId(workerJson.getInt("id"));
	            worker.setLastname(workerJson.getString("lastname"));
	            worker.setFirstname(workerJson.getString("firstname"));
	            worker.setAge(workerJson.getInt("age"));
	            worker.setAddress(workerJson.getString("address"));
	            worker.setMatricule(workerJson.getString("matricule"));
	            worker.setPassword(workerJson.getString("password"));
	            workers.add(worker);
	        }

	        Maintenance maintenance = new Maintenance(date, duration, instructions, status, machine, manager, workers);        
	        if(!maintenance.createMaintenance()) {
	        	return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Unable to create a maintenance.").build();
	        }
	        
	        return Response
	        		.status(Status.CREATED)
	                .type(MediaType.APPLICATION_JSON)
	                .build();
			
		}catch (JSONException e) {
			return Response.status(Status.BAD_REQUEST.getStatusCode(), e.getMessage()).build();
		}catch (IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST.getStatusCode(), e.getMessage()).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMaintenance(String jsonData) {
		try {
			var json = new JSONObject(jsonData);
			var maintenance = new Maintenance(
				json.getInt("id"),
				new Date(json.getLong("date")),
				json.getInt("duration"),
				json.getString("instructions"),
				json.getString("report"),
				MaintenanceStatus.valueOf(json.getString("status"))
			);
			if (!maintenance.updateMaintenance()) {
				return Response
						.status(Status.INTERNAL_SERVER_ERROR)
						.entity(maintenance)
						.build();
			}
			return Response
					.status(Status.OK)
					.build();
		} catch (JSONException e) {
			e.printStackTrace();
			return Response
					.status(Status.BAD_REQUEST)
					.build();
		}
	}
}
