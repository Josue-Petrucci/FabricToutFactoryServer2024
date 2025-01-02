package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import be.petrucci.javabeans.DangerLevel;
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

	@Override
	public boolean create(Machine obj) {
		// TODO Auto-generated method stub
		return false;
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
		Machine m = null;
		ArrayList<Machine> machines = new ArrayList<Machine>();
		CallableStatement cstmt = null;
	    ResultSet rs = null;
	    
		try {
	        String query = "{CALL SeeAllMachines(?)}";
	        cstmt = conn.prepareCall(query);
	        cstmt.registerOutParameter(1, OracleTypes.CURSOR);
	        cstmt.execute();
	        rs = (ResultSet) cstmt.getObject(1);
	        
	        int lastid = -1;
			while(rs.next()){
				int currentId = rs.getInt("machine_id");
				
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
					            
	            if (currentId == lastid && m != null) {
	                m.addZone(z);
	            } else {
	                m = new Machine(
	                    currentId,
	                    MachineType.valueOf(rs.getString("machine_type")),
	                    rs.getDouble("machine_size"),
	                    MachineStatus.valueOf(rs.getString("machine_status")),
	                    s, z);

	                machines.add(m);
	            }
	            lastid = currentId;
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
		return machines;
	}
}
