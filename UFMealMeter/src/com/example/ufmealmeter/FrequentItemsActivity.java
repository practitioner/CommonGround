package com.example.ufmealmeter;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class FrequentItemsActivity extends ListActivity {

	SharedPreferences.Editor editor;
	public final String PREF_NAME = "frequentItems";
	public final String frequentItemPreference="frequentItemsStringSet";
	FoodItemAdapter adapter;
	int totalCalories = 0;
	float totalCarbs = 0;
	float totalFat = 0;
	ArrayList<String> indvCal = new ArrayList<String>();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setupActionBar();
		setContentView(R.layout.activity_frequent_items);
		
		final List<FoodItem> foodItemsOfThisRestaurant = readFrequentItemsFromSharedPreference();
		adapter = new FoodItemAdapter(this, foodItemsOfThisRestaurant);
		setListAdapter(adapter);
		final ListView foodItemsListView=(ListView)findViewById(android.R.id.list);
	}

	private List<FoodItem> readFrequentItemsFromSharedPreference() {
		// TODO Auto-generated method stub
		editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
		SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		if(pref.contains(frequentItemPreference)){
			
		}
		
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.frequent_items, menu);
		return true;
	}
	
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_go:
			ArrayList<String> selFood = getSummary();
			if (null != selFood && selFood.size() > 0) {
				Intent intent = new Intent(FrequentItemsActivity.this, CaloriesSummaryActivity.class);
				intent.putStringArrayListExtra("foodNames", selFood);
				intent.putExtra("totalCal", totalCalories);
				intent.putExtra("totalCarbs", totalCarbs);
				intent.putExtra("totalFat", totalFat);
				intent.putExtra("calBalance", getCalorieBalance());
				intent.putExtra("budgetBalance", getBudgetBalance());
				intent.putStringArrayListExtra("indvCalorie", indvCal);
				intent.putExtra("position", 1);//check
				startActivity(intent);
			} else {
				Toast.makeText(FrequentItemsActivity.this, "You didnt select any Food Item!", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private ArrayList<String> getSummary() {
		ArrayList<String> selFoodString = new ArrayList<String>();
		for (FoodItem fd : adapter.getSelectedFoodItemList()) {
			selFoodString.add(fd.foodName);
			totalCalories = totalCalories + fd.calories;
			totalCarbs = totalCarbs + fd.totalCarbs;
			totalFat = totalFat + fd.totalFat;
			indvCal.add(String.valueOf(fd.calories));
		}

		return selFoodString;
	}

	private float getCalorieBalance() {
		float balance_cal = 0;
		SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		if (pref.contains("cal_balance")) {
			balance_cal = pref.getFloat("cal_balance", 0) - totalCalories;
		} else {
			balance_cal = (float) 0 - totalCalories;
		}
		return balance_cal;
	}

	private float getBudgetBalance() {
		float balance_budget = 0;
		SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		if (pref.contains("budget_balance")) {
			balance_budget = pref.getFloat("budget_balance", 0);
		} else {
			balance_budget = 0;
		}
		return balance_budget;
	}


}
