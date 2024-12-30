package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import be.petrucci.connection.FabricToutConnection;
import be.petrucci.dao.DAOFactory;
import be.petrucci.dao.MaintenanceDAO;


public class Maintenance implements Serializable {
	private static final long serialVersionUID = 6578454947386542915L;
	private int id;
	private Date date;
	private int duration;
	private String instructions;
	private String report;
	private MaintenanceStatus status;
	private MaintenanceManager manager;
	private MaintenanceWorker worker;
	private Machine machine;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getInstructions() {
		return instructions;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public String getReport() {
		return report;
	}
	
	public void setReport(String report) {
		this.report = report;
	}
	
	public MaintenanceStatus getStatus() {
		return status;
	}
	
	public void setStatus(MaintenanceStatus status) {
		this.status = status;
	}

	public MaintenanceManager getManager() {
		return manager;
	}

	public void setManager(MaintenanceManager manager) {
		this.manager = manager;
	}

	public MaintenanceWorker getWorker() {
		return worker;
	}

	public void setWorker(MaintenanceWorker worker) {
		this.worker = worker;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	
	public Maintenance() {}
	
	//Methods
	public boolean deleteMaintenance() {
		DAOFactory daofact = new DAOFactory();
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO(FabricToutConnection.getInstance());
		if (!deleteWorkerMaintenance(maintenanceDAO)) {
	        return false;
	    }
    	if (!deleteMaintenance(daofact)) {
	        return false;
	    }
	    return true;
	}
	
	//DAO methods
	public boolean deleteMaintenance(DAOFactory daofact) {
		return daofact.getMaintenanceDAO().delete(this);
	}
	
	public boolean deleteWorkerMaintenance(MaintenanceDAO maintenanceDAO) {
		return maintenanceDAO.deleteWorkerMaintenance(this);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, duration, id, instructions, machine, manager, report, status, worker);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Maintenance other = (Maintenance) obj;
		return Objects.equals(date, other.date) && duration == other.duration && id == other.id
				&& Objects.equals(instructions, other.instructions) && Objects.equals(machine, other.machine)
				&& Objects.equals(manager, other.manager) && Objects.equals(report, other.report)
				&& status == other.status && Objects.equals(worker, other.worker);
	}

	@Override
	public String toString() {
		return "Maintenance [id=" + id + ", date=" + date + ", duration=" + duration + ", instructions=" + instructions
				+ ", report=" + report + ", status=" + status + "]";
	}
}
