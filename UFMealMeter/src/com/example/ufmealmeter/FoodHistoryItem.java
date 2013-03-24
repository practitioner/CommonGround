package com.example.ufmealmeter;

import java.util.Date;
import java.util.HashMap;

public class FoodHistoryItem {
	Date logDate;
	String totalCalories;
	String totalPrice;
	HashMap<String, String> itemCal;

	public FoodHistoryItem(Date logDate, String totalCalories, String totalPrice, HashMap<String, String> map) {
		super();
		this.logDate = logDate;
		this.totalCalories = totalCalories;
		this.totalPrice = totalPrice;
		this.itemCal = map;
	}

}
