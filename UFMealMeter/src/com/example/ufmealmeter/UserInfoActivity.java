package com.example.ufmealmeter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class UserInfoActivity extends Activity {

	SharedPreferences.Editor editor; 
	public final String PREF_NAME = "threshold";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
		setupActionBar();
		loadUserThreshold();
		
		
		switchToggleLogic();
		
		setActionOnSaveButton();
		setActionOnCancelButton();

	}

	private void setActionOnCancelButton() {
		Button cancelCalorie = (Button)findViewById(R.id.cancel_calorie);
		cancelCalorie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideCalButtons();
			}
		});
		
		Button cancelBudget = (Button)findViewById(R.id.cancel_budget);
		cancelBudget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideBudgetButtons();
			}
		});
		
	}

	private void setActionOnSaveButton() {
		Button ok_cal = (Button)findViewById(R.id.ok_calorie);
		ok_cal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveCalThreshold();
			}
		});
		
		Button ok_budget = (Button)findViewById(R.id.ok_budget);
		ok_budget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveBudgetThreshold();
			}
		});
		
		
	}

	private void loadUserThreshold() {
		SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		EditText cal_thres = (EditText) findViewById(R.id.calories_per_day);
		if(pref.contains("cal_threshold")){
			cal_thres.setText(String.valueOf(pref.getFloat("cal_threshold", 0))); 
		}else{
			cal_thres.setText(String.valueOf(0)); 
		}
		
		EditText budget_thres = (EditText) findViewById(R.id.budget_per_day);
		if(pref.contains("budget_threshold")){
			budget_thres.setText(String.valueOf(pref.getFloat("budget_threshold", 0))); 
		}else{
			budget_thres.setText(String.valueOf(0)); 
		}
		
		
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

		EditText cal_thres = (EditText) findViewById(R.id.calories_per_day);
		editor.putFloat("cal_threshold",
				Float.parseFloat(cal_thres.getText().toString()));
		editor.commit();
		editor.putFloat("cal_balance",
				Float.parseFloat(cal_thres.getText().toString()));
		editor.commit();
		Toast.makeText(UserInfoActivity.this, "Calorie Threshold saved!",
				Toast.LENGTH_SHORT).show();
		
		hideCalButtons();
	}
	
	private void hideCalButtons() {
		findViewById(R.id.cal_threshold_ll_ll).setVisibility(
				View.INVISIBLE);
	}
	
	private void hideBudgetButtons() {
		findViewById(R.id.budget_threshold_ll_ll).setVisibility(
				View.INVISIBLE);
	}
	

	private void navigateToPreviousScreen() {
		Intent intent = new Intent(UserInfoActivity.this, RestaurantActivity.class); 
		startActivity(intent);
	}

	private void saveBudgetThreshold() {
	//	SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		EditText budget_thres = (EditText) findViewById(R.id.budget_per_day);
		editor.putFloat("budget_threshold",
				Float.parseFloat(budget_thres.getText().toString()));
		editor.commit();
		editor.putFloat("budget_balance",
				Float.parseFloat(budget_thres.getText().toString()));
		editor.commit();
		Toast.makeText(UserInfoActivity.this, "Budget Threshold saved!",
				Toast.LENGTH_SHORT).show();
		
		hideBudgetButtons();
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
							findViewById(R.id.cal_threshold_ll_ll).setVisibility(
									View.VISIBLE);
						} else {
							findViewById(R.id.cal_threshold_ll).setVisibility(
									View.INVISIBLE);
							findViewById(R.id.cal_threshold_ll_ll).setVisibility(
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
