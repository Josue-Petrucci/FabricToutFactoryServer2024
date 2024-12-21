package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.Maintenance;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Maintenance find(Maintenance obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Maintenance> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
