package edu.tus.car.exception;

public class CarNotFoundException extends CarException {

	private static final long serialVersionUID = 334051992916748022L;

	public CarNotFoundException(final String errorMessage) {
		super(errorMessage);
	}

}

