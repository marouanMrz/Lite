package com.mrz.lite.generators;

import java.io.BufferedWriter;

import javax.tools.JavaFileObject;

import com.mrz.lite.models.EntityModel;
import com.mrz.lite.models.FieldModel;

public class DefaultGenerator implements Generator {
    
	@Override
	public void generate(JavaFileObject jfo, EntityModel entityModel) {
		try {
			BufferedWriter bw = new BufferedWriter(jfo.openWriter());
			bw.append("package ");
			bw.append(entityModel.getPackageName());
			bw.append(";");
			bw.newLine();
			bw.newLine();
			bw.append("import android.provider.BaseColumns;");
			bw.newLine();
			bw.newLine();
			bw.append("public static abstract class " + entityModel.getClassName() + "Contract implements BaseColumns" + "{");
			bw.newLine();
			bw.newLine();
			bw.append("    public static final String TABLE_NAME = \"" + entityModel.getClassName() + "\";");
			bw.newLine();
			bw.append("    public static final String ID = \"" + entityModel.getClassName() + "_id\";");
			bw.newLine();
			for (FieldModel field : entityModel.getFields()) {
				bw.append("    public static final " + field.getType().toString() + " " + field.getName().toUpperCase() + " = \"" + field.getName() + "\";");
				bw.newLine();
			}
			bw.append("}");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
