package be.petrucci.javabeans;

import java.io.Serializable;

public class Admin extends User implements Serializable {
	private static final long serialVersionUID = -4621245361944258008L;

	public Admin() {
	}

	public Admin(int id, String lastname, String firstname, int age, String address, String matricule,
			String password) {
		super(id, lastname, firstname, age, address, matricule, password);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Admin [id=" + getId() + ", lastname=" + getLastname() + ", firstname=" + getFirstname() + ", age="
				+ getAge() + ", address=" + getAddress() + ", matricule=" + getMatricule() + ", password="
				+ getPassword() + "]";
	}
}
