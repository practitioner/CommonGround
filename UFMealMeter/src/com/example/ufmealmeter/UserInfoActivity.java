package com.example.ufmealmeter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class UserInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		setupActionBar();

		switchToggleLogic();

	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.user_info, menu);
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
	
	
	private void switchToggleLogic() {
		Switch calToggle = (Switch) findViewById(R.id.cal_threshold);
		calToggle
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							findViewById(R.id.cal_threshold_ll).setVisibility(
									View.VISIBLE);
						} else {
							findViewById(R.id.cal_threshold_ll).setVisibility(
									View.INVISIBLE);
						}
					}
				});

		Switch budgetToggle = (Switch) findViewById(R.id.budget_threshold);
		budgetToggle
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							findViewById(R.id.budget_threshold_ll)
									.setVisibility(View.VISIBLE);
						} else {
							findViewById(R.id.budget_threshold_ll)
									.setVisibility(View.INVISIBLE);
						}
					}
				});

	}

}
