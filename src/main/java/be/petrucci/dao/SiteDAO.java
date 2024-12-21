package be.petrucci.dao;

import java.sql.Connection;
import java.util.ArrayList;

import be.petrucci.javabeans.Site;

public class SiteDAO extends DAO<Site>{

	public SiteDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Site obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Site find(Site obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Site> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
