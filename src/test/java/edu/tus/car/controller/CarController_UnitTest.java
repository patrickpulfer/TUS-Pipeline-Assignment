package edu.tus.car.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import edu.tus.car.exception.CarNotFoundException;
import edu.tus.car.exception.CarValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import edu.tus.car.exception.CarException;
import edu.tus.car.model.Car;
import edu.tus.car.service.CarService;
import edu.tus.car.errors.ErrorMessage;


class CarController_UnitTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public class TestCarException extends CarException {
        public TestCarException(String message) {
            super(message);
        }
    }


    @Test
    void getAllCarsShouldReturnNoContentWhenEmpty() {
        when(carService.getAllCars()).thenReturn(new ArrayList<>());
        ResponseEntity response = carController.getAllCars();
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertTrue(((ArrayList<?>) response.getBody()).isEmpty());
    }

    @Test
    void getAllCarsShouldReturnOkWhenNotEmpty() {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(new Car());
        when(carService.getAllCars()).thenReturn(cars);

        ResponseEntity response = carController.getAllCars();
        assertEquals(OK, response.getStatusCode());
        assertFalse(((ArrayList<?>) response.getBody()).isEmpty());
    }

    @Test
    void getCarByIdShouldReturnNoContentWhenEmpty() {
        when(carService.getCarById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity response = carController.getCarById(1L);
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getCarByIdShouldReturnOkWhenCarExists() {
        Car car = new Car();
        when(carService.getCarById(anyLong())).thenReturn(Optional.of(car));

        ResponseEntity response = carController.getCarById(1L);
        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Optional<?>);
        assertTrue(((Optional<?>) response.getBody()).isPresent());
        assertEquals(car, ((Optional<?>) response.getBody()).get());
    }

    @Test
    void addCarShouldReturnCreatedOnSuccess() throws CarValidationException {
        Car car = new Car();
        when(carService.createCar(any(Car.class))).thenReturn(car);

        ResponseEntity response = carController.addCar(new Car());
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(car, response.getBody());
    }

    @Test
    void deleteCarShouldReturnOkOnSuccess() throws CarNotFoundException {
        doNothing().when(carService).deleteCar(anyLong());

        ResponseEntity response = carController.deleteCar(1L);
        assertEquals(OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteCarShouldReturnNotFoundOnFailure() throws CarNotFoundException {
        doThrow(new RuntimeException("Not found")).when(carService).deleteCar(anyLong());

        ResponseEntity response = carController.deleteCar(1L);
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

}