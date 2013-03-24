package com.example.ufmealmeter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

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
	Map<Date, FoodHistoryItem> dateMap = new HashMap<Date, FoodHistoryItem>();
	String delimiter = ":";

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
		HashMap<String, String> itemCal = new HashMap<String, String>();
		String itemName = null, itemPrice = null;

		InputStream inputStream = null;
		try {
			inputStream = openFileInput(userHistory);
			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String line = "";
				int count = 0;
				while ((line = bufferedReader.readLine()) != null) {
					if (count > 0) {
						if (isEndOfRecord(line)) {
							fd = new FoodHistoryItem(logDate, totalCalories, totalPrice, itemCal);
							// items.add(fd);
							consolidateDate(logDate, fd);
							count = 0;
							line = line.substring(1);
						} else {
							// processing
							StringTokenizer tokens = new StringTokenizer(line, delimiter);
							if (tokens.hasMoreTokens()) {
								itemName = (String) tokens.nextElement();
							}
							if (tokens.hasMoreTokens()) {
								itemPrice = (String) tokens.nextElement();
							}
							if (null != itemName && null != itemPrice) {
								itemCal.put(itemName, itemPrice);
								itemName = null;
								itemPrice = null;
								count++;
							}
						}

					}
					if (count == 0 && !"".equalsIgnoreCase(line) && !isEndOfRecord(line)) {
						StringTokenizer tokens = new StringTokenizer(line, delimiter);

						if (tokens.hasMoreTokens()) {
							String date = tokens.nextElement().toString();
							logDate = getDate(date);
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

			populateItems();

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

	private void populateItems() {
		Map<Date, FoodHistoryItem> sortedDateMap = new TreeMap<Date, FoodHistoryItem>(dateMap);
		for (Date key : ((TreeMap<Date, FoodHistoryItem>) sortedDateMap).descendingKeySet()) {
			items.add(sortedDateMap.get(key));
		}
	}

	@SuppressWarnings("rawtypes")
	private void consolidateDate(Date logDate, FoodHistoryItem fd) {
		if (dateMap.containsKey(logDate)) {
			FoodHistoryItem fd_old = dateMap.get(logDate);
			int cal = Integer.parseInt(fd_old.totalCalories);
			cal += Integer.parseInt(fd.totalCalories);
			fd_old.totalCalories = String.valueOf(cal);
			float price = Float.parseFloat(fd_old.totalPrice);
			price += Float.parseFloat(fd.totalPrice);
			fd_old.totalPrice = String.valueOf(price);

			Iterator<Entry<String, String>> it = fd.itemCal.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				fd_old.itemCal.put(pairs.getKey().toString(), pairs.getValue().toString());
			}
			dateMap.put(logDate, fd_old);
		} else {
			dateMap.put(logDate, fd);
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
		try {
			Date convertedDate = dateFormat.parse(dateString);
			return convertedDate;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
