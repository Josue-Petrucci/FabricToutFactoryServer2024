package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.sql.Types;
import java.util.stream.Collectors;

import be.petrucci.javabeans.Maintenance;

public class MaintenanceDAO extends DAO<Maintenance>{

	public MaintenanceDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Maintenance obj) {
		boolean result = false;
		CallableStatement cstmt = null;
	    int maintenanceId = -1;

	    try {
	    	String workerIds = obj.getWorkers().stream()
	                .map(w -> String.valueOf(w.getId()))
	                .collect(Collectors.joining(","));

	        String sql = "{CALL CreateMaintenance(?, ?, ?, ?, ?, ?, ?, ?)}";
	        cstmt = conn.prepareCall(sql);

	        cstmt.setDate(1, obj.getDate());
	        cstmt.setInt(2, obj.getDuration());
	        cstmt.setString(3, obj.getInstructions());
	        cstmt.setString(4, obj.getStatus().name());
	        cstmt.setInt(5, obj.getManager().getId());
	        cstmt.setInt(6, obj.getMachine().getId());
	        cstmt.setString(7, workerIds);
	        cstmt.registerOutParameter(8, Types.INTEGER);

	        cstmt.execute();

	        maintenanceId = cstmt.getInt(8);
	        if (maintenanceId > -1) {
	            result = true;
	        }	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        result = false;
	    } finally {
	        try {
	            if (cstmt != null) cstmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
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
		boolean success = false;
		final String query = "{ call UpdateMaintenance(?, ?, ?, ?, ?, ?) }";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.setInt(1, obj.getId());
			cs.setDate(2, obj.getDate());
			cs.setInt(3, obj.getDuration());
			cs.setString(4, obj.getInstructions());
			cs.setString(5, obj.getReport());
			cs.setString(6, String.valueOf(obj.getStatus()));
			cs.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
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
