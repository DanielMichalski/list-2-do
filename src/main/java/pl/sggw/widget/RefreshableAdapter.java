package pl.sggw.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * User: Daniel
 * Date: 25.10.12
 */

public abstract class RefreshableAdapter<T> extends BaseAdapter {

	protected LayoutInflater inflater;

	protected Context context;

	protected List<T> items;


	public RefreshableAdapter(Context context, List<T> items) {
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public T getItem(int position) {
		return items.get(position);
	}

	public int getPosition(T item) {
		return items.indexOf(item);
	}

	public void refreshItems(List<T> newItems) {
		setItems(newItems);
		notifyDataSetChanged();
	}

	private void setItems(List<T> items) {
		this.items = items;
	}
}
