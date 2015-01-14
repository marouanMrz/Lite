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
			bw.append(" * The LiteDao class allows you to do CRUD ");
			bw.newLine();
			bw.append(" * operations on a specific entity.");
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
			bw.append("import java.lang.reflect.Field;");
			bw.newLine();
			bw.append("import java.util.LinkedList;");
			bw.newLine();
			bw.append("import java.util.List;");
			bw.newLine();
			bw.newLine();
			bw.append("import android.content.ContentValues;");
			bw.newLine();
			bw.append("import android.content.Context;");
			bw.newLine();
			bw.append("import android.database.Cursor;");
			bw.newLine();
			bw.append("import android.database.sqlite.SQLiteDatabase;");
			bw.newLine();
			bw.newLine();
			bw.append("public class LiteDao {");
			bw.newLine();
			bw.append("    private Class<?> entityClazz;");
			bw.newLine();
			bw.append("    private SQLiteDatabase db;");
			bw.newLine();
			bw.append("    private LiteCore liteCore;");
			bw.newLine();
			bw.newLine();
			bw.append("    public LiteDao(Context context, Class<?> entityClass) {");
			bw.newLine();
			bw.append("        this.liteCore = new LiteCore(context);");
			bw.newLine();
			bw.append("        this.entityClazz = entityClass;");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("    @SuppressWarnings(\"unchecked\")");
			bw.newLine();
			bw.append("    public <T extends Object> List<T> selectAll() {");
			bw.newLine();
			bw.append("        List<Object> all = new LinkedList<Object>();");
			bw.newLine();
			bw.append("        db = liteCore.getReadableDatabase();");
			bw.newLine();
			bw.append("        Cursor cursor = db.query(entityClazz.getSimpleName(), null, null, null, null, null, null);");
			bw.newLine();
			bw.append("        if (cursor != null) {");
			bw.newLine();
			bw.append("            cursor.moveToFirst();");
			bw.newLine();
			bw.append("                while (cursor.moveToNext()) {");
			bw.newLine();
			bw.append("                    Object currentInstance = null;");
			bw.newLine();
			bw.append("                    try {");
			bw.newLine();
			bw.append("                        currentInstance = Class.forName(entityClazz.getName());");
			bw.newLine();
			bw.append("                        for (Field field : entityClazz.getFields()) {");
			bw.newLine();
			bw.append("                            field.setAccessible(true);");
			bw.newLine();
			bw.append("                            field.set(currentInstance, cursor.getColumnIndex(field.getName()));");
			bw.newLine();
			bw.append("                            field.setAccessible(false);");
			bw.newLine();
			bw.append("                        }");
			bw.newLine();
			bw.append("                        all.add(currentInstance);");
			bw.newLine();
			bw.append("                    } catch (ClassNotFoundException e) {");
			bw.newLine();
			bw.append("                        e.printStackTrace();");
			bw.newLine();
			bw.append("                    } catch (IllegalAccessException e) {");
			bw.newLine();
			bw.append("                        e.printStackTrace();");
			bw.newLine();
			bw.append("                    } catch (IllegalArgumentException e) {");
			bw.newLine();
			bw.append("                        e.printStackTrace();");
			bw.newLine();
			bw.append("                    }");
			bw.newLine();
			bw.append("                }");
			bw.newLine();
			bw.append("                cursor.close();");
			bw.newLine();
			bw.append("        }");
			bw.newLine();
			bw.append("        db.close();");
			bw.newLine();
			bw.append("        return (List<T>) all;");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.newLine();
			bw.append("    public int insert(Object object) {");
			bw.newLine();
			bw.append("        if (object.getClass().isAssignableFrom(entityClazz)) {");
			bw.newLine();
			bw.append("            db = liteCore.getWritableDatabase();");
			bw.newLine();
			bw.append("            ContentValues values = new ContentValues();");
			bw.newLine();
			bw.append("            for (Field field : entityClazz.getFields()) {");
			bw.newLine();
			bw.append("                field.setAccessible(true);");
			bw.newLine();
			bw.append("                try {");
			bw.newLine();
			bw.append("                    values.put(field.getName(), field.get(object).toString());");
			bw.newLine();
			bw.append("                } catch (IllegalAccessException e) {");
			bw.newLine();
			bw.append("                    e.printStackTrace();");
			bw.newLine();
			bw.append("                } catch (IllegalArgumentException e) {");
			bw.newLine();
			bw.append("                    e.printStackTrace();");
			bw.newLine();
			bw.append("                }");
			bw.newLine();
			bw.append("                field.setAccessible(false);");
			bw.newLine();
			bw.append("            }");
			bw.newLine();
			bw.append("            db.insert(entityClazz.getSimpleName(), null, values);");
			bw.newLine();
			bw.append("            db.close();");
			bw.newLine();
			bw.append("            return 1;");
			bw.newLine();
			bw.append("        } else return 0;");
			bw.newLine();
			bw.append("    }");
			bw.newLine();
			bw.append("}");
			bw.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
