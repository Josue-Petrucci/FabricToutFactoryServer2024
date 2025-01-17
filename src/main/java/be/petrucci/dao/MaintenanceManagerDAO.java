package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.petrucci.javabeans.Factory;
import be.petrucci.javabeans.MaintenanceManager;
import be.petrucci.javabeans.Site;
import oracle.jdbc.OracleTypes;

public class MaintenanceManagerDAO extends DAO<MaintenanceManager> {

	public MaintenanceManagerDAO(Connection conn) {
		super(conn);
	}

	public boolean create(MaintenanceManager obj) {
		boolean success = false;
		String query = "{ call AddEmployee(?, ?, ?, ?, ?, ?, ?) }";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.setString(1, obj.getLastname());
			cs.setString(2, obj.getFirstname());
			cs.setInt(3, obj.getAge());
			cs.setString(4, obj.getAddress());
			cs.setString(5, obj.getMatricule());
			cs.setString(6, obj.getPassword());
			cs.setInt(7, obj.getSite().getId());

			cs.executeUpdate();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public boolean delete(MaintenanceManager obj) {
		return false;
	}

	public boolean update(MaintenanceManager obj) {
		return false;
	}

	public MaintenanceManager find(MaintenanceManager obj) {
		MaintenanceManager manager = null;

		Map<Integer, Factory> factoryMap = new HashMap<>();
		Map<Integer, Site> siteMap = new HashMap<>();

		String query = "{CALL GetManagerDetails(?, ?)}";

		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.setInt(1, obj.getId());
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();

			try (ResultSet rs = (ResultSet) cs.getObject(2)) {
				while (rs.next()) {
					if (manager == null) {
						manager = new MaintenanceManager();
						manager.setId(rs.getInt("manager_id"));
						manager.setLastname(rs.getString("manager_lastname"));
						manager.setFirstname(rs.getString("manager_firstname"));
						manager.setAge(rs.getInt("manager_age"));
						manager.setAddress(rs.getString("manager_address"));
						manager.setMatricule(rs.getString("manager_matricule"));
					}

					Factory factory = factoryMap.computeIfAbsent(rs.getInt("factory_id"), id -> {
						try {
							Factory f = new Factory();
							f.setId(id);
							f.setName(rs.getString("factory_name"));
							return f;
						} catch (SQLException e) {
							throw new RuntimeException("Erreur lors de la création de Factory", e);
						}
					});

					Site site = siteMap.computeIfAbsent(rs.getInt("site_id"), id -> {
						try {
							Site s = new Site();
							s.setId(id);
							s.setName(rs.getString("site_name"));
							s.setCity(rs.getString("site_city"));
							s.setFactory(factory);
							return s;
						} catch (SQLException e) {
							throw new RuntimeException("Erreur lors de la création de Site", e);
						}
					});

					manager.setSite(site);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manager;
	}

	public ArrayList<MaintenanceManager> findAll() {
		return null;
	}

}
