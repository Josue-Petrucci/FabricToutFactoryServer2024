package be.petrucci.dao;

import java.sql.Connection;

import be.petrucci.connection.FabricToutConnection;
import be.petrucci.javabeans.*;

public class DAOFactory extends AbstractDAOFactory {
	protected static final Connection conn = FabricToutConnection.getInstance();

	@Override
	public DAO<Admin> getAdminDAO() {
		return new AdminDAO(conn);
	}

	@Override
	public DAO<Factory> getFactoryDAO() {
		return new FactoryDAO(conn);
	}

	@Override
	public DAO<Machine> getMachineDAO() {
		return new MachineDAO(conn);
	}

	@Override
	public DAO<Maintenance> getMaintenanceDAO() {
		return new MaintenanceDAO(conn);
	}

	@Override
	public DAO<MaintenanceManager> getMaintenanceManagerDAO() {
		return new MaintenanceManagerDAO(conn);
	}

	@Override
	public DAO<MaintenanceWorker> getMaintenanceWorkerDAO() {
		return new MaintenanceWorkerDAO(conn);
	}

	@Override
	public DAO<PurchaseEmployee> getPurchaseEmployeeDAO() {
		return new PurchaseEmployeeDAO(conn);
	}

	@Override
	public DAO<Site> getSiteDAO() {
		return new SiteDAO(conn);
	}

	@Override
	public DAO<User> getUserDAO() {
		return new UserDAO(conn);
	}

	@Override
	public DAO<Zone> getZoneDAO() {
		return new ZoneDAO(conn);
	}
}
