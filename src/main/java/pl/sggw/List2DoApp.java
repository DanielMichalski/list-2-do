package pl.sggw;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import pl.sggw.util.AbstractApp;

/**
 * @author Daniel
 * @date 03.01.13
 */

@ReportsCrashes(formKey = "dF8zYXhIU1p0TnFQLUVoaFpmeEJjMWc6MQ",
		customReportContent = {
				ReportField.APP_VERSION_CODE,
				ReportField.PHONE_MODEL,
				ReportField.ANDROID_VERSION,
				ReportField.APP_VERSION_NAME,
				ReportField.STACK_TRACE
		},
		mode = ReportingInteractionMode.TOAST,
		resToastText = R.string.toast_text_crash)

public class List2DoApp extends AbstractApp {

	private static SharedPreferences preferences;

	@Override
	public void synchronizedOnCreateOperations() {
		preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
	}

	@Override
	public void onCreate() {
		ACRA.init(this);
		super.onCreate();
	}

}
