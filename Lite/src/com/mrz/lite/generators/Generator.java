package com.mrz.lite.generators;

import javax.tools.JavaFileObject;

import com.mrz.lite.models.EntityModel;

public interface Generator {
	public void generate(JavaFileObject jfo, EntityModel entityModel);
}
