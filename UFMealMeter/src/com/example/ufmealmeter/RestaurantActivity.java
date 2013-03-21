package com.example.ufmealmeter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * Main Launching Activity
 * 
 * @author manasi
 * 
 */
public class RestaurantActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new RestaurantAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(RestaurantActivity.this, FoodItemActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});

		ActionsAdapter adapter = new ActionsAdapter(getApplicationContext(), getActions());
		ListView action_list = (ListView) findViewById(R.id.action_list);
		action_list.setAdapter(adapter);

		action_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				System.out.println("position" + position);
				System.out.println("id : " + id);
				if (position == 0) {

					Intent intent = new Intent(RestaurantActivity.this, ViewHistoryActivity.class);
					intent.putExtra("position", position);
					startActivity(intent);
				} else if (position == 1) {

					Intent intent = new Intent(RestaurantActivity.this, UserInfoActivity.class);
					intent.putExtra("position", position);
					startActivity(intent);
				}
			}
		});

	}

	private List<String> getActions() {
		ArrayList<String> actionNames = new ArrayList<String>();
		actionNames.add(ActivityMapping.actionNames[0]);
		actionNames.add(ActivityMapping.actionNames[1]);
		actionNames.add(ActivityMapping.actionNames[2]);
		return actionNames;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
