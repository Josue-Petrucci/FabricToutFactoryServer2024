package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.PurchaseEmployee;

public class PurchaseEmployeeDAO extends DAO<PurchaseEmployee>{

	public PurchaseEmployeeDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(PurchaseEmployee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(PurchaseEmployee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PurchaseEmployee obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PurchaseEmployee find(PurchaseEmployee obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PurchaseEmployee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
