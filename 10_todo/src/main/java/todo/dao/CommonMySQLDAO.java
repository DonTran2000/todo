package todo.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommonMySQLDAO {
	protected Connection connection = null;

	public Connection getConnection() throws Exception {

		try {
			if (connection == null || connection.isClosed()) {
				InitialContext initCtx = new InitialContext();

				DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/library");

				connection = ds.getConnection();

				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
			throw e;
		}

		return connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}

	public void commit() throws SQLException {
		connection.setAutoCommit(true);
	}
	
	public void rollback() throws SQLException {
		connection.rollback();
	}
}
