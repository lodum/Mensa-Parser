package de.ifgi.lodum.domain;

/**
 * A food additive is one of the 12 possible ingredients that have to be marked. 
 * A dish can have zero or more food additives. All additives are already in the triple store.
 * Thus, you only have to add the additive number in order to link to the additive description.
 * 
 * @author steffan
 *
 */
public class FoodAdditive implements RDFRepresentable {
	
	int foodAdditiveNumber;
	
	public FoodAdditive(int foodAdditiveNumber) {
		
		this.foodAdditiveNumber = foodAdditiveNumber;
		
	}
	
	public String toTurtle() {
		
		return "gr:includes \"http://data.uni-muenster.de/context/food/food-additive"+foodAdditiveNumber+"\";";
	}

}
