package pl.sggw.widget.tabhost;

import android.content.Context;
import android.content.Intent;
import android.widget.TabHost;

/**
 * @author Daniel
 * @since 30/07/2011
 */
public class TabHostPopulator {

	private Context ctx;

	private TabHost tabHost;

	public TabHostPopulator(Context ctx, TabHost tabHost) {
		this.ctx = ctx;
		this.tabHost = tabHost;
	}

	public void newTab(int labelResId, Intent intentOnClick) {
		intentOnClick.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		String label = ctx.getString(labelResId);
		Tab tab = new Tab(ctx)
				.withLabel(label);

		TabHost.TabSpec tabSpec = tabHost.newTabSpec(label).setIndicator(tab).setContent(intentOnClick);
		tabHost.addTab(tabSpec);
	}

}
