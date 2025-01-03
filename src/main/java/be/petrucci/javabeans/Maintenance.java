package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.Objects;

import be.petrucci.connection.FabricToutConnection;
import be.petrucci.dao.DAOFactory;
import be.petrucci.dao.MaintenanceDAO;
import java.sql.Date;
import java.util.ArrayList;

public class Maintenance implements Serializable {
	private static final long serialVersionUID = 6578454947386542915L;
	private int id;
	private Date date;
	private int duration;
	private String instructions;
	private String report;
	private MaintenanceStatus status;
	private MaintenanceManager manager;
	private ArrayList<MaintenanceWorker> workers;
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

	public ArrayList<MaintenanceWorker> getWorkers() {
		return workers;
	}

	public void setWorker(ArrayList<MaintenanceWorker> workers) {
		this.workers = workers;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	
	public Maintenance() {}
	
	public Maintenance(int id, Date date, int duration, String instruction, String report, MaintenanceStatus status) {
		this.id = id;
		this.date = date;
		this.duration = duration;
		this.instructions = instruction;
		this.report = report;
		this.status = status;
	}

	public Maintenance(int id, Date date, int duration, String instructions, String report, MaintenanceStatus status,
			MaintenanceManager manager, MaintenanceWorker worker, Machine machine) {
		this(id, date, duration, instructions, report, status);
		this.manager = manager;
		this.machine = machine;
		addWorker(worker);
	}
	
	public Maintenance(Date date, int duration, String instruction, MaintenanceStatus status, Machine machine, MaintenanceManager manager, ArrayList<MaintenanceWorker> workers) {
		this.date = date;
		this.duration = duration;
		this.instructions = instruction;
		this.status = status;
		this.machine = machine;
		this.manager = manager;
		this.workers = workers;
	}
	
	public void addWorker(MaintenanceWorker worker) {
		if(!workers.contains(worker)) {
			workers.add(worker);
		}
	}

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
		
	public boolean createMaintenance() {
		DAOFactory dao = new DAOFactory();
		return dao.getMaintenanceDAO().create(this);
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
				&& status == other.status && Objects.equals(workers, other.workers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, duration, id, instructions, machine, manager, report, status, workers);
	}
	
	@Override
	public String toString() {
		return "Maintenance [id=" + id + ", date=" + date + ", duration=" + duration + ", instructions=" + instructions
				+ ", report=" + report + ", status=" + status + "]";
	}
}
