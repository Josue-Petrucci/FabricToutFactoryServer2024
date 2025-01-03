package be.petrucci.api;

import javax.ws.rs.DELETE;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	        manager.setId(managerJson.optInt("id", -1));
	        manager.setLastname(managerJson.optString("lastname", ""));
	        manager.setFirstname(managerJson.optString("firstname", ""));
	        manager.setAge(managerJson.optInt("age", 0));
	        manager.setAddress(managerJson.optString("address", ""));
	        manager.setMatricule(managerJson.optString("matricule", ""));
	        manager.setPassword(managerJson.optString("password", ""));
	        
	        JSONObject machineJson = json.getJSONObject("machine");
	        Machine machine = new Machine();
	        machine.setId(machineJson.optInt("id", -1));
	        machine.setType(MachineType.valueOf(machineJson.optString("type", "UNKNOWN")));
	        machine.setSize(machineJson.optDouble("size", 0.0));
	        machine.setStatus(MachineStatus.valueOf(machineJson.optString("status", "UNKNOWN")));
	        
	        JSONArray workersArray = json.getJSONArray("workers");
	        ArrayList<MaintenanceWorker> workers = new ArrayList<>();
	        
	        for (int i = 0; i < workersArray.length(); i++) {
	            JSONObject workerJson = workersArray.getJSONObject(i);
	            MaintenanceWorker worker = new MaintenanceWorker();

	            worker.setId(workerJson.optInt("id", -1));
	            worker.setLastname(workerJson.optString("lastname", ""));
	            worker.setFirstname(workerJson.optString("firstname", ""));
	            worker.setAge(workerJson.optInt("age", 0));
	            worker.setAddress(workerJson.optString("address", ""));
	            worker.setMatricule(workerJson.optString("matricule", ""));
	            worker.setPassword(workerJson.optString("password", "")); 
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
}
