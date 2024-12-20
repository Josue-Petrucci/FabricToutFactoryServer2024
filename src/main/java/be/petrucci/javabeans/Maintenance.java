package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.Date;

import be.petrucci.enums.MaintenanceStatus;


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
}
