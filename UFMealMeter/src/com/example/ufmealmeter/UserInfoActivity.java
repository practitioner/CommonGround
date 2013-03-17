package com.example.ufmealmeter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class UserInfoActivity extends Activity {

	Float prevCalThreshold;
	Float prevBudgetThreshold;
	Float currCalThreshold;
	Float currBudgetThreshold;
	public static final String PREFS_NAME = "MyThresholdFile";
	public static final String budget_threshold = "budget_threshold";
	public static final String cal_threshold = "cal_threshold";
	SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME,
			MODE_PRIVATE).edit();
	SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	EditText cal_thres = (EditText) findViewById(R.id.calories_per_day);
	EditText budget_thres = (EditText) findViewById(R.id.budget_per_day);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		setupActionBar();
		/*loadOldThresholds();*/

		switchToggleLogic();
/*
		okButtonLogic();
		cancelButtonLogic();*/

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

			/*
			 * case R.id.menu_save_user_threshold: saveUserThresholds(); Intent
			 * intent = new Intent(UserInfoActivity.this,
			 * RestaurantActivity.class); startActivity(intent); return true;
			 */
		}

		return super.onOptionsItemSelected(item);
	}

	private void saveCalThreshold() {
		String temp = cal_thres.getText().toString();
		if (null != temp) {
			editor.putFloat("cal_threshold", Float.parseFloat(temp));
			editor.commit();
			Toast.makeText(UserInfoActivity.this, "Calorie Threshold saved!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(UserInfoActivity.this, "Invalid Calorie Threshold!",
					Toast.LENGTH_SHORT).show();
		}

	}

	private void saveBudgetThreshold() {
		String temp = budget_thres.getText().toString();
		if (null != temp) {
			editor.putFloat("budget_threshold", Float.parseFloat(temp));
			editor.commit();
			Toast.makeText(UserInfoActivity.this, "Budget Threshold saved!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(UserInfoActivity.this, "Invalid Budget Threshold!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void loadOldThresholds() {
		if (pref.contains(cal_threshold)) {
			prevCalThreshold = pref.getFloat(cal_threshold, (Float) null);
			cal_thres.setText(prevCalThreshold.toString());
		}
		if (pref.contains(budget_threshold)) {
			prevBudgetThreshold = pref.getFloat(budget_threshold, (Float) null);
			budget_thres.setText(prevBudgetThreshold.toString());
		}
	}

	private void cancelButtonLogic() {
		Button cancelCalButton = (Button) findViewById(R.id.calories_per_day_cancel);
		cancelCalButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelCalThreshold();
			}

		});

		Button cancelBudgetButton = (Button) findViewById(R.id.budget_per_day_cancel);
		cancelBudgetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelBudgetThreshold();
			}

		});
	}

	private void okButtonLogic() {
		Button okCalButton = (Button) findViewById(R.id.calories_per_day_save);
		okCalButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveCalThreshold();
			}
		});

		Button okBudgetButton = (Button) findViewById(R.id.budget_per_day_save);
		okBudgetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveBudgetThreshold();
			}
		});
	}

	private void cancelCalThreshold() {
		Toast.makeText(UserInfoActivity.this,
				"Continuing with old Calorie Threshold, if any!",
				Toast.LENGTH_SHORT).show();
	}

	private void cancelBudgetThreshold() {
		Toast.makeText(UserInfoActivity.this,
				"Continuing with old Budget Threshold, if any!",
				Toast.LENGTH_SHORT).show();
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
							findViewById(R.id.cal_threshold_ll_ll)
									.setVisibility(View.VISIBLE);
						} else {
							findViewById(R.id.cal_threshold_ll).setVisibility(
									View.INVISIBLE);
							findViewById(R.id.cal_threshold_ll_ll)
									.setVisibility(View.INVISIBLE);
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
							findViewById(R.id.budget_threshold_ll_ll)
									.setVisibility(View.VISIBLE);
						} else {
							findViewById(R.id.budget_threshold_ll)
									.setVisibility(View.INVISIBLE);
							findViewById(R.id.budget_threshold_ll_ll)
									.setVisibility(View.INVISIBLE);
						}
					}
				});

	}

}
