package com.mrz.lite.util;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import com.mrz.lite.annotations.LiteManyToMany;
import com.mrz.lite.annotations.LiteManyToOne;
import com.mrz.lite.annotations.LiteOneToOne;
import com.mrz.lite.generators.ContractGenerator;
import com.mrz.lite.generators.Generator;
import com.mrz.lite.models.EntityModel;
import com.mrz.lite.models.FieldModel;

public class ProcessorHelper {
	
	/**
	 * Generate a full qualified class name for Helper 
	 * @param fullQualifiedClassName
	 * @return Full qualified class name in a separated package (db package)
	 */
	public static String generateLiteHelperFullQualifiedClassName(String fullQualifiedClassName){
		return fullQualifiedClassName.substring(0, fullQualifiedClassName.lastIndexOf("Contract"));
	}

	/**
	 * Retrieve typeElement fields to construct a list of FieldModel 
	 * @param classElement TypeElement of kind CLASS
	 * @return List of FieldModel
	 */
	public static List<FieldModel> fieldsMapper(ProcessingEnvironment processingEnv, TypeElement classElement, EntityModel entityModel){
		List<FieldModel> fields = new LinkedList<FieldModel>();
		List<VariableElement> variableElements = ElementFilter.fieldsIn(classElement.getEnclosedElements());
		for (VariableElement field : variableElements) {
			FieldModel fieldModel = new FieldModel();
			fieldModel.setName(field.getSimpleName().toString());
			if (field.asType().getKind() == TypeKind.DECLARED) {
				fieldModel.setType(formatFieldType(field.asType().toString()));
				if(field.getAnnotation(LiteManyToOne.class) != null){
					fieldModel.setName("fk_"+field.getAnnotation(LiteManyToOne.class).mappedBy());
				}
				if(field.getAnnotation(LiteOneToOne.class) != null){
					fieldModel.setName("fk_"+field.getAnnotation(LiteOneToOne.class).mappedBy());
				}
				if (field.getAnnotation(LiteManyToMany.class) != null) {
					generateXReferenceContract(processingEnv, field, entityModel);
					continue;
				}
			} else {
				fieldModel.setType(field.asType().toString());
			}
			if (!fieldModel.getName().toLowerCase().equals("table_name") &&
					!fieldModel.getName().toLowerCase().equals("id") &&
					!field.asType().toString().contains("<"))
			fields.add(fieldModel);
		}
		return fields;
	}
	
	
	private static void generateXReferenceContract(ProcessingEnvironment processingEnv,
			VariableElement field, EntityModel entityModel) {
		String firstModel = entityModel.getClassName();
		String secondModel = field.getAnnotation(LiteManyToMany.class).mappedBy();
		EntityModel xRefModel = new EntityModel();
		xRefModel.setClassName(firstModel + secondModel);
		List<FieldModel> fields = new LinkedList<FieldModel>();
		FieldModel firstField = new FieldModel("fk_" + firstModel, "String");
		FieldModel secondField = new FieldModel("fk_" + secondModel, "String");
		fields.add(firstField);
		fields.add(secondField);
		xRefModel.setFields(fields);
		xRefModel.setFullQualifiedClassName(entityModel.getFullQualifiedClassName());
		xRefModel.setPackageName(entityModel.getPackageName());
		JavaFileObject jfo = null;
		try {
			jfo = processingEnv.getFiler().createSourceFile(xRefModel.getPackageName() + "." + xRefModel.getClassName() + "Contract");
			Generator generator = new ContractGenerator();
			generator.generate(jfo, xRefModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
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