package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.*;

import be.petrucci.dao.DAOFactory;

public class MaintenanceWorker extends User implements Serializable{
	private static final long serialVersionUID = -4939100544238173021L;
	@JsonManagedReference
	private Site site;
	@JsonManagedReference
	private ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Maintenance> getMaintenance() {
		return maintenances;
	}

	public void setMaintenances(ArrayList<Maintenance> maintenance) {
		this.maintenances = maintenance;
	}
	
	public MaintenanceWorker() {}
	
	public MaintenanceWorker(int id, String lastname, String firstname, int age, String address, String matricule, String password, Site site) {
		super(id, lastname, firstname, age, address, matricule, password);
		this.site = site;
	}
	
	public void addMaintenance(Maintenance maintenance) {
		if(!maintenances.contains(maintenance)) {
			maintenances.add(maintenance);
		}
	}
	
	public static ArrayList<MaintenanceWorker> getAllWorkers(){
		DAOFactory dao = new DAOFactory();
		return dao.getMaintenanceWorkerDAO().findAll();
	}
	
	@Override
	public boolean equals(Object obj) {
		MaintenanceWorker w = null;
		if(obj == null || obj.getClass() != this.getClass()) {
			return true;
		}
		
		w = (MaintenanceWorker)obj;
		if(w.getFirstname().equals(this.getFirstname()) & w.getLastname().equals(this.getLastname())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.getFirstname().hashCode() + this.getLastname().hashCode();
	}
	
	@Override
	public String toString() {
		return "MaintenanceWorker [id=" + getId() + ", lastname=" 
				+ getLastname() + ", firstname=" 
				+ getFirstname() + ", age=" + getAge() + ", address="
				+ getAddress() + ", matricule=" + getMatricule() 
				+ ", password=" + getPassword() + "]";
	}
}
