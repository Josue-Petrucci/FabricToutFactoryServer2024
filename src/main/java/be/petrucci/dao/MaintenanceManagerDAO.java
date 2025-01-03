package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import be.petrucci.javabeans.MaintenanceManager;

public class MaintenanceManagerDAO extends DAO<MaintenanceManager> {

	public MaintenanceManagerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(MaintenanceManager obj) {
		boolean success = false;
		String query = "{ call AddEmployee(?, ?, ?, ?, ?, ?, ?) }";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
            cs.setString(1, obj.getLastname());
			cs.setString(2, obj.getFirstname());
			cs.setInt(3, obj.getAge());
			cs.setString(4, obj.getAddress());
			cs.setString(5, obj.getMatricule());
			cs.setString(6, obj.getPassword());
			cs.setInt(7, obj.getSite().getId());
			
			cs.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
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
