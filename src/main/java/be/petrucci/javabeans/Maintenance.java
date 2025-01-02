package be.petrucci.javabeans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import be.petrucci.dao.DAOFactory;


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

	public boolean createMaintenance() {
		DAOFactory dao = new DAOFactory();
		return dao.getMaintenanceDAO().create(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		Maintenance m = null;
		if(obj == null || obj.getClass() != this.getClass()) {
			return true;
		}
		
		m = (Maintenance)obj;
		if(m.getId() == this.getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.getInstructions().hashCode();
	}
}
