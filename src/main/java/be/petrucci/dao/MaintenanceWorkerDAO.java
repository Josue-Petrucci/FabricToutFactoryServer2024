package be.petrucci.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.Site;

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
		try {
			String query = "SELECT user_id, user_lastname, user_firstname, user_age, user_address, user_matricule, user_password, "
					+ "site_id, site_name, site_city, factory_id, factory_name, "
					+ "maintenance_id, maintenance_date, maintenance_duration, maintenance_instructions, maintenance_report, maintenance_status "
					+ "FROM Employee E, Site S, Factory F, Maintenance M "
					+ "WHERE E.site_id_FK = S.site_id AND S.factory_id_FK = F.factory_id AND E.user_id = M.manager_id_FK(+)";
			pst = conn.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Site s = new Site(
						rs.getInt("site_id"),
						rs.getString("site_name"),
						rs.getString("site_city"),
						rs.getInt("factory_id"),
						rs.getString("factory_name"));
				
				MaintenanceWorker w = new MaintenanceWorker(
						rs.getInt("user_id"),
						rs.getString("user_lastname"),
						rs.getString("user_firstname"),
						rs.getInt("user_age"),
						rs.getString("user_address"),
						rs.getString("user_matricule"),
						rs.getString("user_password"),
						s);
				
				workers.add(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) pst.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workers;
	}

}
