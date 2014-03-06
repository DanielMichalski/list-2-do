package pl.sggw.util;

import android.app.Application;
import android.content.Context;

/**
 * @author Lukasz Strzelecki
 * @date 10.02.12
 */
public abstract class AbstractApp extends Application {

	protected static Context applicationContext;

	public static Context getCtx() {
		return applicationContext;
	}

	public static String getStringByResId(int stringResId) {
		return applicationContext.getString(stringResId);
	}

	public static boolean getBooleanByResId(int booleanResId) {
		return applicationContext.getResources().getBoolean(booleanResId);
	}

	public abstract void synchronizedOnCreateOperations();

	@Override
	public void onCreate() {
		super.onCreate();
		synchronized (this) {
			if (applicationContext == null) {
				applicationContext = getApplicationContext();
				synchronizedOnCreateOperations();
			}
		}
	}
}

