package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

public class Zone implements Serializable {
	private static final long serialVersionUID = -7817333198883984982L;
	private int id;
	private char zoneLetter;
	private DangerLevel dangerLevel;
	private Site site;
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public char getZoneLetter() {
		return zoneLetter;
	}
	
	public void setZoneLetter(char zoneLetter) {
		this.zoneLetter = zoneLetter;
	}
	
	public DangerLevel getDangerLevel() {
		return dangerLevel;
	}
	
	public void setDangerLevel(DangerLevel dangerLevel) {
		this.dangerLevel = dangerLevel;
	}
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
	
	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}
	
	public Zone() {}
}
