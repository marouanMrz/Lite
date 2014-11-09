package com.mrz.lite.generators;

import javax.tools.JavaFileObject;

public interface Generator {
	public void generate(JavaFileObject jfo, String className, String packageName, String fullQualifiedClassName);
}
