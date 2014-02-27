package com.kanzelmeyer.json;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LogAdapter extends BaseAdapter {

	protected Context _context;
	List<Entry> entryList;
	
	public LogAdapter(Context context, List<Entry> eList){
		_context = context;
		entryList = eList;
	}
	
	
	@Override
	public int getCount() {
		return entryList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return entryList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		if (arg1 == null) {
			LayoutInflater mInflater = (LayoutInflater) _context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = mInflater.inflate(R.layout.adapter_log, null);
		}

		TextView title = (TextView) arg1.findViewById(R.id.titleTextView);
		TextView desc = (TextView) arg1.findViewById(R.id.descriptionTextView);

		Entry entry = entryList.get(arg0);

		title.setText(entry.description);
		desc.setText(entry.date + " at " + entry.time);

		return arg1;
	}

}
