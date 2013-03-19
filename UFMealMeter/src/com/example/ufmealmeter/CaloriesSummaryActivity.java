package com.example.ufmealmeter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CaloriesSummaryActivity extends Activity {

	int position;
	int totalCalories = 0;
	float totalCarbs = 0;
	float totalFat = 0;
	float remainingCalories = 0;
	float budgetBalance = 0;
	public final String PREF_NAME = "threshold";
	public final String userHistory = "history.txt";
	ArrayList<String> selectedFoodItems;
	ArrayList<String> selectedCal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calories_summary);
		// Show the Up button in the action bar.
		setupActionBar();

		// Select position from previous activity and bring that menu here
		position = this.getIntent().getExtras().getInt("position");

		selectedCal = this.getIntent().getExtras()
				.getStringArrayList("indvCalorie");
		totalCalories = this.getIntent().getExtras().getInt("totalCal");
		totalCarbs = this.getIntent().getExtras().getFloat("totalCarbs");
		totalFat = this.getIntent().getExtras().getFloat("totalFat");
		remainingCalories = this.getIntent().getExtras().getFloat("calBalance");
		budgetBalance = this.getIntent().getExtras().getFloat("budgetBalance");
		selectedFoodItems = this.getIntent().getExtras()
				.getStringArrayList("foodNames");

		FoodNameAdapter adapter = new FoodNameAdapter(getApplicationContext(),
				selectedFoodItems);
		ListView food_list = (ListView) findViewById(R.id.selected_fooditem);
		food_list.setAdapter(adapter);

		TextView cal = (TextView) findViewById(R.id.total_calories);
		cal.setText("Total Calories : " + String.valueOf(totalCalories) + "\n");

		TextView carbs = (TextView) findViewById(R.id.total_carbs);
		carbs.setText("Total Carbs : " + String.valueOf(totalCarbs) + "\n");

		TextView fat = (TextView) findViewById(R.id.total_fat);
		fat.setText("Total Fat : " + String.valueOf(totalFat) + "\n");

		TextView remaining_calories = (TextView) findViewById(R.id.remaining_calories);
		remaining_calories.setText("Remaining Calories : "
				+ String.valueOf(remainingCalories) + "\n");

		TextView remaining_budget = (TextView) findViewById(R.id.remaining_budget);
		remaining_budget.setText("Remaining Budget (Excluding this meal): "
				+ String.valueOf(budgetBalance) + "\n");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.calories_summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(CaloriesSummaryActivity.this,
					FoodItemActivity.class);
			intent.putExtra("position", position);
			NavUtils.navigateUpTo(this, intent);
			return true;
		case R.id.menu_save:
			Intent intent1 = new Intent(CaloriesSummaryActivity.this,
					RestaurantActivity.class);
			writeToFile(getWriteData());
			startActivity(intent1);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getWriteData() {
		StringBuilder temp = new StringBuilder();
		temp.append(getTodaysDate());
		temp.append(",");
		temp.append(totalCalories);
		temp.append(",");
		EditText price = (EditText) findViewById(R.id.price);
		if (null != price.getText().toString())
			temp.append(price.getText().toString());
		else
			temp.append("0");
		temp.append(",");
		temp.append(getFoodCalData());
		temp.append("*");

		return temp.toString();
	}

	private String getFoodCalData() {
		StringBuilder temp = new StringBuilder();
		int i = 0;
		while (i < selectedFoodItems.size()) {
			temp = temp.append(selectedFoodItems.get(i));
			temp = temp.append(",");
			temp = temp.append(selectedCal.get(i));
			temp = temp.append(",");
			i++;
		}
		return temp.toString();
	}

	private void writeToFile(String fileData) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput(userHistory, Context.MODE_PRIVATE));
			outputStreamWriter.write(fileData);
			outputStreamWriter.close();
		} catch (IOException e) {

		}

	}

	private String getTodaysDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
		Date now = new Date();
		return formatter.format(now);

	}

}
