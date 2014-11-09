package com.mrz.lite.processors;

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

import com.mrz.lite.annotations.LiteEntity;

@SupportedAnnotationTypes({"com.mrz.lite.annotations.LiteEntity"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LiteProcessor extends AbstractProcessor {
	private String className;
	private String packageName;
	private String fullQualifiedClassName;

	public LiteProcessor() {
		super();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(LiteEntity.class);
		for (Element e : elements) {
			if (e.getKind() == ElementKind.CLASS) {
				TypeElement classElement = (TypeElement) e;
				PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

				fullQualifiedClassName = classElement.getQualifiedName().toString();
				className = "" + classElement.getSimpleName();
				packageName = packageElement.getQualifiedName().toString();
			}
		}

		return false;
	}

}
