package com.example.ufmealmeter;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class CaloriesSummaryActivity extends ListActivity {
	String position;
	int totalCalories = 0;
	float totalCarbs = 0;
	float totalFat = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();

		// Select position from previous activity and bring that menu here
		position = this.getIntent().getExtras().getString("position");

		totalCalories = this.getIntent().getExtras().getInt("totalCal");
		totalCarbs = this.getIntent().getExtras().getFloat("totalCarbs");
		totalFat = this.getIntent().getExtras().getFloat("totalFat");
		ArrayList<String> selFoodString = this.getIntent().getExtras()
				.getStringArrayList("foodNames");
		
		

		CaloriesSummaryAdapter adapter = new CaloriesSummaryAdapter(this,
				selFoodString);

		setListAdapter(adapter);

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calories_summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			Intent intent = new Intent(CaloriesSummaryActivity.this,
					FoodItemActivity.class);
			intent.putExtra("position", position);
			NavUtils.navigateUpTo(this, intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
