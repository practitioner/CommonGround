package com.example.ufmealmeter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ActionsAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final List<String> actions;

	public ActionsAdapter(Context context, List<String> actions) {
		super(context, R.layout.activity_indv_actions, actions);
		this.context = context;
		this.actions = actions;
	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String action_name = actions.get(position);

		View tweetView = inflater.inflate(R.layout.activity_indv_actions, parent, false);
		TextView textView = (TextView) tweetView.findViewById(R.id.action_name);
		textView.setText(action_name.toString());

		return tweetView;

	}

}
