package com.example.ufmealmeter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class UserInfoActivity extends Activity {

	SharedPreferences.Editor editor;
	public final String PREF_NAME = "threshold";
	EditText cal_thres=null;
	EditText budget_thres=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
		cal_thres = (EditText) findViewById(R.id.calories_per_day);
		
		cal_thres.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				findViewById(R.id.cal_threshold_ll_ll).setVisibility(View.VISIBLE);
			}
		});
		budget_thres = (EditText) findViewById(R.id.budget_per_day);
		budget_thres.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				findViewById(R.id.budget_threshold_ll).setVisibility(View.INVISIBLE);
			}
		});
		
		setupActionBar();
		loadUserThreshold();

		switchToggleLogic();

		setActionOnSaveButton();
		setActionOnCancelButton();

	}

	private void setActionOnCancelButton() {
		Button cancelCalorie = (Button) findViewById(R.id.cancel_calorie);
		cancelCalorie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideCalButtons();
			}
		});

		Button cancelBudget = (Button) findViewById(R.id.cancel_budget);
		cancelBudget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideBudgetButtons();
			}
		});

	}

	private void setActionOnSaveButton() {
		Button ok_cal = (Button) findViewById(R.id.ok_calorie);
		ok_cal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveCalThreshold();
			}
		});

		Button ok_budget = (Button) findViewById(R.id.ok_budget);
		ok_budget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveBudgetThreshold();
			}
		});

	}

	private void loadUserThreshold() {
		SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		if (pref.contains("cal_threshold")) {
			cal_thres.setText(String.valueOf(pref.getFloat("cal_threshold", 0)));
		} else {
			cal_thres.setText(String.valueOf(0));
		}

		
		if (pref.contains("budget_threshold")) {
			budget_thres.setText(String.valueOf(pref.getFloat("budget_threshold", 0)));
		} else {
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

		
		editor.putFloat("cal_threshold", Float.parseFloat(cal_thres.getText().toString()));
		editor.commit();
		editor.putFloat("cal_balance", Float.parseFloat(cal_thres.getText().toString()));
		editor.commit();
		
		//adding commit to save toggle switch status
		editor.putBoolean("cal_toggle_status_on",true);
		editor.commit();
		
		Toast.makeText(UserInfoActivity.this, "Calorie Threshold saved!", Toast.LENGTH_SHORT).show();

		hideCalButtons();
	}

	private void hideCalButtons() {
		findViewById(R.id.cal_threshold_ll_ll).setVisibility(View.INVISIBLE);
	}

	private void hideBudgetButtons() {
		findViewById(R.id.budget_threshold_ll_ll).setVisibility(View.INVISIBLE);
	}

	private void navigateToPreviousScreen() {
		Intent intent = new Intent(UserInfoActivity.this, RestaurantActivity.class);
		startActivity(intent);
	}

	private void saveBudgetThreshold() {
		// SharedPreferences.Editor editor =
		// getPreferences(MODE_PRIVATE).edit();
		
		editor.putFloat("budget_threshold", Float.parseFloat(budget_thres.getText().toString()));
		editor.commit();
		editor.putFloat("budget_balance", Float.parseFloat(budget_thres.getText().toString()));
		editor.commit();
		
		//added code for retaining status of toggle switch
		editor.putBoolean("budget_toggle_status_on",true);
		editor.commit();
		
		Toast.makeText(UserInfoActivity.this, "Budget Threshold saved!", Toast.LENGTH_SHORT).show();

		hideBudgetButtons();
	}

	private void switchToggleLogic() {
		Switch calToggle = (Switch) findViewById(R.id.cal_threshold);
		
		//added to check SharedPrefereces if toggle switch was set on by user before
		//if yes then it should be displayed as on when user returns to this activity
		SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		
		boolean cal_toggle_status_on=false;
		if(pref.contains("cal_toggle_status_on")){
			cal_toggle_status_on=pref.getBoolean("cal_toggle_status_on", false);
		} else {
			editor.putBoolean("cal_toggle_status_on", false);
		}		
		calToggle.setChecked(cal_toggle_status_on);
		
		if(cal_toggle_status_on){
			makeCalEditTextBoxVisible(true);
		} else {
			makeCalEditTextBoxInvisible();
		}
		
		//-- end logic for persisting toggle status
		
		calToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					makeCalEditTextBoxVisible(false);
				} else {
					makeCalEditTextBoxInvisible();
				}
			}
		});

		Switch budgetToggle = (Switch) findViewById(R.id.budget_threshold);
		
		//added to check SharedPrefereces if toggle switch was set on by user before
		//if yes then it should be displayed as on when user returns to this activity
		boolean budget_toggle_status_on=false;
		if(pref.contains("budget_toggle_status_on")){
			budget_toggle_status_on=pref.getBoolean("budget_toggle_status_on", false);
		} else {
			editor.putBoolean("budget_toggle_status_on", false);
		}		
		budgetToggle.setChecked(budget_toggle_status_on);
		
		if(budget_toggle_status_on){
			makeBudgetEditTextBoxVisible(true);
		} else {
			makeBudgetEditTextBoxInvisible();
		}
		//-- end logic for persisting toggle status
		
		
		budgetToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					makeBudgetEditTextBoxVisible(false);
				} else {
					makeBudgetEditTextBoxInvisible();
				}
			}
		});

	}

	public void makeCalEditTextBoxVisible(boolean firstTime){
		findViewById(R.id.cal_threshold_ll).setVisibility(View.VISIBLE);
		if(!firstTime){
			findViewById(R.id.cal_threshold_ll_ll).setVisibility(View.VISIBLE);
		}
		
	}
	public void makeBudgetEditTextBoxVisible(boolean firstTime){
		findViewById(R.id.budget_threshold_ll).setVisibility(View.VISIBLE);
		if(!firstTime){
			findViewById(R.id.budget_threshold_ll_ll).setVisibility(View.VISIBLE);
		}
		
	}
	
	public void makeCalEditTextBoxInvisible(){
		findViewById(R.id.cal_threshold_ll).setVisibility(View.INVISIBLE);
		findViewById(R.id.cal_threshold_ll_ll).setVisibility(View.INVISIBLE);
		editor.putBoolean("cal_toggle_status_on", false);
		editor.commit();
	}
	
	public void makeBudgetEditTextBoxInvisible(){
		findViewById(R.id.budget_threshold_ll).setVisibility(View.INVISIBLE);
		findViewById(R.id.budget_threshold_ll_ll).setVisibility(View.INVISIBLE);
		editor.putBoolean("budget_toggle_status_on", false);
		editor.commit();
	}
	
}
