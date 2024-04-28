package edu.tus.car.errors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.tus.car.model.Car;

@Component
public class ErrorValidation {
	
	HashMap<String, String> makeModels;
	List colorsList =new ArrayList();
	
public ErrorValidation() {
	makeModels = new HashMap<String, String>();
	makeModels.put("MERCEDES","E220");
	makeModels.put("AUDI","A6");
	makeModels.put("VOLKSVAGEN","ARTEON");
	makeModels.put("BMW","320");
	makeModels.put("FERRARI", "F40");
	makeModels.put("PROSCHE", "GT4");
	
	colorsList.add("RED");
	colorsList.add("GREEN");
	colorsList.add("BLACK");
	colorsList.add("SILVER");
	
}
	public boolean checkMakeAndModelNotAllowed(Car car) {
		String model =makeModels.get(car.getMake().toUpperCase());
		return (model==null ||(!model.equals(car.getModel().toUpperCase())));
	}
	
	public boolean yearNotOK(Car car) {
		return ((car.getYear()<2020)); 
	}
	
	public boolean colorNotOK(Car car) {
		return ((!colorsList.contains(car.getColor().toUpperCase()))); 
	}

}
