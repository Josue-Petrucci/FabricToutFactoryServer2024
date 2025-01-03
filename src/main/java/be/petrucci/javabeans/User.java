package be.petrucci.javabeans;

import java.io.Serializable;
import java.util.Objects;

import be.petrucci.dao.DAOFactory;

public class User implements Serializable {
	private static final long serialVersionUID = -5407718036535653341L;
	private int id;
	private String lastname;
	private String firstname;
	private int age;
	private String address;
	private String matricule;
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
	
	public String getMatricule() {
		return matricule;
	}
	
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {}

	public User(int id, String lastname, String firstname, int age, String address, String matricule, String password) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.age = age;
		this.address = address;
		this.matricule = matricule;
		this.password = password;
	}
	
	
	//Methods
	public static User login(User user) {
		if ((user.getMatricule() != null && !user.getMatricule().equals("")) 
				&& (user.getPassword() != null && !user.getPassword().equals("")) 
				&& user.getLastname() == null && user.getFirstname() == null 
				&& user.getAge() == 0 && user.getAddress() == null) {
			User newUser = findUser(user);
			return newUser;
        } else if (user.getLastname() == null || user.getFirstname() == null 
				|| user.getAge() == 0 || user.getAddress() == null 
				|| user.getMatricule() == null || user.getPassword() == null) {
			return null;
		}
		return user;
	}
	
	//DAO Methods
	private static User findUser(User user) {
		DAOFactory daofact = new DAOFactory();
		return daofact.getUserDAO().find(user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, age, firstname, id, lastname, matricule, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(firstname, other.firstname)
				&& id == other.id && Objects.equals(lastname, other.lastname)
				&& Objects.equals(matricule, other.matricule) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", age=" + age + ", address="
				+ address + ", matricule=" + matricule + ", password=" + password + "]";
	}
}
