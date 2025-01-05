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

public class FactoryDAO extends DAO<Factory> {

	public FactoryDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public Factory find(Factory obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Factory> findAll() {
		ArrayList<Factory> factoryList = new ArrayList<>();
		Map<Integer, Factory> factoryMap = new HashMap<>();
		Map<Integer, Site> siteMap = new HashMap<>();
		String query = "{call SeeAllFactories(?)}";

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
						newFactory.setSites(new ArrayList<>());
						return newFactory;
					});

					int siteId = rs.getInt("site_id");
					String siteName = rs.getString("site_name");
					String siteCity = rs.getString("site_city");
					Site site = siteMap.computeIfAbsent(siteId, id -> {
						Site newSite = new Site();
						newSite.setId(siteId);
						newSite.setName(siteName);
						newSite.setCity(siteCity);
						newSite.setZones(new ArrayList<>());
						factory.getSites().add(newSite);
						return newSite;
					});

					Zone zone = new Zone();
					zone.setId(rs.getInt("zone_id"));
					zone.setZoneLetter(rs.getString("zone_letter").charAt(0));
					zone.setDangerLevel(DangerLevel.valueOf(rs.getString("zone_danger_level")));
					site.getZones().add(zone);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		factoryList.addAll(factoryMap.values());
		return factoryList;
	}

}
