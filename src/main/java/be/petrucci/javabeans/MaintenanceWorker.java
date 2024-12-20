package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class MaintenanceWorker extends User implements Serializable{
	private static final long serialVersionUID = -4939100544238173021L;
	private Site site;
	private ArrayList<Maintenance> maintenance = new ArrayList<Maintenance>();
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(ArrayList<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}
	
	public MaintenanceWorker() {}
}
