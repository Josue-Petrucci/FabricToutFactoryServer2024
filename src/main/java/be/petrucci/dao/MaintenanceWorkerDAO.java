package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.Site;
import oracle.jdbc.OracleTypes;

public class MaintenanceWorkerDAO extends DAO<MaintenanceWorker>{

	public MaintenanceWorkerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MaintenanceWorker find(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MaintenanceWorker> findAll() {
		ArrayList<MaintenanceWorker> workers = new ArrayList<MaintenanceWorker>();
		MaintenanceWorker w = null;
		CallableStatement cstmt = null;
	    ResultSet rs = null;
		try {
			String query = "{CALL SeeAllEmployeesWorkers(?)}";
	        cstmt = conn.prepareCall(query);
	        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
	        cstmt.execute();
	        rs = (ResultSet) cstmt.getObject(1);
	        
	        int lastIdWorker = -1;
			while(rs.next()){
				int idWorker = rs.getInt("user_id");
				
				Site s = new Site(
						rs.getInt("site_id"),
						rs.getString("site_name"),
						rs.getString("site_city"),
						rs.getInt("factory_id"),
						rs.getString("factory_name"));
				
				Maintenance m = new Maintenance(
                        rs.getInt("maintenance_id"),
                        rs.getDate("maintenance_date"),
                        rs.getInt("maintenance_duration"),
                        rs.getString("maintenance_instructions"),
                        rs.getString("maintenance_report"),
                        MaintenanceStatus.valueOf(rs.getString("maintenance_status")));
				
				if(idWorker == lastIdWorker) {
					w.addMaintenance(m);
				} else {
					w = new MaintenanceWorker(
							rs.getInt("user_id"),
							rs.getString("user_lastname"),
							rs.getString("user_firstname"),
							rs.getInt("user_age"),
							rs.getString("user_address"),
							rs.getString("user_matricule"),
							rs.getString("user_password"),
							s);
					w.addMaintenance(m);
					workers.add(w);
				}
				lastIdWorker = idWorker;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (cstmt != null) cstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workers;
	}

}
