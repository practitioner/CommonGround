package com.example.ufmealmeter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.GetChars;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FoodItemActivity extends ListActivity {

	int position;
	FoodItemAdapter adapter;
	int totalCalories = 0;
	float totalCarbs = 0;
	float totalFat = 0;
	ArrayList<String> indvCal = new ArrayList<String>();
	public final String PREF_NAME = "threshold";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the Up button in the action bar.
		setupActionBar();

		// Select position from previous activity and bring that menu here
		position = this.getIntent().getExtras().getInt("position");

		final List<FoodItem> foodItemsOfThisRestaurant=readFoodItems(getRestaurantName(position));
		
		adapter = new FoodItemAdapter(this, foodItemsOfThisRestaurant);

		setTitle(RestaurantName.actual_display_name[position]);

		setListAdapter(adapter);
		
		/*//Add TextView for suggest a meal
		TextView suggestAMealTextView=(TextView)findViewById(R.id.suggestAMealTextView);
		
		//set on click listener
		suggestAMealTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//activate some text view showing rationale behind selecting food items
				
				
				//reorder list based on meal suggestion principles
				adapter.changeOrderForSuggestAMeal();
				
				
			}
		});*/
		//--on click listener ends

	}

	private String getRestaurantName(int position) {
		return RestaurantName.raw_resource_name[position];
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	public void printItems(ArrayList<FoodItem> fd) {
		System.out.println("Size: " + fd.size());
		for (int i = 0; i < fd.size(); i++) {
			System.out.println(fd.get(i).toString());

		}
	}

	/**
	 * Populates the Panda menu
	 * 
	 * @param restaurantName
	 * 
	 * @return
	 */
	private ArrayList<FoodItem> readFoodItems(String restaurantName) {
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		try {
			String foodName = null;
			int calories = 0;
			String servingSize = null;
			float totalFat = 0;
			int totalCarbs = 0;

			FoodItem fd;
			InputStream inputStream = getResources().openRawResource(getResources().getIdentifier(restaurantName, null, this.getPackageName()));
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String fileData = null;
			while ((fileData = in.readLine()) != null) {
				foodName = fileData;
				if ((fileData = in.readLine()) != null) {
					servingSize = fileData;
				}
				if ((fileData = in.readLine()) != null) {
					StringTokenizer line = new StringTokenizer(fileData);
					if (line.hasMoreTokens()) {
						calories = Integer.parseInt((String) line.nextElement());
						totalFat = Float.parseFloat((String) line.nextElement());
						totalCarbs = Integer.parseInt((String) line.nextElement());
					}
				}

				fd = new FoodItem(foodName, calories, servingSize, totalFat, totalCarbs);
				items.add(fd);

			}
			// printItems(items);
			inputStream.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return items;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.fooditem, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_go:
			Intent intent = new Intent(FoodItemActivity.this, CaloriesSummaryActivity.class);
			intent.putStringArrayListExtra("foodNames", getSummary());
			intent.putExtra("totalCal", totalCalories);
			intent.putExtra("totalCarbs", totalCarbs);
			intent.putExtra("totalFat", totalFat);
			intent.putExtra("calBalance", getCalorieBalance());
			intent.putExtra("budgetBalance", getBudgetBalance());
			intent.putStringArrayListExtra("indvCalorie", indvCal);
			intent.putExtra("position", position);
			startActivity(intent);
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
	
	/*
	public List<FoodItem> reorderFoodItemListToSuggestAMeal(List<FoodItem> foodItemList){
		
		List<Integer> indexesOfSuggestibleFoodItems=new ArrayList<Integer>();
		
		for(int i=0;i<foodItemList.size();i++){
			FoodItem foodItem=(FoodItem)foodItemList.get(i);
			if(foodItem.getCalories()<mealSuggestionCalorieThreshold){
				indexesOfSuggestibleFoodItems.add(i);
			}
		}
		
		int low=0;
		int high=indexesOfSuggestibleFoodItems.size();
		
		Random randomNumber=new Random();
		List<FoodItem> foodItemsToSuggest=new ArrayList<FoodItem>();
		
		for(int i=0;i<numberOfsuggestedFoodItems;i++){
			int randomNumberForSelectingFoodItems=randomNumber.nextInt(high-low)+low;
			int indexOfItemToRetreive=indexesOfSuggestibleFoodItems.get(randomNumberForSelectingFoodItems);
			FoodItem foodItem=(FoodItem)foodItemList.get(indexOfItemToRetreive);
			foodItemsToSuggest.add(foodItem);
		}
		
		//remove from list
		for(int i=0;i<foodItemsToSuggest.size();i++){
			foodItemList.remove(foodItemsToSuggest.get(i));
		}
			
		//put back at front of list
		for(int i=0;i<foodItemsToSuggest.size();i++){
			foodItemList.add(0, foodItemList.get(i));
		}
		
		return foodItemList;
	}
*/
}
