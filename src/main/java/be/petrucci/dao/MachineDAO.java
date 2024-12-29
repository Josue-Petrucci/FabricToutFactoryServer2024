package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.petrucci.javabeans.DangerLevel;
import be.petrucci.javabeans.Factory;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.Zone;
import oracle.jdbc.OracleTypes;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Machine obj) {
		boolean success = false;
		String query = "{ call AddMachine(?, ?, ?, ?) }";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
            cs.setString(1, String.valueOf(obj.getType()));
			cs.setDouble(2, obj.getSize());
			cs.setString(3, String.valueOf(obj.getStatus()));
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			
			cs.executeUpdate();
			obj.setId(cs.getInt(4));
			
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}

	public boolean delete(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public Machine find(Machine obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Machine> findAll() {
	    ArrayList<Machine> machines = new ArrayList<>();
	    Map<Integer, Site> siteMap = new HashMap<>();
	    Map<Integer, Zone> zoneMap = new HashMap<>();
	    Map<Integer, Factory> factoryMap = new HashMap<>();
	    Map<Integer, Machine> machineMap = new HashMap<>();
	    String query = "{call SeeAllMachines(?)}";

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
	                DangerLevel dangerLevel = DangerLevel.valueOf(rs.getString("zone_danger_level"));
	                Zone zone = zoneMap.computeIfAbsent(zoneId, id -> new Zone(zoneId, zoneLetter, dangerLevel, site));

	                int machineId = rs.getInt("machine_id");
	                MachineType machineType = MachineType.valueOf(rs.getString("machine_type"));
	                double machineSize = rs.getDouble("machine_size");
	                MachineStatus machineStatus = MachineStatus.valueOf(rs.getString("machine_status"));
	                Machine machine = machineMap.computeIfAbsent(machineId, id -> {
	                    Machine m = new Machine();
	                    m.setId(machineId);
	                    m.setType(machineType);
	                    m.setSize(machineSize);
	                    m.setStatus(machineStatus);
	                    m.setSite(site);
	                    m.setZones(new ArrayList<>());
	                    return m;
	                });
                    machine.getZones().add(zone);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    machines.addAll(machineMap.values());
	    return machines;
	}

	
	public boolean createMachineLocation(int machineId, int zoneId) {
		boolean success = false;
		String query = "{ call AddMachineLocation(?, ?) }";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
            cs.setInt(1, machineId);
			cs.setInt(2, zoneId);
			cs.executeUpdate(); 
			success = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return success;
	}
}
