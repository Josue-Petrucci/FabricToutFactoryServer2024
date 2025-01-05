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
import be.petrucci.javabeans.Site;
import be.petrucci.javabeans.Zone;
import oracle.jdbc.OracleTypes;

public class ZoneDAO extends DAO<Zone> {

	public ZoneDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public Zone find(Zone obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Zone> findAll() {
		ArrayList<Zone> zoneList = new ArrayList<>();
		Map<Integer, Factory> factoryMap = new HashMap<>();
		Map<Integer, Site> siteMap = new HashMap<>();
		String query = "{call SeeAllZones(?)}";

		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();

			try (ResultSet rs = (ResultSet) cs.getObject(1)) {
				while (rs.next()) {
					int factoryId = rs.getInt("factory_id");
					String factoryName = rs.getString("factory_name");
					Factory factory = factoryMap.computeIfAbsent(factoryId, id -> {
						Factory newFactory = new Factory();
						newFactory.setId(factoryId);
						newFactory.setName(factoryName);
						return newFactory;
					});

					int siteId = rs.getInt("site_id");
					String siteName = rs.getString("site_name");
					String siteCity = rs.getString("site_city");
					Site site = siteMap.computeIfAbsent(siteId, id -> {
						Site newSite = new Site();
						newSite.setId(id);
						newSite.setName(siteName);
						newSite.setCity(siteCity);
						newSite.setFactory(factory);
						return newSite;
					});

					Zone zone = new Zone(rs.getInt("zone_id"), rs.getString("zone_letter").charAt(0),
							DangerLevel.valueOf(rs.getString("zone_danger_level")), site);

					zoneList.add(zone);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return zoneList;
	}

}
