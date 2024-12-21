package be.petrucci.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public abstract class DAO<T> {
	protected Connection conn = null;
	protected PreparedStatement pst = null;
	
	public DAO(Connection conn) {
		this.conn = conn;
	}
	
	public abstract boolean create(T obj);
	public abstract boolean delete(T obj);
	public abstract boolean update(T obj);
	public abstract T find(T obj);
	public abstract ArrayList<T> findAll();
}
