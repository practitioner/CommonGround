package com.example.ufmealmeter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodHistoryAdapterFood extends ArrayAdapter<String> {

	private final Context context;
	private final List<String> foodHistoryItems;

	public FoodHistoryAdapterFood(Context context, List<String> itemsList) {
		super(context, R.layout.activity_indv_foodhistoryitem_food, itemsList);
		this.context = context;
		this.foodHistoryItems = itemsList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		String item = foodHistoryItems.get(position);

		View food_history_view = inflater.inflate(R.layout.activity_indv_foodhistoryitem_food, parent, false);

		TextView name = (TextView) food_history_view.findViewById(R.id.item);
		name.setText(item);

		return food_history_view;

	}
	
	

}
