package pl.sggw.database.adapters;

/**
 * @author Daniel Michalski
 * @date 06.11.12
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pl.sggw.database.DBHelper;

/**
 * Klasa DatabaseAdapter jest wzorcem projektowym Singletone.
 * Aby pobrać obiekt Klasy DatabaseAdapter trzeba wywoałać
 * statyczną metodę getIstance().
 */
@Singleton
public class DatabaseAdapter {
    private static final String DEBUG_TAG = DatabaseAdapter.class.getSimpleName();

	private static final String DATABASE_NAME = "task_database.db";

	private static final int DB_VERSION = 4;

    private SQLiteDatabase database;

	private SQLiteOpenHelper dbHelper;

	@Inject
    private DatabaseAdapter() {
	}

    public static DatabaseAdapter getInstance() {
        return new DatabaseAdapter();
    }

    public SQLiteDatabase open(Context context) {
        this.dbHelper = new DBHelper(context, DATABASE_NAME, DB_VERSION);
		this.database = getDatabaseBy(dbHelper);
        return database;
    }

	public SQLiteDatabase open(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
		this.database = getDatabaseBy(dbHelper);
		return database;
	}

	private SQLiteDatabase getDatabaseBy(SQLiteOpenHelper dbHelper){
		SQLiteDatabase database;
		try {
			database = dbHelper.getWritableDatabase();
		} catch (SQLException ex) {
			database = dbHelper.getReadableDatabase();
		}
		return database;
	}

    public void close() {
        dbHelper.close();
		database.close();
    }
}