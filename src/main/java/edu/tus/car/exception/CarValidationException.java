package edu.tus.car.exception;

public class CarValidationException extends CarException {

	private static final long serialVersionUID = 334051992916748022L;

	public CarValidationException(final String errorMessage) {
		super(errorMessage);
	}

}

