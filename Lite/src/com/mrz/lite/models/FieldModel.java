package com.mrz.lite.models;

public class FieldModel {
	private String name;
	private Object type;
	
	public FieldModel() {
	}
	
	public FieldModel(String name, Object type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}
	
}
