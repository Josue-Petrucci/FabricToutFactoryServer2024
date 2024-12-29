package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.petrucci.dao.DAOFactory;

public class Factory implements Serializable {
	private static final long serialVersionUID = -4655307262531040065L;
	private int id;
	private String name;
	private ArrayList<Site> sites;
	
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

	public ArrayList<Site> getSites() {
		return sites;
	}

	public void setSites(ArrayList<Site> sites) {
		this.sites = sites;
	}

	public Factory() {}
	
	public Factory(int id, String name, Site site) {
		this.id = id;
		this.name = name;
		sites = new ArrayList<Site>();
		addSite(site);
	}

	public Factory(int id, String name, ArrayList<Site> sites) {
		super();
		this.id = id;
		this.name = name;
		this.sites = sites;
	}

	//Methods
	public void addSite(Site site) {
		if(!sites.contains(site)) {
			sites.add(site);
		}
	}
	
	//DAO methods
	public static ArrayList<Factory> getAllFactories() {
		DAOFactory dao = new DAOFactory();
		return dao.getFactoryDAO().findAll();
	}
	
	@Override
	public boolean equals(Object obj) {
		Factory f = null;
		if(obj == null || obj.getClass() == this.getClass()) {
			return true;
		}
		
		f = (Factory)obj;
		if(f.getName().equals(this.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	@Override
	public String toString() {
		return "Factory [id=" + id + ", name=" + name + "]";
	}
}
