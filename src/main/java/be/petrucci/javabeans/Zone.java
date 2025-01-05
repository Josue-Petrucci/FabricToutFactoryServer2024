package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import be.petrucci.dao.DAOFactory;

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

	public Zone() {
	}

	public Zone(int id, char zoneLetter, DangerLevel dangerLevel, Site site) {
		this.id = id;
		this.zoneLetter = zoneLetter;
		this.dangerLevel = dangerLevel;
		this.site = site;
	}

	// Methods
	public void addMachine(Machine machine) {
		if (!machines.contains(machine)) {
			machines.add(machine);
		}
	}

	// DAO methods
	public static ArrayList<Zone> getAllZones() {
		DAOFactory dao = new DAOFactory();
		return dao.getZoneDAO().findAll();
	}

	@Override
	public boolean equals(Object obj) {
		Zone z = null;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		z = (Zone) obj;
		return z.getId() == this.getId();
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", zoneLetter=" + zoneLetter + ", dangerLevel=" + dangerLevel + "]";
	}
}
