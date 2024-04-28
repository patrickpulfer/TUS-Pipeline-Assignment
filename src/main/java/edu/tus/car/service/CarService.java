package edu.tus.car.service;

import edu.tus.car.dao.CarRepository;
import edu.tus.car.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tus.car.errors.ErrorMessages;
import edu.tus.car.errors.ErrorValidation;
import edu.tus.car.exception.CarNotFoundException;
import edu.tus.car.exception.CarValidationException;

@Service
public class CarService {
	Car car;

	@Autowired
	ErrorValidation errorValidation;

	@Autowired
	CarRepository carRepo;
	
	public List<Car> getAllCars(){
		return carRepo.findAll();
	}
	
	public Optional<Car> getCarById(Long id){
		return carRepo.findById(id);
	}



	public Car createCar(Car car) throws CarValidationException {
		this.car = car;
		if (errorValidation.checkMakeAndModelNotAllowed(car)) {
			throw new CarValidationException(ErrorMessages.INVALID_MAKE_MODEL.getMsg());
		}

		if (errorValidation.yearNotOK(car)) {
			throw new CarValidationException(ErrorMessages.INVALID_YEAR.getMsg());
		}
		if (errorValidation.colorNotOK(car)) {
			throw new CarValidationException(ErrorMessages.INVALID_COLOR.getMsg());
		}
		return carRepo.save(car);
	}
	
	public void deleteCar(Long id) throws CarNotFoundException {
		try {
			Car car = carRepo.findById(id).get();
			carRepo.delete(car);
		}catch(Exception e) {
			throw new CarNotFoundException("Car Not Found");
		}
	}

}
