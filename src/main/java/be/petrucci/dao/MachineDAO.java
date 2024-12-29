package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.petrucci.javabeans.DangerLevel;
import be.petrucci.javabeans.Machine;
import be.petrucci.javabeans.MachineStatus;
import be.petrucci.javabeans.MachineType;
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.Zone;

public class MachineDAO extends DAO<Machine>{

	public MachineDAO(Connection conn) {
		super(conn);
	}

	@Override
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

	@Override
	public boolean delete(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Machine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Machine find(Machine obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Machine> findAll() {
		ArrayList<Machine> machines = new ArrayList<Machine>();
		try {
			String query = "SELECT machine_id, machine_type, machine_size, machine_status, zone_id, zone_letter, zone_danger_level, "
					+ "site_id, site_name, site_city, factory_id, factory_name "
					+ "FROM Machine M, Zone Z, Site S, Factory F "
					+ "WHERE M.zone_id_FK=Z.zone_id AND Z.site_id_FK=S.site_id AND S.factory_id_FK=F.factory_id";
			pst = conn.prepareStatement(query);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Site s = new Site(
						rs.getInt("site_id"),
						rs.getString("site_name"),
						rs.getString("site_city"),
						rs.getInt("factory_id"),
						rs.getString("factory_name"));
				
				Zone z = new Zone(
						rs.getInt("zone_id"),
						rs.getString("zone_letter").charAt(0),
						DangerLevel.valueOf(rs.getString("zone_danger_level")),
						s);
				
				Machine m = new Machine(
						rs.getInt("machine_id"),
						MachineType.valueOf(rs.getString("machine_type")),
						rs.getDouble("machine_size"),
						MachineStatus.valueOf(rs.getString("machine_status")),
						s, z);
				
				machines.add(m);
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
