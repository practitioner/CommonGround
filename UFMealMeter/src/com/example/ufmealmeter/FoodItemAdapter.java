package com.example.ufmealmeter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
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
	private final SparseBooleanArray foodItemSelected;

	public FoodItemAdapter(Context context, List<FoodItem> itemsList) {
		super(context, R.layout.activity_indv_fooditem, itemsList);
		this.context = context;
		this.foodItemList = itemsList;
		this.foodItemSelected = new SparseBooleanArray();
		for (int i = 0; i < this.getCount(); i++) {
			this.foodItemSelected.put(i, false);
		}
	}

	public List<FoodItem> getSelectedFoodItemList() {
		List<FoodItem> selfoodItemList = new ArrayList<FoodItem>();
		int i = 0;
		while (i < foodItemList.size()) {
			if (this.foodItemSelected.get(i)) {
				selfoodItemList.add(foodItemList.get(i));
			}
			i++;
		}
		return selfoodItemList;
	}

	public SparseBooleanArray getFoodItemSelected() {
		return foodItemSelected;
	}

	public List<FoodItem> getFoodItemList() {
		return foodItemList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FoodItem foodItem = foodItemList.get(position);

		View tweetView = inflater.inflate(R.layout.activity_indv_fooditem, parent, false);
		TextView textView = (TextView) tweetView.findViewById(R.id.text);
		textView.setText(foodItem.toString());

		final CheckBox cb = (CheckBox) tweetView.findViewById(R.id.selectfooditem);
		cb.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (cb.isChecked()) {
					foodItemSelected.put(position, true);
					// do some operations here
				} else if (!cb.isChecked()) {
					foodItemSelected.put(position, false);
					// do some operations here
				}
			}
		});

		cb.setChecked(foodItemSelected.get(position));
		return tweetView;

	}

}
