package com.example.ufmealmeter;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ViewHistoryIndvFood extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_history_indv_food);
		// Show the Up button in the action bar.
		setupActionBar();
		
		List<String> summary_food_list = this.getIntent().getExtras().getStringArrayList("summary_food_list");
		String date = this.getIntent().getExtras().getString("summary_date");
		setTitle(date);
		
		FoodHistoryAdapterFood adapter = new FoodHistoryAdapterFood(getApplicationContext(), summary_food_list);
		ListView action_list = (ListView) findViewById(R.id.history_item_list_food);
		action_list.setAdapter(adapter);
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
		getMenuInflater().inflate(R.menu.view_history_indv_food, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
