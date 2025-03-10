package com.ques3_6mar.sql;

import org.reflections.Reflections;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

public class GenerateSQL {

    public static void main(String[] args) {
        try {
            generateSQLScripts("com.ques3_6mar.sql");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateSQLScripts(String packageName) throws IOException {
        // Scan the package for @Entity classes
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);

        FileWriter writer = new FileWriter("generated-sql.sql");

        for (Class<?> entityClass : entityClasses) {
            String tableName = entityClass.getSimpleName().toLowerCase(); // Table name is the class name (lowercase)

            // Start generating the CREATE TABLE script
            StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " (\n");

            for (Field field : entityClass.getDeclaredFields()) {
                // If the field is annotated with @Id, it's a primary key
                if (field.isAnnotationPresent(Id.class)) {
                    sql.append("  ").append(field.getName()).append(" ").append(mapJavaTypeToSQL(field.getType())).append(" PRIMARY KEY,\n");
                } else if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    sql.append("  ").append(field.getName()).append(" ").append(mapJavaTypeToSQL(field.getType()));
                    if (column.nullable() == false) {
                        sql.append(" NOT NULL");
                    }
                    sql.append(",\n");
                }
            }

            // Remove the last comma and newline, then close the statement
            sql.setLength(sql.length() - 2);  // Remove trailing comma
            sql.append("\n);\n\n");

            // Write the SQL script to the file
            writer.write(sql.toString());
        }

        writer.close();
        System.out.println("SQL scripts generated successfully!");
    }

    // Helper method to map Java types to SQL types
    private static String mapJavaTypeToSQL(Class<?> javaType) {
        if (javaType == Long.class || javaType == long.class) {
            return "BIGINT";
        } else if (javaType == String.class) {
            return "VARCHAR(255)";
        } else if (javaType == Integer.class || javaType == int.class) {
            return "INTEGER";
        } else if (javaType == Double.class || javaType == double.class) {
            return "DOUBLE";
        } else if (javaType == Boolean.class || javaType == boolean.class) {
            return "BOOLEAN";
        } else {
            return "VARCHAR(255)"; // Default to string if type is unknown
        }
    }
}
