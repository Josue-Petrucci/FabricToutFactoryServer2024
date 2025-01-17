package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import be.petrucci.dao.DAOFactory;

public class MaintenanceManager extends User implements Serializable {
	private static final long serialVersionUID = -9163803205862632545L;
	private Site site;
	private ArrayList<Maintenance> maintenance = new ArrayList<Maintenance>();

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public ArrayList<Maintenance> getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(ArrayList<Maintenance> maintenance) {
		this.maintenance = maintenance;
	}

	public MaintenanceManager() {
	}

	public MaintenanceManager(int id, String lastname, String firstname, int age, String address, String matricule,
			String password, Site site, ArrayList<Maintenance> maintenanceList) {
		super(id, lastname, firstname, age, address, matricule, password);
		this.site = site;
		this.maintenance = maintenanceList;
	}

	public MaintenanceManager(int id) {
		this.setId(id);
	}

	// Methods
	public boolean addMaintenanceManager() {
		DAOFactory daofact = new DAOFactory();
		if (!createMaintenanceManager(daofact)) {
			return false;
		}
		return true;
	}

	// DAO methods
	public boolean createMaintenanceManager(DAOFactory daofact) {
		return daofact.getMaintenanceManagerDAO().create(this);
	}

	public static MaintenanceManager getManagerDetail(MaintenanceManager manager) {
		DAOFactory dao = new DAOFactory();
		return dao.getMaintenanceManagerDAO().find(manager);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maintenance, site);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || getClass() != obj.getClass()) {
			return false;
		}
		MaintenanceManager other = (MaintenanceManager) obj;
		return Objects.equals(maintenance, other.maintenance) && Objects.equals(site, other.site);
	}

	@Override
	public String toString() {
		return "MaintenanceManager [id=" + getId() + ", lastname=" + getLastname() + ", firstname=" + getFirstname()
				+ ", age=" + getAge() + ", address=" + getAddress() + ", matricule=" + getMatricule() + ", password="
				+ getPassword() + "]";
	}
}
