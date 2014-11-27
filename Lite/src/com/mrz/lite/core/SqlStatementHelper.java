package com.mrz.lite.core;

import java.util.HashMap;
import java.util.Map;

public class SqlStatementHelper {
	private static HashMap<String, String> sqlCreate;
	private static HashMap<String, String> sqlDelete;
	
	static{
		sqlCreate = new HashMap<>();
		sqlDelete = new HashMap<>();
	}

	public static HashMap<String, String> getSqlCreate() {
		return sqlCreate;
	}

	public static void setSqlCreate(HashMap<String, String> sqlCreate) {
		for (Map.Entry<String, String> sqlCreateEntry : sqlCreate.entrySet()) {
			SqlStatementHelper.sqlCreate.put(sqlCreateEntry.getKey(), sqlCreateEntry.getValue());
		}
	}

	public static HashMap<String, String> getSqlDelete() {
		return sqlDelete;
	}

	public static void setSqlDelete(HashMap<String, String> sqlDelete) {
		for (Map.Entry<String, String> sqlDeleteEntry : sqlDelete.entrySet()) {
			SqlStatementHelper.sqlCreate.put(sqlDeleteEntry.getKey(), sqlDeleteEntry.getValue());
		}
	}
	
}
