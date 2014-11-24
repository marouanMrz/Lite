package com.mrz.lite.processors;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;

import com.mrz.lite.models.FieldModel;

public class ProcessorHelper {
	
	
	public static String generateLiteHelperFullQualifiedClassName(String fullQualifiedClassName){
		return fullQualifiedClassName.substring(0, fullQualifiedClassName.lastIndexOf("Contract"));
	}

	/**
	 * Retrieve typeElement fields to construct a list of FieldModel 
	 * @param classElement TypeElement of kind CLASS
	 * @return List of FieldModel
	 */
	public static List<FieldModel> fieldsMapper(TypeElement classElement){
		List<FieldModel> fields = new LinkedList<FieldModel>();
		List<VariableElement> variableElements = ElementFilter.fieldsIn(classElement.getEnclosedElements());
		for (VariableElement field : variableElements) {
			FieldModel fieldModel = new FieldModel();
			fieldModel.setName(field.getSimpleName().toString());
			if (field.asType().getKind() == TypeKind.DECLARED) {
				fieldModel.setType(formatFieldType(field.asType().toString()));
			} else {
				fieldModel.setType(field.asType().toString());
			}
			fields.add(fieldModel);
		}
		return fields;
	}
	
	/**
	 * Return the simple name of field type
	 * @param fieldType qualified name of type
	 * @return simple name of type
	 */
	private static String formatFieldType(String fieldType) {
		String[] type = fieldType.split("\\.");
		return type[type.length - 1];
	}
	
	/**
	 * Generate a full qualified class for SQLite Management 
	 * @param fullQualifiedClassName
	 * @return Full qualified class name in a separated package
	 */
	public static String generateFullQualifiedClassName(String fullQualifiedClassName){
		String[] fqcn = fullQualifiedClassName.split("\\.");
		StringBuffer packageName = new StringBuffer();
		if (fqcn.length == 2) {
			packageName.append("db");
			packageName.append("." + fqcn[fqcn.length - 1]);
		}
		else {
			for (int i = 0; i < fqcn.length - 2; i++) {
				if(i == 0) packageName.append(fqcn[i]);
				else packageName.append("."+fqcn[i]);
			}
			packageName.append(".db");
			packageName.append("." + fqcn[fqcn.length - 1]);
		} 
		return packageName.toString();
	}
	
	/**
	 * Generate a separated package for SQLite Management 
	 * @param fullQualifiedClassName
	 * @return Package for SQLite Management
	 */
	public static String generateDbPackage(String fullQualifiedClassName) {
		return fullQualifiedClassName.substring(0, fullQualifiedClassName.lastIndexOf("."));
	}
}
