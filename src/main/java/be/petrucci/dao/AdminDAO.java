package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.Admin;

public class AdminDAO extends DAO<Admin> {

	public AdminDAO(Connection conn) {
		super(conn);
	}
	
	public boolean create(Admin obj) {
		return false;
	}

	public boolean delete(Admin obj) {
		return false;
	}

	public boolean update(Admin obj) {
		return false;
	}

	public Admin find(Admin obj) {
		return null;
	}

	public ArrayList<Admin> findAll() {
		return null;
	}
}
