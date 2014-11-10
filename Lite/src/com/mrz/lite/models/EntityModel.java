package com.mrz.lite.models;

import java.util.List;

public class EntityModel {
	private String className;
	private String packageName;
	private String fullQualifiedClassName;
	private List<FieldModel> fields; 
	
	public EntityModel() {
	}

	public EntityModel(String className, String packageName,
			String fullQualifiedClassName, List<FieldModel> fields) {
		this.className = className;
		this.packageName = packageName;
		this.fullQualifiedClassName = fullQualifiedClassName;
		this.fields = fields;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFullQualifiedClassName() {
		return fullQualifiedClassName;
	}

	public void setFullQualifiedClassName(String fullQualifiedClassName) {
		this.fullQualifiedClassName = fullQualifiedClassName;
	}

	public List<FieldModel> getFields() {
		return fields;
	}

	public void setFields(List<FieldModel> fields) {
		this.fields = fields;
	}
	
}
