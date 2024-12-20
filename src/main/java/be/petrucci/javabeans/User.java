package be.petrucci.javabeans;

import java.io.Serializable;

public abstract class User implements Serializable {
	private static final long serialVersionUID = -5407718036535653341L;
	private int id;
	private String lastname;
	private String firstname;
	private int age;
	private String address;
	private int matricule;
	private String password;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getMatricule() {
		return matricule;
	}
	
	public void setMatricule(int matricule) {
		this.matricule = matricule;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {}
}
