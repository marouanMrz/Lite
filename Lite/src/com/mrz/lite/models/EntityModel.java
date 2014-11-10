package com.mrz.lite.models;

import java.util.List;

public class EntityModel {
	private String className;
	private String packageName;
	private String fullQualifiedClassName;
	private List<FieldModel> members; 
	
	public EntityModel() {
	}

	public EntityModel(String className, String packageName,
			String fullQualifiedClassName, List<FieldModel> members) {
		this.className = className;
		this.packageName = packageName;
		this.fullQualifiedClassName = fullQualifiedClassName;
		this.members = members;
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

	public List<FieldModel> getMembers() {
		return members;
	}

	public void setMembers(List<FieldModel> members) {
		this.members = members;
	}
	
}
