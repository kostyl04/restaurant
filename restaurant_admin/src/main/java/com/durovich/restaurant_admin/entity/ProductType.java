package com.durovich.restaurant_admin.entity;

public enum ProductType {
	Food("Основное меню"), Drink("Напитки"), Bar("Барное меню");
	private String name;

	private ProductType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ProductType get(String name) {
		if (name.equals("Основное меню"))
			return Food;
		else if (name.equals("Напитки"))
			return Drink;
		return Bar;

	}

}
