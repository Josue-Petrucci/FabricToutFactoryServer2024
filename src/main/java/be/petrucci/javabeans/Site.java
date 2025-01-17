package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.petrucci.dao.DAOFactory;

public class Site implements Serializable {
	private static final long serialVersionUID = -2933081182814214954L;
	private int id;
	private String name;
	private String city;
	private Factory factory;
	private ArrayList<Zone> zones = new ArrayList<Zone>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	private ArrayList<MaintenanceManager> listMaintenanceManagers = new ArrayList<MaintenanceManager>();
	private ArrayList<MaintenanceWorker> listMaintenanceWorkers = new ArrayList<MaintenanceWorker>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}

	public ArrayList<MaintenanceManager> getListMaintenanceManagers() {
		return listMaintenanceManagers;
	}

	public void setListMaintenanceManagers(ArrayList<MaintenanceManager> listMaintenanceManagers) {
		this.listMaintenanceManagers = listMaintenanceManagers;
	}

	public ArrayList<MaintenanceWorker> getListMaintenanceWorkers() {
		return listMaintenanceWorkers;
	}

	public void setListMaintenanceWorkers(ArrayList<MaintenanceWorker> listMaintenanceWorkers) {
		this.listMaintenanceWorkers = listMaintenanceWorkers;
	}

	public Site() {
	}

	public Site(int idSite, String nameSite, String city, int idFactory, String nameFactory) {
		this.id = idSite;
		this.name = nameSite;
		this.city = city;
		this.factory = new Factory(idFactory, nameFactory, this);
	}

	public Site(int id, String name, String city, Factory factory, ArrayList<Zone> zones) {
		this.id = id;
		this.name = name;
		this.city = city;
		if (factory != null)
			this.factory = factory;
		else
			throw new IllegalArgumentException("Site factory must not be null");
		if (zones.size() >= 1)
			this.zones = zones;
		else
			throw new IllegalArgumentException("Site zone list must not be empty");
		this.machines = new ArrayList<Machine>();
		this.listMaintenanceManagers = new ArrayList<MaintenanceManager>();
		this.listMaintenanceWorkers = new ArrayList<MaintenanceWorker>();
	}

	public Site(int id, String name, String city, Factory factory, ArrayList<Zone> zones, ArrayList<Machine> machines,
			ArrayList<MaintenanceManager> listMaintenanceManagers,
			ArrayList<MaintenanceWorker> listMaintenanceWorkers) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		if (factory != null)
			this.factory = factory;
		else
			throw new IllegalArgumentException("Site factory must not be null");
		if (zones.size() >= 1)
			this.zones = zones;
		else
			throw new IllegalArgumentException("Site zone list must not be empty");
		this.machines = machines;
		this.listMaintenanceManagers = listMaintenanceManagers;
		this.listMaintenanceWorkers = listMaintenanceWorkers;
	}

	// Methods
	public void addZone(Zone zone) {
		if (!zones.contains(zone)) {
			zones.add(zone);
		}
	}

	// DAO methods
	public static ArrayList<Site> getAllSites() {
		DAOFactory dao = new DAOFactory();
		return dao.getSiteDAO().findAll();
	}

	@Override
	public boolean equals(Object obj) {
		Site s = null;
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		s = (Site) obj;
		return s.getName().equals(this.getName()) && s.getCity().equals(this.getCity());
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode() + this.getCity().hashCode();
	}

	@Override
	public String toString() {
		return "Site [id=" + id + ", name=" + name + ", city=" + city + "]";
	}
}
