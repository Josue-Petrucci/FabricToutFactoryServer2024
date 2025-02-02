package be.petrucci.api;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.petrucci.javabeans.DangerLevel;
import be.petrucci.javabeans.Factory;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.Zone;

@Path("/machine")
public class MachineAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMachine() {
		ArrayList<Machine> machineList = Machine.getAllMachines();
		if (machineList == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).entity(machineList).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMachine(String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			MachineType type = MachineType.valueOf(json.getString("type"));
			double size = json.getDouble("size");
			MachineStatus status = MachineStatus.valueOf(json.getString("status"));

			JSONObject siteJson = json.getJSONObject("site");
			Site site = new Site();
			site.setId(siteJson.getInt("id"));
			site.setName(siteJson.getString("name"));
			site.setCity(siteJson.getString("city"));

			JSONObject factoryJson = siteJson.getJSONObject("factory");
			Factory factory = new Factory(factoryJson.getInt("id"), factoryJson.getString("name"), site);
			site.setFactory(factory);

			JSONArray zonesJsonArray = json.getJSONArray("zones");
			ArrayList<Zone> zones = new ArrayList<>();
			for (int i = 0; i < zonesJsonArray.length(); i++) {
				JSONObject zoneJson = zonesJsonArray.getJSONObject(i);
				Zone zone = new Zone();
				zone.setId(zoneJson.getInt("id"));
				zone.setZoneLetter(zoneJson.getString("zoneLetter").charAt(0));
				zone.setDangerLevel(DangerLevel.valueOf(zoneJson.getString("dangerLevel")));
				zones.add(zone);
			}
			site.setZones(zones);

			if (type == null || size == 0 || status == null || site == null || zones == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			Machine machine = new Machine(0, type, size, status, site, zones);

			if (!machine.addMachine()) {
				return Response.status(Status.SERVICE_UNAVAILABLE).build();
			} else {
				return Response.status(Status.CREATED).build();
			}
		} catch (JSONException ex) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid JSON format").build();
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMachine(String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			int id = json.getInt("id");
			if (id == 0) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			Machine machine = new Machine();
			machine.setId(json.getInt("id"));

			if (!machine.deleteMachine()) {
				return Response.status(Status.NO_CONTENT).build();
			} else {
				return Response.status(Status.OK).build();
			}
		} catch (JSONException ex) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid JSON format").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMachine(String jsonData) {
		try {
			JSONObject json = new JSONObject(jsonData);
			int id = json.getInt("id");
			if (id == 0) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			MachineStatus status = MachineStatus.valueOf(json.getString("status"));
			Machine machine = new Machine();
			machine.setId(id);
			machine.setStatus(status);
			if (!machine.updateMachine()) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				// TODO: Send back content as specified by the HTTP protocol in case of an `OK`
				// response for a `PUT` request
				return Response.status(Status.OK).build();
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity("Invalid JSON format").build();
		}
	}
}
