package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.Machine;

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
		// TODO Auto-generated method stub
		return null;
	}

}
