package be.petrucci.dao;

import be.petrucci.javabeans.*;


public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY = 0;
	
	public abstract DAO<Admin> getAdminDAO();
	public abstract DAO<Factory> getFactoryDAO();
	public abstract DAO<Machine> getMachineDAO();
	public abstract DAO<Maintenance> getMaintenanceDAO();
	public abstract DAO<MaintenanceManager> getMaintenanceManagerDAO();
	public abstract DAO<MaintenanceWorker> getMaintenanceWorkerDAO();
	public abstract DAO<PurchaseEmployee> getPurchaseEmployeeDAO();
	public abstract DAO<Site> getSiteDAO();
	public abstract DAO<User> getUserDAO();
	public abstract DAO<Zone> getZoneDAO();
	
	public static AbstractDAOFactory getFactory(int type) {
		switch(type){
		case DAO_FACTORY:
			return new DAOFactory();
		default:
			return null;
		}
	}
}

