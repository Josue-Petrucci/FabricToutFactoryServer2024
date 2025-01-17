package be.petrucci.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.petrucci.javabeans.User;
import oracle.jdbc.OracleTypes;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	public boolean create(User obj) {
		return false;
	}

	public boolean delete(User obj) {
		return false;
	}

	public boolean update(User obj) {
		return false;
	}

	public User find(User obj) {
		User user = new User();
		user.setId(0);

		String query = "{call Login(?,?,?)}";
		try (CallableStatement cs = this.conn.prepareCall(query)) {
			cs.setString(1, obj.getMatricule());
			cs.setString(2, obj.getPassword());
			cs.registerOutParameter(3, OracleTypes.CURSOR);

			cs.execute();

			try (ResultSet resultSet = (ResultSet) cs.getObject(3)) {
				if (resultSet.next()) {
					user = new User(resultSet.getInt("user_id"), resultSet.getString("user_lastname"),
							resultSet.getString("user_firstname"), resultSet.getInt("user_age"),
							resultSet.getString("user_address"), obj.getMatricule(), obj.getPassword());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public ArrayList<User> findAll() {
		return null;
	}

}
