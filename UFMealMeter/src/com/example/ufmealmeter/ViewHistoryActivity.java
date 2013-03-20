package com.example.ufmealmeter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ViewHistoryActivity extends Activity {
	public final String userHistory = "history.txt";
	ArrayList<FoodHistoryItem> items = new ArrayList<FoodHistoryItem>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_history);
		setupActionBar();

		loadHistory();

	}

	private void loadHistory() {
		FoodHistoryAdapter adapter = new FoodHistoryAdapter(getApplicationContext(), readFoodHistoryItems());
		ListView action_list = (ListView) findViewById(R.id.history_item_list);
		action_list.setAdapter(adapter);
	}

	@SuppressWarnings("finally")
	private List<FoodHistoryItem> readFoodHistoryItems() {
		FoodHistoryItem fd;
		Date logDate = null;
		String totalCalories = null;
		String totalPrice = null;
		
		/*
		 * ArrayList<String> foodItemNames = new ArrayList<String>();
		 * ArrayList<Float> calories = new ArrayList<Float>();
		 */
		InputStream inputStream = null;
		try {
			inputStream = openFileInput(userHistory);
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String line = "";
				int count = 0;
				while ((line = bufferedReader.readLine()) != null) {
					if(count > 0){
						// processing
						count++;
						if(isEndOfRecord(line)){
							fd = new FoodHistoryItem(logDate, totalCalories, totalPrice, null, null);
							items.add(fd);
							count = 0;
							line = line.substring(1);
						}
					}
					if (count == 0 && !"".equalsIgnoreCase(line) && !isEndOfRecord(line)) {
					StringTokenizer tokens = new StringTokenizer(line, ",");
					
						StringBuilder date = new StringBuilder();
						if (tokens.hasMoreTokens()) {
							date.append((String) tokens.nextElement()).append((String) tokens.nextElement());
							logDate = getDate(date.toString().trim());
						}
						if (tokens.hasMoreTokens()) {
							totalCalories = (String) tokens.nextElement();
						}
						if (tokens.hasMoreTokens()) {
							totalPrice = (String) tokens.nextElement();
						}
						count++;
					}
					
					
					
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return items;
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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_history, menu);
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean isEndOfRecord(String receiveString) {
		return ('*' == receiveString.charAt(0));
	}

	@SuppressLint("SimpleDateFormat")
	private Date getDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
		try {
			Date convertedDate = dateFormat.parse(dateString);
			return convertedDate;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
