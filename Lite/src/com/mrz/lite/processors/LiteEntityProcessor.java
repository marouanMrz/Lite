package com.mrz.lite.processors;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.mrz.lite.annotations.LiteEntity;
import com.mrz.lite.generators.ContractGenerator;
import com.mrz.lite.generators.Generator;
import com.mrz.lite.models.EntityModel;
import com.mrz.lite.util.ProcessorHelper;

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
				entityModel.setFullQualifiedClassName(ProcessorHelper.generateFullQualifiedClassName(classElement.getQualifiedName().toString()));
				entityModel.setPackageName(ProcessorHelper.generateDbPackage(entityModel.getFullQualifiedClassName()));
				entityModel.setClassName(classElement.getSimpleName().toString());
				entityModel.setFields(ProcessorHelper.fieldsMapper(classElement));
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
	
}