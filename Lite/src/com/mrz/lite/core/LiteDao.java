package com.mrz.lite.core;

import java.util.HashMap;

public class LiteDao {

	public LiteDao() {
	}
	
	public static HashMap<String, String> getCreate() {
		return SqlStatementHelper.getSqlCreate();
	}
	
	public static HashMap<String, String> getDelete() {
		return SqlStatementHelper.getSqlDelete();
	}
}
