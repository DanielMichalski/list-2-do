package pl.sggw.database;

import java.io.File;
import org.junit.runners.model.InitializationError;
import com.xtremelabs.robolectric.RobolectricConfig;
import pl.sggw.util.FileNotExistException;

public class SQLiteTestRunner extends com.xtremelabs.robolectric.RobolectricTestRunner {
	private static final String PROJECT_PATH = "/home/mattie/workspace/list2do/list-2-do";

	private static final String DB_PATH_FILE = PROJECT_PATH + "/assets/testdb";

	public SQLiteTestRunner(Class testClass) throws InitializationError {
		super(testClass, new RobolectricConfig(new File(".")), new SQLiteMap(DB_PATH_FILE));

		File dbFile = new File(DB_PATH_FILE);
		if(!dbFile.exists()){
			throw new FileNotExistException("Nie poprawna sciezka do pliku z baza: .../assets/testdb.db");
		}
	}
}