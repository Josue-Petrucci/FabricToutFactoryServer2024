package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import be.petrucci.javabeans.DangerLevel;
import be.petrucci.javabeans.Factory;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.Zone;
import oracle.jdbc.OracleTypes;

public class MaintenanceWorkerDAO extends DAO<MaintenanceWorker>{

	public MaintenanceWorkerDAO(Connection conn) {
		super(conn);
	}

	public boolean create(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public MaintenanceWorker find(MaintenanceWorker obj) {
		// TODO Auto-generated method stub
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

	                Optional.ofNullable(maintenance).ifPresent(worker.getMaintenances()::add);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    workers.addAll(workerMap.values());
	    return workers;
	}

	public ArrayList<Maintenance> findWorkInProgress(MaintenanceWorker obj) {
		ArrayList<Maintenance> list = new ArrayList<>();
		Map<Integer, Maintenance> maintenanceMap = new HashMap<>();
		Map<Integer, Machine> machineMap = new HashMap<>();
		Map<Integer, Zone> zoneMap = new HashMap<>();
		final String query = "{ call seeWorkerWorkInProgress(?, ?) }";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2, obj.getId());
			cs.execute();

			try (ResultSet rs = (ResultSet)cs.getObject(1)) {
				while (rs.next()) {
					int zone_id = rs.getInt("zone_id");
					char zone_letter = rs.getString("zone_letter").charAt(0);
					DangerLevel zone_danger_level = DangerLevel.valueOf(rs.getString("zone_danger_level"));
					Zone zone = zoneMap.computeIfAbsent(zone_id, id -> new Zone(
						zone_id,
						zone_letter,
						zone_danger_level,
						null
					));

					int machine_id = rs.getInt("machine_id");
					MachineType machine_type = MachineType.valueOf(rs.getString("machine_type"));
					double machine_size = rs.getDouble("machine_size");
					MachineStatus machine_status = MachineStatus.valueOf(rs.getString("machine_status"));
					Machine machine = machineMap.computeIfAbsent(machine_id, id -> {
						Machine m = new Machine();
						m.setId(machine_id);
						m.setType(machine_type);
						m.setSize(machine_size);
						m.setStatus(machine_status);
						m.setZones(new ArrayList<>());
						return m;
					});
					machine.getZones().add(zone);

					int maintenance_id = rs.getInt("maintenance_id");
					Date maintenance_date = rs.getDate("maintenance_date");
					int maintenance_duration = rs.getInt("maintenance_duration");
					String maintenance_instructions = rs.getString("maintenance_instructions");
					String maintenance_report = rs.getString("maintenance_report");
					MaintenanceStatus maintenance_status = MaintenanceStatus.valueOf(rs.getString("maintenance_status"));
					maintenanceMap.computeIfAbsent(maintenance_id, id -> {
						Maintenance m = new Maintenance();
						m.setId(maintenance_id);
						m.setDate(maintenance_date);
						m.setDuration(maintenance_duration);
						m.setInstructions(maintenance_instructions);
						m.setReport(maintenance_report);
						m.setStatus(maintenance_status);
						m.setMachine(machine);
						return m;
					});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		list.addAll(maintenanceMap.values());
		return list;
	}
}
