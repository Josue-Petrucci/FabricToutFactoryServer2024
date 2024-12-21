package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.Factory;

public class FactoryDAO extends DAO<Factory>{

	public FactoryDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Factory obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Factory find(Factory obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Factory> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
