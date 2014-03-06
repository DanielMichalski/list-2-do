package pl.sggw.database;

import java.sql.ResultSet;
import com.xtremelabs.robolectric.util.DatabaseConfig;

public class SQLiteMap implements DatabaseConfig.DatabaseMap {

	private String dbPathFile;

	/**
	 * This constructor will use in-memory database.
	 */
	public SQLiteMap() {

	}

	/**
	 * This constructor will use use database file
	 *
	 * @param dbFile: path to the SQLite database file
	 */
	public SQLiteMap(String dbFile) {
		dbPathFile = dbFile;
	}

	public String getDriverClassName() {
		return "org.sqlite.JDBC";
	}

	public String getConnectionString() {
		if (dbPathFile == null){
			return "jdbc:sqlite::memory:";
		} else {
			return String.format("jdbc:sqlite:%s", dbPathFile);
		}
	}

	public String getScrubSQL(String sql) {
		return sql;
	}

	public String getSelectLastInsertIdentity() {
		return "SELECT last_insert_rowid() AS id";
	}

	public int getResultSetType() {
		return ResultSet.TYPE_FORWARD_ONLY;
	}
}