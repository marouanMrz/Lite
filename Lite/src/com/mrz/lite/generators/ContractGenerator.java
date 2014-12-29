package com.mrz.lite.generators;

import java.io.BufferedWriter;

import javax.tools.JavaFileObject;

import com.mrz.lite.models.EntityModel;
import com.mrz.lite.models.FieldModel;

public class ContractGenerator implements Generator {
    
	@Override
	public void generate(JavaFileObject jfo, EntityModel entityModel) {
		try {
			BufferedWriter bw = new BufferedWriter(jfo.openWriter());
			bw.append("/*");
			bw.newLine();
			bw.append(" * Automatically generated file. DO NOT MODIFY");
			bw.newLine();
			bw.append(" * ");
			bw.newLine();
			bw.append(" * This class was automatically generated by");
			bw.newLine();
			bw.append(" * Lite Framework from the resource data it found.");
			bw.newLine();
			bw.append(" * It should not be modified by hand.");
			bw.newLine();
			bw.append(" */");
			bw.newLine();
			bw.newLine();
			bw.append("package ");
			bw.append(entityModel.getPackageName());
			bw.append(";");
			bw.newLine();
			bw.newLine();
			bw.append("/**");
			bw.newLine();
			bw.append(" * Lite Framework");
			bw.newLine();
			bw.append(" * ");
			bw.newLine();
			bw.append(" * The contract class allows you to use the same constants");
			bw.newLine();
			bw.append(" * across all the other classes in the same package.");
			bw.newLine();
			bw.append(" * This lets you change a column name in one ");
			bw.newLine();
			bw.append(" * place and have it propagate throughout your code.");
			bw.newLine();
			bw.append(" * ");
			bw.newLine();
			bw.append(" * @author Marouan Marzouga - MRz");
			bw.newLine();
			bw.append(" * marouan.mrz@gmail.com");
			bw.newLine();
			bw.append(" */");
			bw.newLine();
			bw.newLine();
			bw.append("import com.mrz.lite.annotations.LiteContract;");
			bw.newLine();
			bw.append("import android.provider.BaseColumns;");
			bw.newLine();
			bw.newLine();
			bw.append("@LiteContract");
			bw.newLine();
			bw.append("public class " + entityModel.getClassName() + "Contract implements BaseColumns" + " {");
			bw.newLine();
			bw.append("    public static final String TABLE_NAME = \"" + entityModel.getClassName() + "\";");
			bw.newLine();
			bw.append("    public static final String ID = \"" + entityModel.getClassName().toLowerCase() + "_id\";");
			bw.newLine();
			for (FieldModel field : entityModel.getFields()) {
				bw.append("    public static final String " + field.getName().toUpperCase() + " = \"" + field.getName() + "\";");
				bw.newLine();
			}
			bw.append("}");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
