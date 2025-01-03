package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.MaintenanceManager;

public class MaintenanceManagerDAO extends DAO<MaintenanceManager> {

	public MaintenanceManagerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaintenanceManager find(MaintenanceManager obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MaintenanceManager> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
