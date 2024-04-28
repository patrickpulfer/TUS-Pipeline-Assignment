package edu.tus.car.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class Car_UnitTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
    }

    @Test
    void testId() {
        Long idValue = 1L;
        car.setId(idValue);
        assertEquals(idValue, car.getId(), "Error in getId or setId");
    }

    @Test
    void testMake() {
        String make = "Toyota";
        car.setMake(make);
        assertEquals(make, car.getMake(), "Error in getMake or setMake");
    }

    @Test
    void testModel() {
        String model = "Camry";
        car.setModel(model);
        assertEquals(model, car.getModel(), "Error in getModel or setModel");
    }

    @Test
    void testYear() {
        int year = 2021;
        car.setYear(year);
        assertEquals(year, car.getYear(), "Error in getYear or setYear");
    }

    @Test
    void testColor() {
        String color = "Red";
        car.setColor(color);
        assertEquals(color, car.getColor(), "Error in getColor or setColor");
    }

    @Test
    void testInitialization() {
        assertNull(car.getId(), "Id should be null on initialization");
        assertNull(car.getMake(), "Make should be null on initialization");
        assertNull(car.getModel(), "Model should be null on initialization");
        assertEquals(0, car.getYear(), "Year should be 0 on initialization");
        assertNull(car.getColor(), "Color should be null on initialization");
    }
}