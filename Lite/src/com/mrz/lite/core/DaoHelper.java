package com.mrz.lite.core;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DaoHelper {
	private static ClassLoader classLoader;
	
	public DaoHelper() {

	}
	
	public static Hashtable<String, String> getStatements(String uri, String pack) {
		List<String> classes = new LinkedList<String>();
		String[] files = new File(uri+pack.replace(".", "/")+"/").list();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];
			if (fileName.endsWith(".class") && fileName.toLowerCase().contains("helper")) {
				classes.add(pack + "." + fileName.split("\\.")[0]);
			}
		}
		return loadClasses(uri, classes);
	}
	
	public static Hashtable<String, String> loadClasses(String uri, List<String> classes) {
		Hashtable<String, String> statements = new Hashtable<String, String>();
		File f = new File(uri);
		URL url = null;
		try {
			url = f.toURI().toURL();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		URL[] urls = new URL[]{url};
		classLoader = new URLClassLoader(urls);
		for (String clazz : classes) {
			try {
				for (Field field : classLoader.loadClass(clazz).getDeclaredFields()) {
					field.setAccessible(true);
					if (field.getName().toLowerCase().contains("sql")) {
						statements.put(field.getName(), field.get(field.getName()).toString());
					}
					field.setAccessible(false);
				}
			} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return statements;
	}
	
	public static Hashtable<String, String> getCreateStatement(Hashtable<String, String> statement) {
		Hashtable<String, String> createStatements = new Hashtable<String, String>();
		for (Map.Entry<String, String> statementEntry : statement.entrySet()) {
			if (statementEntry.getKey().toLowerCase().contains("create")) {
				createStatements.put(statementEntry.getKey(), statementEntry.getValue());
			}
		}
		return createStatements;
	}
	
	public static Hashtable<String, String> getDeleteStatement(Hashtable<String, String> statement) {
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
