package com.mrz.lite.generators;

import java.io.BufferedWriter;

import javax.tools.JavaFileObject;

import com.mrz.lite.models.EntityModel;

public class HelperGenerator implements Generator {

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
			bw.append(" * The helper class allows you to define some constants");
			bw.newLine();
			bw.append(" * that create and maintain the database and tables.");
			bw.newLine();
			bw.append(" * ");
			bw.newLine();
			bw.append(" * @author Marouan Marzouga - MRz");
			bw.newLine();
			bw.append(" */");
			bw.newLine();
			bw.newLine();
			bw.append("@LiteHelper");
			bw.newLine();
			bw.append("public class " + entityModel.getClassName() + "LiteHelper" + " {");
			bw.newLine();
			bw.append("    private static final String TEXT_TYPE = \" TEXT\";");
			bw.newLine();
			bw.append("    private static final String COMMA_SEP = \",\";");
			bw.newLine();
			bw.append("    private static final String SQL_CREATE_" + entityModel.getClassName().toUpperCase() + " =");
			bw.newLine();
			bw.append("			\"CREATE TABLE \" + " + entityModel.getClassName() + "Contract.TABLE_NAME +" + "\" (\" +");
			bw.newLine();
			bw.append("			" + entityModel.getClassName() + "Contract._ID +" + "\" INTEGER PRIMARY KEY AUTOINCREMENT,\" +");
			bw.newLine();
			int size = entityModel.getFields().size();
			for (int i = 0; i < size; i++) {
				if (i + 1 == size) {
					bw.append("			" + entityModel.getClassName() + "Contract." + entityModel.getFields().get(i).getName() + " + TEXT_TYPE +");
				} else {
					bw.append("			" + entityModel.getClassName() + "Contract." + entityModel.getFields().get(i).getName() + " + TEXT_TYPE + COMMA_SEP +");
				}
				bw.newLine();
			}
			bw.append("			\" )\";");
			bw.newLine();
			bw.append("    private static final String SQL_DELETE_" + entityModel.getClassName().toUpperCase() +" =");
			bw.newLine();
			bw.append("			\"DROP TABLE IF EXISTS \" + " + entityModel.getClassName() + "Contract" + ".TABLE_NAME;");
			bw.newLine();
			bw.append("}");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
