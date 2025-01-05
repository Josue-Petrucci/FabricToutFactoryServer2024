package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import be.petrucci.connection.FabricToutConnection;
import be.petrucci.dao.DAOFactory;
import be.petrucci.dao.MachineDAO;

public class Machine implements Serializable {
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

	public Machine() {
	}

	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, Zone zone) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		this.zones = new ArrayList<Zone>();
		addZone(zone);
	}

	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, ArrayList<Zone> zones) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		if (zones.size() >= 1)
			this.zones = zones;
		else
			throw new IllegalArgumentException("Machine zone list must not be empty");
		this.maintenance = new ArrayList<Maintenance>();
	}

	public Machine(int id, MachineType type, double size, MachineStatus status, Site site, ArrayList<Zone> zones,
			ArrayList<Maintenance> maintenance) {
		this.id = id;
		this.type = type;
		this.size = size;
		this.status = status;
		this.site = site;
		if (zones.size() >= 1)
			this.zones = zones;
		else
			throw new IllegalArgumentException("Machine zone list must not be empty");
		this.maintenance = maintenance;
	}

	// Methods
	public void addZone(Zone zone) {
		if (!zones.contains(zone)) {
			zones.add(zone);
		}
	}

	public boolean addMachine() {
		DAOFactory daofact = new DAOFactory();
		MachineDAO machineDAO = new MachineDAO(FabricToutConnection.getInstance());
		if (this.getId() == 0 || !createMachine(daofact)) {
			return false;
		}
		for (Zone zone : this.getZones()) {
			if (!createMachineLocation(machineDAO, zone.getId())) {
				return false;
			}
		}
		return true;
	}

	public boolean deleteMachine() {
		DAOFactory daofact = new DAOFactory();
		MachineDAO machineDAO = new MachineDAO(FabricToutConnection.getInstance());
		return deleteMachineLocation(machineDAO) && deleteMachine(daofact);
	}

	// DAO methods
	public boolean createMachine(DAOFactory daofact) {
		return daofact.getMachineDAO().create(this);
	}

	public boolean createMachineLocation(MachineDAO machineDAO, int zoneId) {
		return machineDAO.createMachineLocation(this.getId(), zoneId);
	}

	public boolean deleteMachine(DAOFactory daofact) {
		return daofact.getMachineDAO().delete(this);
	}

	public boolean deleteMachineLocation(MachineDAO machineDAO) {
		return machineDAO.deleteMachineLocation(this);
	}

	public static ArrayList<Machine> getAllMachines() {
		DAOFactory dao = new DAOFactory();
		return dao.getMachineDAO().findAll();
	}

	public boolean updateMachine() {
		DAOFactory daofact = new DAOFactory();
		return updateMachine(daofact);
	}

	public boolean updateMachine(DAOFactory daofact) {
		return daofact.getMachineDAO().update(this);
	}

	@Override
	public boolean equals(Object obj) {
		Machine m = null;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		m = (Machine) obj;
		return m.getId() == this.getId() && m.getSite().getName().equals(this.getSite().getName());
	}

	@Override
	public int hashCode() {
		return this.hashCode();
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", type=" + type + ", size=" + size + ", status=" + status + "]";
	}

}
