package de.ifgi.lodum.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * A menu item consists of the offered food, the prices and food additives.
 * 
 * @author steffan
 *
 */
public class MenuItem implements RDFRepresentable{

	private String dish;
	private Price price;
	private List<FoodAdditive> foodAdditives = new ArrayList<FoodAdditive>();

	public MenuItem(String dish, Price price, List<FoodAdditive> foodAdditives) {

		this.dish = dish;
		this.price = price;
		this.foodAdditives = foodAdditives;
	}

	public String toTurtle() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("\n");
		
		if(price != null){
		stringBuilder.append(price.toTurtle());
		stringBuilder.append("\n");
		}
		
		for(FoodAdditive currentFoodAdditive : foodAdditives){
			
			stringBuilder.append(currentFoodAdditive.toTurtle());
			stringBuilder.append("\n");
		}
		stringBuilder.append("\n");
		
		stringBuilder.append("gr:name \""+dish+"\"@de.");

		stringBuilder.append("\n");
		
		return stringBuilder.toString();
	}
	
}