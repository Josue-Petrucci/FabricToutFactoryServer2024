package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import be.petrucci.connection.FabricToutConnection;
import be.petrucci.dao.DAOFactory;
import be.petrucci.dao.MaintenanceWorkerDAO;

public class MaintenanceWorker extends User implements Serializable{
	private static final long serialVersionUID = -4939100544238173021L;
	private Site site;
	private ArrayList<Maintenance> maintenances = new ArrayList<Maintenance>();
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Maintenance> getMaintenances() {
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
	
	public ArrayList<Maintenance> seeWorkInProgress() {
		var maintenanceWorkerDAO = new MaintenanceWorkerDAO(FabricToutConnection.getInstance());
		return seeWorkInProgress(maintenanceWorkerDAO);
	}

	public ArrayList<Maintenance> seeWorkInProgress(MaintenanceWorkerDAO maintenanceWorkerDAO) {
		return maintenanceWorkerDAO.findWorkInProgress(this);
	}

	@Override
	public boolean equals(Object obj) {
		MaintenanceWorker w = null;
		if(obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		
		w = (MaintenanceWorker)obj;
		return w.getFirstname().equals(this.getFirstname()) && w.getLastname().equals(this.getLastname());
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
