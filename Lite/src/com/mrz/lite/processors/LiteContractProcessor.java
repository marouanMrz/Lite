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
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import com.mrz.lite.annotations.LiteContract;
import com.mrz.lite.generators.CoreGenerator;
import com.mrz.lite.generators.DaoGenerator;
import com.mrz.lite.generators.Generator;
import com.mrz.lite.generators.HelperGenerator;
import com.mrz.lite.models.EntityModel;
import com.mrz.lite.util.ProcessorHelper;

@SupportedAnnotationTypes({"com.mrz.lite.annotations.LiteContract"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class LiteContractProcessor extends AbstractProcessor {
	private EntityModel entityModel;
	
	public LiteContractProcessor() {
		super();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(LiteContract.class);
		for (Element e : elements) {
			if (e.getKind() == ElementKind.CLASS) {
				entityModel = new EntityModel();
				TypeElement classElement = (TypeElement) e;
				Boolean test = false;
				for (TypeMirror inter : classElement.getInterfaces()) {
					if (inter.toString().equals("android.provider.BaseColumns")) {
						test = true;
					}
				}
				if (!test) return false;
				entityModel.setPackageName(ProcessorHelper.generateDbPackage(classElement.getQualifiedName().toString()));
				entityModel.setFullQualifiedClassName(ProcessorHelper.generateLiteHelperFullQualifiedClassName(classElement.getQualifiedName().toString()));
				entityModel.setClassName(ProcessorHelper.generateLiteHelperFullQualifiedClassName(classElement.getSimpleName().toString()));
				entityModel.setFields(ProcessorHelper.fieldsMapper(classElement));
			}
		}
		JavaFileObject jfoLiteHelper = null;
		JavaFileObject jfoLiteCore = null;
		JavaFileObject jfoLiteDao = null;
		
		try {
			String fqClassName = entityModel.getFullQualifiedClassName();
			jfoLiteHelper = processingEnv.getFiler().createSourceFile(fqClassName + "LiteHelper");
			jfoLiteCore = processingEnv.getFiler().createSourceFile(entityModel.getPackageName() + ".LiteCore");
			jfoLiteDao = processingEnv.getFiler().createSourceFile(entityModel.getPackageName() + ".LiteDao");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Generator helperGenerator = new HelperGenerator();
		helperGenerator.generate(jfoLiteHelper, entityModel);
		Generator coreGenerator = new CoreGenerator();
		coreGenerator.generate(jfoLiteCore, entityModel);
		Generator daoGenerator = new DaoGenerator();
		daoGenerator.generate(jfoLiteDao, entityModel);
		return true;
	}

}
