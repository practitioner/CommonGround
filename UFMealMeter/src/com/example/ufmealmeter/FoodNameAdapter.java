package com.example.ufmealmeter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodNameAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final List<String> StringList;

	public FoodNameAdapter(Context context, List<String> itemsList) {
		super(context, R.layout.activity_indv_sel_fooditem, itemsList);
		this.context = context;
		this.StringList = itemsList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String String = StringList.get(position);

		View tweetView = inflater.inflate(R.layout.activity_indv_sel_fooditem, parent, false);
		TextView textView = (TextView) tweetView.findViewById(R.id.text);
		textView.setText(String.toString());

		return tweetView;

	}

}
