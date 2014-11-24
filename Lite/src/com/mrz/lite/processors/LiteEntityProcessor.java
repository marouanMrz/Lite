package com.mrz.lite.processors;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import com.mrz.lite.annotations.LiteEntity;
import com.mrz.lite.generators.ContractGenerator;
import com.mrz.lite.generators.Generator;
import com.mrz.lite.models.EntityModel;
import com.mrz.lite.models.FieldModel;

@SupportedAnnotationTypes({"com.mrz.lite.annotations.LiteEntity"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LiteEntityProcessor extends AbstractProcessor {
	private EntityModel entityModel;
	
	public LiteEntityProcessor() {
		super();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(LiteEntity.class);
//		Messager m = this.processingEnv.getMessager();
		for (Element e : elements) {
			if (e.getKind() == ElementKind.CLASS) {
				entityModel = new EntityModel();
				TypeElement classElement = (TypeElement) e;
				entityModel.setFullQualifiedClassName(generateFullQualifiedClassName(classElement.getQualifiedName().toString()));
				entityModel.setPackageName(generateDbPackage(entityModel.getFullQualifiedClassName()));
				entityModel.setClassName(classElement.getSimpleName().toString());
				entityModel.setFields(fieldsMapper(classElement));
			}
		}
		JavaFileObject jfo = null;
		try {
			String fqClassName = entityModel.getFullQualifiedClassName();
			jfo = processingEnv.getFiler().createSourceFile(fqClassName + "Contract");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Generator generator = new ContractGenerator();
		generator.generate(jfo, entityModel);
		return true;
	}
	

	/**
	 * Return the simple name of field type
	 * @param fieldType qualified name of type
	 * @return simple name of type
	 */
	private String formatFieldType(String fieldType) {
		String[] type = fieldType.split("\\.");
		return type[type.length - 1];
	}
	
	/**
	 * Retrieve typeElement fields to construct a list of FieldModel 
	 * @param classElement TypeElement of kind CLASS
	 * @return List of FieldModel
	 */
	private List<FieldModel> fieldsMapper(TypeElement classElement){
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
	 * Generate a full qualified class for SQLite Management 
	 * @param fullQualifiedClassName
	 * @return Full qualified class name in a separated package
	 */
	private String generateFullQualifiedClassName(String fullQualifiedClassName){
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
	private String generateDbPackage(String fullQualifiedClassName) {
		return fullQualifiedClassName.substring(0, fullQualifiedClassName.lastIndexOf("."));
	}
}
