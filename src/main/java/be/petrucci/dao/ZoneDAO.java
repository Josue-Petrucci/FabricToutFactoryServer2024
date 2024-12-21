package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.Zone;

public class ZoneDAO extends DAO<Zone>{

	public ZoneDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Zone obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Zone find(Zone obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Zone> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
