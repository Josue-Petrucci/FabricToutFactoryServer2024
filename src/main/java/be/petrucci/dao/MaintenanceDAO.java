package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.sql.Types;
import java.util.stream.Collectors;

import be.petrucci.javabeans.DangerLevel;
import be.petrucci.javabeans.Factory;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.Maintenance;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.MaintenanceStatus;
import be.petrucci.javabeans.MaintenanceWorker;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.Zone;
import oracle.jdbc.OracleTypes;

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
		return null;
	}

	public ArrayList<Maintenance> findAll() {
		ArrayList<Maintenance> maintenances = new ArrayList<>();

        Map<Integer, Factory> factoryMap = new HashMap<>();
        Map<Integer, Site> siteMap = new HashMap<>();
        Map<Integer, Zone> zoneMap = new HashMap<>();
        Map<Integer, Machine> machineMap = new HashMap<>();
        Map<Integer, Maintenance> maintenanceMap = new HashMap<>();

        String query = "{CALL SeeAllMaintenance(?)}";

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

                    int zoneId = rs.getInt("zone_id");
                    char zoneLetter = rs.getString("zone_letter").charAt(0);
                    String zoneDangerLevel = rs.getString("zone_danger_level");
                    Zone zone = zoneMap.computeIfAbsent(zoneId, id -> {
                        Zone z = new Zone();
                        z.setId(zoneId);
                        z.setZoneLetter(zoneLetter);
                        z.setDangerLevel(DangerLevel.valueOf(zoneDangerLevel));
                        z.setSite(site);
                        return z;
                    });

                    int machineId = rs.getInt("machine_id");
                    String machineType = rs.getString("machine_type");
                    double machineSize = rs.getDouble("machine_size");
                    String machineStatus = rs.getString("machine_status");
                    Machine machine = machineMap.computeIfAbsent(machineId, id -> {
                        Machine m = new Machine();
                        m.setId(machineId);
                        m.setType(MachineType.valueOf(machineType));
                        m.setSize(machineSize);
                        m.setStatus(MachineStatus.valueOf(machineStatus));
                        m.setZones(new ArrayList<>());
                        return m;
                    });
                    
                    Optional.ofNullable(machine).map(Machine::getZones) 
                    .filter(zones -> zones.stream().noneMatch(z -> z.getId() == zone.getId())) 
                    .ifPresent(zones -> zones.add(zone));
                    
                    int managerId = rs.getInt("manager_id");
                    String managerLastname = rs.getString("manager_lastname");
                    String managerFirstname = rs.getString("manager_firstname");
                    int managerAge = rs.getInt("manager_age");
                    String managerAddress = rs.getString("manager_address");
                    String managerMatricule = rs.getString("manager_matricule");
                    MaintenanceManager manager = new MaintenanceManager();
                    manager.setId(managerId);
                    manager.setLastname(managerLastname);
                    manager.setFirstname(managerFirstname);
                    manager.setAge(managerAge);
                    manager.setAddress(managerAddress);
                    manager.setMatricule(managerMatricule);

                    int workerId = rs.getInt("worker_id");
                    String workerLastname = rs.getString("worker_lastname");
                    String workerFirstname = rs.getString("worker_firstname");
                    int workerAge = rs.getInt("worker_age");
                    String workerAddress = rs.getString("worker_address");
                    String workerMatricule = rs.getString("worker_matricule");
                    MaintenanceWorker worker = new MaintenanceWorker();
                    worker.setId(workerId);
                    worker.setLastname(workerLastname);
                    worker.setFirstname(workerFirstname);
                    worker.setAge(workerAge);
                    worker.setAddress(workerAddress);
                    worker.setMatricule(workerMatricule);

                    try {
                        Maintenance maintenance = (rs.getInt("maintenance_id") > 0)
                            ? maintenanceMap.computeIfAbsent(rs.getInt("maintenance_id"), id -> {
                                try {
                                    return new Maintenance(
                                        rs.getDate("maintenance_date"),
                                        rs.getInt("maintenance_duration"),
                                        rs.getString("maintenance_instructions"),
                                        rs.getString("maintenance_report"),
                                        MaintenanceStatus.valueOf(rs.getString("maintenance_status")),
                                        machine,
                                        manager,
                                        new ArrayList<>(),
                                        rs.getInt("maintenance_id")
                                    );
                                } catch (SQLException e) {
                                    throw new RuntimeException("Erreur lors de la lecture des données de maintenance", e);
                                }
                            })
                            : null;

                        Optional.ofNullable(maintenance).ifPresent(m -> {
                            m.getWorkers().add(worker);
                        });

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        maintenances.addAll(maintenanceMap.values());
        return maintenances;
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
