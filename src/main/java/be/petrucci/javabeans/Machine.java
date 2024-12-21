package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Machine implements Serializable{
	private static final long serialVersionUID = -1046535624725789699L;
	private int id;
	private MachineType type;
	private double size;
	private MachineStatus status;
	private Site site;
	private ArrayList<Zone> zones;
	private ArrayList<Maintenance> maintenance = new ArrayList<Maintenance>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public MachineType getType() {
		return type;
	}
	
	public void setType(MachineType type) {
		this.type = type;
	}
	
	public double getSize() {
		return size;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public MachineStatus getStatus() {
		return status;
	}
	
	public void setStatus(MachineStatus status) {
		this.status = status;
	}
	
	public Site getSite() {
		return site;
	}
	
	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Zone> getZones() {
		return zones;
	}
	
	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}
	
	public ArrayList<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(ArrayList<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}

	public Machine() {}
}
