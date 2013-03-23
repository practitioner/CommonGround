package com.example.ufmealmeter;

public class FoodItem {

	String foodName;
	int calories;
	String servingSize;
	float totalFat;
	int totalCarbs;

	public FoodItem() {
	}

	public FoodItem(String name, int cal, String servSize, float totFat, int totCarbs) {
		foodName = name;
		calories = cal;
		servingSize = servSize;
		totalFat = totFat;
		totalCarbs = totCarbs;
	}
	
	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public String getServingSize() {
		return servingSize;
	}

	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
	}

	public float getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(float totalFat) {
		this.totalFat = totalFat;
	}

	public int getTotalCarbs() {
		return totalCarbs;
	}

	public void setTotalCarbs(int totalCarbs) {
		this.totalCarbs = totalCarbs;
	}


	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("  " + this.foodName);
		sb.append("\n  Serving Size: ");
		sb.append(this.servingSize);
		sb.append("\n  Calories: ");
		sb.append(this.calories);
		sb.append(" Total Fat: ");
		sb.append(this.totalFat);
		sb.append(" Total Carbs: ");
		sb.append(this.totalCarbs);
		return sb.toString();
	}

}
