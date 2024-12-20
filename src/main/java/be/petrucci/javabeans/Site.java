package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Site implements Serializable {
	private static final long serialVersionUID = -2933081182814214954L;
	private int id;
	private String name;
	private String city;
	private Factory factory;
	private ArrayList<Zone> zones;
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	private ArrayList<MaintenanceManager> listMaintenanceManagers = new ArrayList<MaintenanceManager>();
	private ArrayList<MaintenanceWorker> listMaintenanceWorkers = new ArrayList<MaintenanceWorker>();

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public Factory getFactory() {
		return factory;
	}
	
	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}
	
	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}
	
	public ArrayList<MaintenanceManager> getListMaintenanceManagers() {
		return listMaintenanceManagers;
	}

	public void setListMaintenanceManagers(ArrayList<MaintenanceManager> listMaintenanceManagers) {
		this.listMaintenanceManagers = listMaintenanceManagers;
	}

	public ArrayList<MaintenanceWorker> getListMaintenanceWorkers() {
		return listMaintenanceWorkers;
	}

	public void setListMaintenanceWorkers(ArrayList<MaintenanceWorker> listMaintenanceWorkers) {
		this.listMaintenanceWorkers = listMaintenanceWorkers;
	}

	public Site() {}
}
