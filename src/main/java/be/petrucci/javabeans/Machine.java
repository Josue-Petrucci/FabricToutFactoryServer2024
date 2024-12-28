package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.petrucci.dao.DAOFactory;

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
	
	
	
	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, Zone zone) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		this.zones = new ArrayList<Zone>();
		addZone(zone);
	}
	
	public void addZone(Zone zone) {
		if(!zones.contains(zone)) {
			zones.add(zone);
		}
	}

	public static ArrayList<Machine> getAllMachine(){
		DAOFactory dao = new DAOFactory();
		return dao.getMachineDAO().findAll();
	}
	
	@Override
	public boolean equals(Object obj) {
		Machine m = null;
		if(obj == null || obj.getClass() == this.getClass()) {
			return true;
		}
		
		m = (Machine)obj;
		if(m.getId() == this.getId() & m.getSite().getName().equals(this.getSite().getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.hashCode();
	}
}
