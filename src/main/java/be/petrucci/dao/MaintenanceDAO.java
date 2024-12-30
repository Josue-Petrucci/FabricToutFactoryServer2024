package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import be.petrucci.javabeans.Maintenance;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(Maintenance obj) {
		boolean success = false;
        String query = "{ call DeleteMaintenance(?) }";

        try (CallableStatement cs = this.conn.prepareCall(query)) {
            cs.setInt(1, obj.getId());
            cs.executeUpdate();
            success = true;
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return success;
	}

	public boolean update(Maintenance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public Maintenance find(Maintenance obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Maintenance> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean deleteWorkerMaintenance(Maintenance obj) {
		boolean success = false;
        String query = "{ call DeleteWorkerMaintenance(?) }";

        try (CallableStatement cs = this.conn.prepareCall(query)) {
            cs.setInt(1, obj.getId());
            cs.executeUpdate();
            success = true;
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return success;
	}

}
