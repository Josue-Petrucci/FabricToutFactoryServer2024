package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.petrucci.javabeans.DangerLevel;
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
					// TODO: implement maintenance retrieval
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
		}
		list.addAll(maintenanceMap.values());
		return list;
	}
}
