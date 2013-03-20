package com.example.ufmealmeter;

import java.util.ArrayList;
import java.util.Date;

public class FoodHistoryItem {
	Date logDate;
	String totalCalories;
	String totalPrice;
	ArrayList<String> foodItemNames = new ArrayList<String>();
	ArrayList<Float> calories = new ArrayList<Float>();

	public FoodHistoryItem(Date logDate, String totalCalories, String totalPrice, ArrayList<String> foodItemNames, ArrayList<Float> calories) {
		super();
		this.logDate = logDate;
		this.totalCalories = totalCalories;
		this.totalPrice = totalPrice;
		this.foodItemNames = foodItemNames;
		this.calories = calories;
	}

}
