package be.petrucci.javabeans;

import java.io.Serializable;

public class PurchaseEmployee extends User implements Serializable{
	private static final long serialVersionUID = -6209061187064734160L;

	public PurchaseEmployee() {}

	public PurchaseEmployee(int id, String lastname, String firstname, int age, String address, String matricule,
			String password) {
		super(id, lastname, firstname, age, address, matricule, password);
	}
	@Override
	public String toString() {
		return "PurchaseEmployee [id=" + getId() + ", lastname=" 
				+ getLastname() + ", firstname=" 
				+ getFirstname() + ", age=" + getAge() + ", address="
				+ getAddress() + ", matricule=" + getMatricule() 
				+ ", password=" + getPassword() + "]";
	}
}
