package com.mrz.lite.processors;

import java.io.IOException;
import java.lang.reflect.Field;
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
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.mrz.lite.annotations.LiteEntity;
import com.mrz.lite.generators.DefaultGenerator;
import com.mrz.lite.generators.Generator;
import com.mrz.lite.models.EntityModel;
import com.mrz.lite.models.FieldModel;

@SupportedAnnotationTypes({"com.mrz.lite.annotations.LiteEntity"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LiteProcessor extends AbstractProcessor {
	private EntityModel entityModel;
	
	public LiteProcessor() {
		super();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(LiteEntity.class);
		for (Element e : elements) {
			if (e.getKind() == ElementKind.CLASS) {
				entityModel = new EntityModel();
				TypeElement classElement = (TypeElement) e;
				PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
				
				entityModel.setPackageName(packageElement.getQualifiedName().toString());
				entityModel.setFullQualifiedClassName(classElement.getQualifiedName().toString());
				entityModel.setClassName(classElement.getSimpleName().toString());
				
				Class<?> clazz = null;
				try {
					clazz = Class.forName(entityModel.getClassName());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				List<FieldModel> fields = new LinkedList<FieldModel>();
				for (Field field : clazz.getDeclaredFields()) {
					FieldModel fieldModel = new FieldModel();
					fieldModel.setName(field.getName());
					fieldModel.setType(field.getType());
					fields.add(fieldModel);
				}
				entityModel.setFields(fields);
			}
		}
		JavaFileObject jfo = null;
		try {
			jfo = processingEnv.getFiler().createSourceFile(entityModel.getFullQualifiedClassName()+"Contract");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Generator generator = new DefaultGenerator();
		generator.generate(jfo, entityModel);
		return true;
	}

}
