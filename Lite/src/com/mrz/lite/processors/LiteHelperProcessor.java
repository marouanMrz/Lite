package com.mrz.lite.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import com.mrz.lite.annotations.LiteHelper;

@SupportedAnnotationTypes({"com.mrz.lite.annotations.LiteHelper"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LiteHelperProcessor extends AbstractProcessor {
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(LiteHelper.class);
		for (Element e : elements) {
			if (e.getKind() == ElementKind.CLASS) {
				TypeElement classElement = (TypeElement) e;
				HashMap<String, String> fieldsValues = ProcessorHelper.getFieldsValues(classElement);
				HashMap<String, String> sqlCreate = new HashMap<String, String>();
				HashMap<String, String> sqlDelete = new HashMap<String, String>();
				for(Map.Entry<String, String> fieldsValuesEntry : fieldsValues.entrySet()){
					if (fieldsValuesEntry.getKey().toLowerCase().contains("create")) {
						sqlCreate.put(fieldsValuesEntry.getKey(), fieldsValuesEntry.getValue());
					} else {
						sqlDelete.put(fieldsValuesEntry.getKey(), fieldsValuesEntry.getValue());
					}
				}
			}
		}
		return true;
	}

}
