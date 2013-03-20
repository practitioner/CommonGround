package com.example.ufmealmeter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FoodHistoryAdapter extends ArrayAdapter<FoodHistoryItem> {

	private final Context context;
	private final List<FoodHistoryItem> foodHistoryItems;

	public FoodHistoryAdapter(Context context, List<FoodHistoryItem> itemsList) {
		super(context, R.layout.activity_indv_foodhistoryitem, itemsList);
		this.context = context;
		this.foodHistoryItems = itemsList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FoodHistoryItem item = foodHistoryItems.get(position);

		View food_history_view = inflater.inflate(R.layout.activity_indv_foodhistoryitem, parent, false);

		TextView date = (TextView) food_history_view.findViewById(R.id.date);
		date.setText(getDate(item.logDate));

		TextView total_history_calories = (TextView) food_history_view.findViewById(R.id.total_history_calories);
		total_history_calories.setText(String.valueOf(item.totalCalories) + " cal");

		TextView total_history_price = (TextView) food_history_view.findViewById(R.id.total_history_price);
		total_history_price.setText("$" + String.valueOf(item.totalPrice));

		return food_history_view;

	}
	
	
	@SuppressLint("SimpleDateFormat")
	private String getDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM");
		return formatter.format(date);

	}
	

}
