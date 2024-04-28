package edu.tus.car.errors;

public enum ErrorMessages {
	INVALID_MAKE_MODEL("Invalid combination of make and model"),
	INVALID_YEAR("Cannot accept cars older than 2020"),
	INVALID_COLOR("Only accepting Red, Green, Black or Silver cars");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
