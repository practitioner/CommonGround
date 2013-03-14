package com.example.ufmealmeter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CaloriesSummaryActivity extends Activity {

	String position;
	int totalCalories = 0;
	float totalCarbs = 0;
	float totalFat = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calories_summary);
		// Show the Up button in the action bar.
		setupActionBar();

		// Select position from previous activity and bring that menu here
		position = this.getIntent().getExtras().getString("position");

		totalCalories = this.getIntent().getExtras().getInt("totalCal");
		totalCarbs = this.getIntent().getExtras().getFloat("totalCarbs");
		totalFat = this.getIntent().getExtras().getFloat("totalFat");
		ArrayList<String> selectedFoodItems = this.getIntent().getExtras()
				.getStringArrayList("foodNames");
		
		FoodNameAdapter adapter = new FoodNameAdapter(getApplicationContext(), selectedFoodItems);
		ListView food_list = (ListView) findViewById(R.id.selected_fooditem);
		food_list.setAdapter(adapter);
		
		TextView cal = (TextView) findViewById(R.id.total_calories);
		cal.setText("Total Calories : " + String.valueOf(totalCalories) + "\n");
		
		TextView carbs = (TextView) findViewById(R.id.total_carbs);
		carbs.setText("Total Carbs : " + String.valueOf(totalCarbs) + "\n");
		
		TextView fat = (TextView) findViewById(R.id.total_fat);
		fat.setText("Total Fat : " + String.valueOf(totalFat) + "\n");
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
		}
		return super.onOptionsItemSelected(item);
	}

}
