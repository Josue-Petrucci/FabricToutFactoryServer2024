package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public void addSite(Site site) {
		if(!sites.contains(site)) {
			sites.add(site);
		}
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
}
