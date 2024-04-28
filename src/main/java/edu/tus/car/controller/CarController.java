package edu.tus.car.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.tus.car.exception.CarException;
import edu.tus.car.model.Car;
import edu.tus.car.service.CarService;
import edu.tus.car.errors.ErrorMessage;

@RestController
@RequestMapping("/api/cars")
public class CarController {

	@Autowired
	CarService carService;
	
	@GetMapping
	public ResponseEntity getAllCars(){
		ArrayList<Car> cars=(ArrayList<Car>) carService.getAllCars();
		if (cars.size()==0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cars);
		}
		else {
			return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(cars);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity getCarById(@PathVariable("id") Long id){
		Optional<Car> car= carService.getCarById(id);
		if (car.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(car);
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(car);
		}
	}

	@PostMapping
	public ResponseEntity addCar(@RequestBody Car car) {
		try {
			Car savedCar = carService.createCar(car);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
		} catch (CarException e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteCar(@PathVariable("id") Long id) {
		try {
			carService.deleteCar(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
