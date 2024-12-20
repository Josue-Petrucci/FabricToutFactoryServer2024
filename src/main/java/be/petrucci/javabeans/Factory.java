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
}
