package com.ifeng.vdn.videolog;

public enum ErrorCodeEnum {

	E208000("208000", "208000"),
	E303000("303000", "303000"),
	E301010("301010", "301010"),
	E301020("301020", "301020"),
	E301030("301030", "301030"),
	E601000("601000", "601000"),
	E602000("602000", "602000");
	
	ErrorCodeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public String getCode() {
		return code;
	}
	public String getValue() {
		return value;
	}

	private String code;
	private String value;
}
