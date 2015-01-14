package com.mrz.lite.util;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DaoHelper {
	
	public DaoHelper() {

	}
	
	public static Hashtable<String, String> getStatements(List<Class<?>> classes) {
		Hashtable<String, String> statements = new Hashtable<String, String>();
		for (Class<?> cl : classes) {
			try {
				for (Field field : cl.getDeclaredFields()) {
					field.setAccessible(true);
					if (field.getName().toLowerCase().contains("sql")) {
						statements.put(field.getName(), field.get(field.getName()).toString());
					}
					field.setAccessible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statements;
	}
	
	public static Hashtable<String, String> getCreateStatements(Hashtable<String, String> statement) {
		Hashtable<String, String> createStatements = new Hashtable<String, String>();
		for (Map.Entry<String, String> statementEntry : statement.entrySet()) {
			if (statementEntry.getKey().toLowerCase().contains("create")) {
				createStatements.put(statementEntry.getKey(), statementEntry.getValue());
			}
		}
		return createStatements;
	}
	
	public static Hashtable<String, String> getDeleteStatements(Hashtable<String, String> statement) {
		Hashtable<String, String> deleteStatements = new Hashtable<String, String>();
		for (Map.Entry<String, String> statementEntry : statement.entrySet()) {
			if (statementEntry.getKey().toLowerCase().contains("delete")) {
				deleteStatements.put(statementEntry.getKey(), statementEntry.getValue());
			}
		}
		return deleteStatements;
	}

	public static String format(String fullQualifiedClassName){
		String[] fqcn = fullQualifiedClassName.split("\\.");
		StringBuffer packageName = new StringBuffer();
		if (fqcn.length == 2) {
			packageName.append("db");
		}
		else {
			for (int i = 0; i < fqcn.length - 2; i++) {
				if(i == 0) packageName.append(fqcn[i]);
				else packageName.append("."+fqcn[i]);
			}
			packageName.append(".db");
		} 
		return packageName.toString();
	}
}
