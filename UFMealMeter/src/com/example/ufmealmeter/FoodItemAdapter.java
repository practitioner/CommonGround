package com.example.ufmealmeter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {

	private final Context context;
	private final List<FoodItem> foodItemList;
	private final List<Boolean> foodItemSelected;

	public FoodItemAdapter(Context context, List<FoodItem> itemsList) {
		super(context, R.layout.fooditem, itemsList);
		this.context = context;
		this.foodItemList = itemsList;
		this.foodItemSelected = new ArrayList<Boolean>();
		for (int i = 0; i < this.getCount(); i++) {
			this.foodItemSelected.add(i, false);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FoodItem foodItem = foodItemList.get(position);

		View tweetView = inflater.inflate(R.layout.fooditem, parent, false);
		TextView textView = (TextView) tweetView.findViewById(R.id.text);
		textView.setText(foodItem.toString());

		final CheckBox cb = (CheckBox) tweetView
				.findViewById(R.id.selectfooditem);
		cb.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (cb.isChecked()) {
					foodItemSelected.set(position, true);
					// do some operations here
				} else if (!cb.isChecked()) {
					foodItemSelected.set(position, false);
					// do some operations here
				}
			}
		});
		
		cb.setChecked(foodItemSelected.get(position));
		return tweetView;

	}

}
