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

public class SiteDAO extends DAO<Site> {

	public SiteDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public Site find(Site obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Site> findAll() {
		ArrayList<Site> siteList = new ArrayList<>();
		Map<Integer, Site> siteMap = new HashMap<>();
		Map<Integer, Factory> factoryMap = new HashMap<>();
		String query = "{call SeeAllSites(?)}";

		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();

			try (ResultSet rs = (ResultSet) cs.getObject(1)) {
				while (rs.next()) {
					int siteId = rs.getInt("site_id");
					String siteName = rs.getString("site_name");
					String siteCity = rs.getString("site_city");
					int factoryId = rs.getInt("factory_id");
					String factoryName = rs.getString("factory_name");

					Factory factory = factoryMap.computeIfAbsent(factoryId, id -> {
						Factory newFactory = new Factory();
						newFactory.setId(factoryId);
						newFactory.setName(factoryName);
						return newFactory;
					});

					Site site = siteMap.computeIfAbsent(siteId, id -> {
						Site newSite = new Site();
						newSite.setId(siteId);
						newSite.setName(siteName);
						newSite.setCity(siteCity);
						newSite.setFactory(factory);
						newSite.setZones(new ArrayList<>());
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

		siteList.addAll(siteMap.values());
		return siteList;
	}

}
