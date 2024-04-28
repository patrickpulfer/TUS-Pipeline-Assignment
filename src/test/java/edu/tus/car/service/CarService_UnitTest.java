package edu.tus.car.service;

import edu.tus.car.dao.CarRepository;
import edu.tus.car.model.Car;

import edu.tus.car.errors.ErrorMessages;
import edu.tus.car.errors.ErrorValidation;
import edu.tus.car.exception.CarNotFoundException;
import edu.tus.car.exception.CarValidationException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import edu.tus.car.dao.CarRepository;
import edu.tus.car.errors.ErrorValidation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class CarService_UnitTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ErrorValidation errorValidation;

    private Car car;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        car = new Car();
        car.setId(1L);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2020);
        car.setColor("Blue");
    }


    @Test
    public void testGetAllCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(car));
        List<Car> result = carService.getAllCars();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
    }


    @Test
    public void testGetCarByIdFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Optional<Car> result = carService.getCarById(1L);
        assertTrue(result.isPresent());
        assertEquals(car, result.get());
    }

    @Test
    public void testGetCarByIdNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Car> result = carService.getCarById(1L);
        assertFalse(result.isPresent());
    }


    @Test
    public void testCreateCarValid() throws CarValidationException {
        when(carRepository.save(car)).thenReturn(car);
        when(errorValidation.checkMakeAndModelNotAllowed(any(Car.class))).thenReturn(false);
        when(errorValidation.yearNotOK(any(Car.class))).thenReturn(false);
        when(errorValidation.colorNotOK(any(Car.class))).thenReturn(false);

        Car result = carService.createCar(car);
        assertNotNull(result);
        assertEquals(car, result);
    }

    @Test(expected = CarValidationException.class)
    public void testCreateCarInvalidMakeModel() throws CarValidationException {
        when(errorValidation.checkMakeAndModelNotAllowed(any(Car.class))).thenReturn(true);
        carService.createCar(car);
    }

    @Test(expected = CarValidationException.class)
    public void testCreateCarInvalidYear() throws CarValidationException {
        when(errorValidation.checkMakeAndModelNotAllowed(any(Car.class))).thenReturn(false);
        when(errorValidation.yearNotOK(any(Car.class))).thenReturn(true);
        carService.createCar(car);
    }

    @Test(expected = CarValidationException.class)
    public void testCreateCarInvalidColor() throws CarValidationException {
        when(errorValidation.checkMakeAndModelNotAllowed(any(Car.class))).thenReturn(false);
        when(errorValidation.yearNotOK(any(Car.class))).thenReturn(false);
        when(errorValidation.colorNotOK(any(Car.class))).thenReturn(true);
        carService.createCar(car);
    }


    @Test
    public void testDeleteCarExisting() throws CarNotFoundException {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        doNothing().when(carRepository).delete(any(Car.class));
        carService.deleteCar(1L);
        verify(carRepository, times(1)).delete(car);
    }

    @Test(expected = CarNotFoundException.class)
    public void testDeleteCarNotExisting() throws CarNotFoundException {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());
        carService.deleteCar(1L);
    }


}
