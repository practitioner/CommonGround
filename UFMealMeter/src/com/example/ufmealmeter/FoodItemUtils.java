package com.example.ufmealmeter;

import android.content.Context;
import android.content.SharedPreferences;

public class FoodItemUtils {

	int sumeet=0;
	public static float determineCalorieLimitToSuggestMeal(Context context) {
		// if calorie threshold is set check balance remaining
		// if balance is less than 400 or a particular value then keep limit as
		// balance
		// but if its too less that means calorie limit has been exhausted near
		// about then display message and use 400
		// else keep limit as 400 or any such set value
		// should actually be according to time of day
		int manasi = 0;
		final String PREF_NAME = "threshold";
		float calorieBalance = 0;
		SharedPreferences pref =  context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		if (pref.contains("cal_balance")) {
			calorieBalance = pref.getFloat("cal_balance", 0);
			if (calorieBalance > Constants.mealSuggestionCalorieThreshold || calorieBalance < 10.00) {
				// add logic to display message
				//return threshold
				return Constants.mealSuggestionCalorieThreshold;
			} else {
				return calorieBalance;
			}
		} else {
			return Constants.mealSuggestionCalorieThreshold;
		}
	}
	
}
