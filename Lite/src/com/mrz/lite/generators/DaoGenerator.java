package com.mrz.lite.generators;

import java.io.BufferedWriter;

import javax.tools.JavaFileObject;

import com.mrz.lite.models.EntityModel;

public class DaoGenerator implements Generator {

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
			bw.append("package " + entityModel.getPackageName() + ";");
			bw.newLine();
			bw.newLine();
			bw.append("/**");
			bw.newLine();
			bw.append(" * Lite Framework");
			bw.newLine();
			bw.append(" * ");
			bw.newLine();
			bw.append(" * The LiteDao<T> generic class allows you to do CRUD");
			bw.newLine();
			bw.append(" * operations on a specific entity.");
			bw.newLine();
			bw.append(" * ");
			bw.newLine();
			bw.append(" * @author Marouan Marzouga - MRz");
			bw.newLine();
			bw.append(" */");
			bw.newLine();
			bw.newLine();
			bw.append("import android.annotation.TargetApi;");
			bw.newLine();
			bw.append("import android.content.Context;");
			bw.newLine();
			bw.append("import android.database.sqlite.SQLiteDatabase;");
			bw.newLine();
			bw.append("import android.database.sqlite.SQLiteOpenHelper;");
			bw.newLine();
			bw.append("import android.os.Build;");
			bw.newLine();
			bw.append("import com.mrz.lite.core.DaoHelper;");
			bw.newLine();
			bw.append("import java.util.Hashtable;");
			bw.newLine();
			bw.append("import java.util.LinkedList;");
			bw.newLine();
			bw.append("import java.util.Map;");
			bw.newLine();
			bw.newLine();
			bw.append("public class LiteDao<T> extends SQLiteOpenHelper {");
			bw.newLine();
			bw.append("    private String uri;");
			bw.newLine();
			bw.append("    private LinkedList<?> classes;");
			bw.newLine();
			bw.append("    private Hashtable<String, String> sqlCreate;");
			bw.newLine();
			bw.append("    private Hashtable<String, String> sqlDelete;");
			bw.newLine();
			bw.append("    private Hashtable<String, String> sqlStatements;");
			bw.newLine();
			bw.append("    public static final int DATABASE_VERSION = 1;");
			bw.newLine();
			bw.append("    public static final String DATABASE_NAME = \"LiteEmbedded.db\";");
			bw.newLine();
			bw.newLine();
			bw.newLine();
			bw.append("    public LiteDao(Context context, Object setAlwaysThis) {");
			bw.newLine();
			bw.append("        super(context, DATABASE_NAME, null, DATABASE_VERSION);");
			bw.newLine();
			bw.append("        this.sqlCreate = new Hashtable<String, String>();");
			bw.newLine();
			bw.append("        this.sqlDelete = new Hashtable<String, String>();");
			bw.newLine();
			bw.append("        this.sqlStatements = new Hashtable<String, String>();");
			bw.newLine();
			bw.append("        this.uri = setAlwaysThis.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();");
			bw.newLine();
			bw.append("        sqlStatements = DaoHelper.getStatements(this.uri, DaoHelper.format(setAlwaysThis.getClass().getCanonicalName()));");
			bw.newLine();
			bw.append("        sqlCreate = DaoHelper.getCreateStatements(sqlStatements);");
			bw.newLine();
			bw.append("        sqlDelete = DaoHelper.getDeleteStatements(sqlStatements);");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("    public void onCreate(SQLiteDatabase db) {");
			bw.newLine();
			bw.append("        for(Map.Entry<String, String> createEntry : sqlCreate.entrySet()) {");
			bw.newLine();
			bw.append("            db.execSQL(createEntry.getValue());");
			bw.newLine();
			bw.append("        }");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {");
			bw.newLine();
			bw.append("        for(Map.Entry<String, String> deleteEntry : sqlDelete.entrySet()) {");
			bw.newLine();
			bw.append("            db.execSQL(deleteEntry.getValue());");
			bw.newLine();
			bw.append("        }");
			bw.newLine();
			bw.append("        onCreate(db);");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("    @TargetApi(Build.VERSION_CODES.HONEYCOMB)");
			bw.newLine();
			bw.append("    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {");
			bw.newLine();
			bw.append("        super.onDowngrade(db, oldVersion, newVersion);");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("}");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
