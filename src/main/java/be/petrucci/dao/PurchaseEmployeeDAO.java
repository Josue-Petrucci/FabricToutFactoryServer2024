package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.PurchaseEmployee;

public class PurchaseEmployeeDAO extends DAO<PurchaseEmployee>{

	public PurchaseEmployeeDAO(Connection conn) {
		super(conn);
	}

	public boolean create(PurchaseEmployee obj) {
		return false;
	}

	public boolean delete(PurchaseEmployee obj) {
		return false;
	}

	public boolean update(PurchaseEmployee obj) {
		return false;
	}

	public PurchaseEmployee find(PurchaseEmployee obj) {
		return null;
	}

	public ArrayList<PurchaseEmployee> findAll() {
		return null;
	}

}
