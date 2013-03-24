package com.example.ufmealmeter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.R;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {

	private final Context context;
	private final List<FoodItem> foodItemList;
	private final SparseBooleanArray foodItemSelected;

	public FoodItemAdapter(Context context, List<FoodItem> itemsList) {
		super(context, com.example.ufmealmeter.R.layout.activity_indv_fooditem, itemsList);
		this.context = context;
		this.foodItemList = itemsList;
		this.foodItemSelected = new SparseBooleanArray();
		for (int i = 0; i < this.getCount(); i++) {
			this.foodItemSelected.put(i, false);
		}
	}

	public List<FoodItem> getSelectedFoodItemList() {
		List<FoodItem> selfoodItemList = new ArrayList<FoodItem>();
		int i = 0;
		while (i < foodItemList.size()) {
			if (this.foodItemSelected.get(i)) {
				selfoodItemList.add(foodItemList.get(i));
			}
			i++;
		}
		return selfoodItemList;
	}

	public SparseBooleanArray getFoodItemSelected() {
		return foodItemSelected;
	}

	public List<FoodItem> getFoodItemList() {
		return foodItemList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FoodItem foodItem = foodItemList.get(position);

		View tweetView = inflater.inflate(com.example.ufmealmeter.R.layout.activity_indv_fooditem, parent, false);
		TextView textView = (TextView) tweetView.findViewById(com.example.ufmealmeter.R.id.text);
		final CheckBox cb = (CheckBox) tweetView.findViewById(com.example.ufmealmeter.R.id.selectfooditem);
		textView.setText(foodItem.toString());
		if(foodItem.isSuggestible){
			textView.setBackgroundResource(R.color.holo_blue_light);
			cb.setBackgroundResource(R.color.holo_green_light);
		}
		

		
		cb.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (cb.isChecked()) {
					foodItemSelected.put(position, true);
					// do some operations here
				} else if (!cb.isChecked()) {
					foodItemSelected.put(position, false);
					// do some operations here
				}
			}
		});

		cb.setChecked(foodItemSelected.get(position));
		return tweetView;

	}

	public boolean changeOrderForSuggestAMeal() {
		
		boolean reorderSuccessful=false;
		List<Integer> indexesOfSuggestibleFoodItems = new ArrayList<Integer>();

		float calorieLimitUsedForSuggestion=FoodItemUtils.determineCalorieLimitToSuggestMeal(context);
		float minimumCalorieCountOfAnyProduct=1000;
		for (int i = 0; i < foodItemList.size(); i++) {
			FoodItem foodItem = (FoodItem) foodItemList.get(i);
			foodItem.setSuggestible(false);
			float calorieCount=foodItem.getCalories();
			
			//for the case where all food items are above the calorie limit
			if(calorieCount<minimumCalorieCountOfAnyProduct){
				minimumCalorieCountOfAnyProduct=calorieCount;
			}
			if (calorieCount < calorieLimitUsedForSuggestion ) {
				indexesOfSuggestibleFoodItems.add(i);
			}
		}

		if(calorieLimitUsedForSuggestion<minimumCalorieCountOfAnyProduct){
			return reorderSuccessful;
		}
		int low = 0;
		int high = indexesOfSuggestibleFoodItems.size();

		Random randomNumber = new Random();
		List<FoodItem> foodItemsToSuggest = new ArrayList<FoodItem>();

		List<Integer> usedRandomNumbers=new ArrayList<Integer>();
		for (int i = 0; i < Constants.numberOfsuggestedFoodItems; i++) {
			
			int randomNumberForSelectingFoodItems = randomNumber.nextInt(high - low) + low;
			
			//should not have same random numbers
			if(!usedRandomNumbers.contains(randomNumberForSelectingFoodItems)){
				usedRandomNumbers.add(randomNumberForSelectingFoodItems);
				int indexOfItemToRetreive = indexesOfSuggestibleFoodItems.get(randomNumberForSelectingFoodItems);
				FoodItem foodItem = (FoodItem) foodItemList.get(indexOfItemToRetreive);
				foodItemsToSuggest.add(foodItem);
			}
			
		}

		// remove from list
		for (int i = 0; i < foodItemsToSuggest.size(); i++) {
			foodItemList.remove(foodItemsToSuggest.get(i));
		}

		// put back at front of list
		for (int i = 0; i < foodItemsToSuggest.size(); i++) {
			FoodItem foodItem=foodItemsToSuggest.get(i);
			foodItem.setSuggestible(true);
			foodItemList.add(0, foodItem);
		}
		
		//notify adapter
		this.notifyDataSetChanged();
		reorderSuccessful=true;
		return reorderSuccessful;
	}
	
	public void stopMealSuggestionAndClearBackgroundColor(){
		for(int i=0;i<Constants.numberOfsuggestedFoodItems;i++){
			foodItemList.get(i).setSuggestible(false);
		}
		this.notifyDataSetChanged();
		
	}
}
