package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import be.petrucci.javabeans.Factory;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.Site;
import oracle.jdbc.OracleTypes;

public class MaintenanceWorkerDAO extends DAO<MaintenanceWorker>{

	public MaintenanceWorkerDAO(Connection conn) {
		super(conn);
	}

	public boolean create(MaintenanceWorker obj) {
		return false;
	}

	public boolean delete(MaintenanceWorker obj) {
		return false;
	}

	public boolean update(MaintenanceWorker obj) {
		return false;
	}

	public MaintenanceWorker find(MaintenanceWorker obj) {
		return null;
	}

	public ArrayList<MaintenanceWorker> findAll() {
	    ArrayList<MaintenanceWorker> workers = new ArrayList<>();
	    Map<Integer, Site> siteMap = new HashMap<>();
	    Map<Integer, Factory> factoryMap = new HashMap<>();
	    Map<Integer, MaintenanceWorker> workerMap = new HashMap<>();
	    String query = "{CALL SeeAllEmployeesWorkers(?)}";

	    try (CallableStatement cs = this.conn.prepareCall(query)) {
	        cs.registerOutParameter(1, OracleTypes.CURSOR);
	        cs.execute();

	        try (ResultSet rs = (ResultSet) cs.getObject(1)) {
	            while (rs.next()) {
	                int factoryId = rs.getInt("factory_id");
	                String factoryName = rs.getString("factory_name");
	                Factory factory = factoryMap.computeIfAbsent(factoryId, id -> {
	                    Factory f = new Factory();
	                    f.setId(factoryId);
	                    f.setName(factoryName);
	                    return f;
	                });

	                int siteId = rs.getInt("site_id");
	                String siteName = rs.getString("site_name");
	                String siteCity = rs.getString("site_city");
	                Site site = siteMap.computeIfAbsent(siteId, id -> {
	                    Site s = new Site();
	                    s.setId(siteId);
	                    s.setName(siteName);
	                    s.setCity(siteCity);
	                    s.setFactory(factory);
	                    return s;
	                });

	                int workerId = rs.getInt("user_id");
	                String workerLastname = rs.getString("user_lastname");
	                String workerFirstname = rs.getString("user_firstname");
	                int workerAge = rs.getInt("user_age");
	                String workerAddress = rs.getString("user_address");
	                String workerMatricule = rs.getString("user_matricule");
	                
	                MaintenanceWorker worker = workerMap.computeIfAbsent(workerId, id -> {
	                    MaintenanceWorker w = new MaintenanceWorker();
	                    w.setId(workerId);
	                    w.setLastname(workerLastname);
	                    w.setFirstname(workerFirstname);
	                    w.setAge(workerAge);
	                    w.setAddress(workerAddress);
	                    w.setMatricule(workerMatricule);
	                    w.setPassword(null);
	                    w.setSite(site);
	                    w.setMaintenances(new ArrayList<>());
	                    return w;
	                });

	                Maintenance maintenance = (rs.getInt("maintenance_id") > 0) 
	                	    ? new Maintenance(
	                	        rs.getInt("maintenance_id"),
	                	        rs.getDate("maintenance_date"),
	                	        rs.getInt("maintenance_duration"),
	                	        rs.getString("maintenance_instructions"),
	                	        rs.getString("maintenance_report"),
	                	        MaintenanceStatus.valueOf(rs.getString("maintenance_status"))
	                	    )
	                	    : null;

	                Optional.ofNullable(maintenance).ifPresent(worker.getMaintenance()::add);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    workers.addAll(workerMap.values());
	    return workers;
	}

}
