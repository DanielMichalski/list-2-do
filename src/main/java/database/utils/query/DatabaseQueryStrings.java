package pl.sggw.database.utils.query;

import pl.sggw.database.utils.tables_headers.TaskTableHeaders;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

public class DatabaseQueryStrings {

	// Polecenia SQL do tworzenia i usuwania tabel.
	public static final String CREATE_TASK_TABLE =
			"CREATE TABLE IF NOT EXISTS " + TaskTableHeaders.TABLE_NAME +
					"(" +
					TaskTableHeaders.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					TaskTableHeaders.GOOGLE_ID + " TEXT, " +
					TaskTableHeaders.TITLE + " TEXT NOT NULL, " +
					TaskTableHeaders.NOTES + " TEXT, " +
					TaskTableHeaders.DUE_DATE + " INTEGER NOT NULL, " +
					TaskTableHeaders.ALARM_DATE + " INTEGER, " +
					TaskTableHeaders.UPDATED_DATE + " INTEGER NOT NULL, " +
					TaskTableHeaders.PRIORITY_TYPE + " TEXT NOT NULL, " +
					TaskTableHeaders.REPEAT_TYPE + " TEXT NOT NULL, " +
					TaskTableHeaders.STATUS_TYPE + " TEXT NOT NULL" +
					");";


	// Usuwanie tabel
	public static final String DROP_TASK_TABLE =
			"DROP TABLE IF EXISTS " + TaskTableHeaders.TABLE_NAME + ";";
}
