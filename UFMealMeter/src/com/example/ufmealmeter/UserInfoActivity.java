package com.example.ufmealmeter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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

/*		case R.id.menu_save_user_threshold:
			saveUserThresholds();
			Intent intent = new Intent(UserInfoActivity.this,
					RestaurantActivity.class);
			startActivity(intent);
			return true;*/
		}

		return super.onOptionsItemSelected(item);
	}

	private void saveUserThresholds() {
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		EditText cal_thres = (EditText) findViewById(R.id.calories_per_day);
		EditText budget_thres = (EditText) findViewById(R.id.budget_per_day);
		editor.putFloat("cal_threshold", Float.parseFloat(cal_thres.getText().toString()));
		editor.putFloat("budget_threshold", Float.parseFloat(budget_thres.getText().toString()));
		editor.commit();
		Toast.makeText(UserInfoActivity.this,
				"Threshold saved!", Toast.LENGTH_SHORT).show();
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
