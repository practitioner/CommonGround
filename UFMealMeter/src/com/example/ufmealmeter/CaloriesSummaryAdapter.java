package com.example.ufmealmeter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CaloriesSummaryAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final List<String> summary;

	public CaloriesSummaryAdapter(Context context, List<String> itemsList) {
		super(context, R.layout.activity_indv_calories_summary, itemsList);
		this.context = context;
		this.summary = itemsList;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String summaryText = summary.get(position);

		View tweetView = inflater.inflate(R.layout.activity_indv_calories_summary, parent, false);
		TextView textView = (TextView) tweetView.findViewById(R.id.summary_text);
		textView.setText(summaryText);
		return tweetView;

	}

}
