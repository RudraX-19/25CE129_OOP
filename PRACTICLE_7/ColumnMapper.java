// ============================================================
//  PRACTICLE 7 - Part A - Task 3: Column Mapper (Advanced)
//  @Column(name) annotation; reflection populates an object's
//  fields from a header row and a data row by matching column names
//  File: PRACTICLE_7/ColumnMapper.java
// ============================================================

import java.lang.annotation.*;
import java.lang.reflect.Field;

// ---- @Column annotation ----

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

// ---- Model class ----

class Student {
    @Column(name = "id")
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "score")
    private String score;

    // Note: no @Column on grade — missing column handling
    private String grade;

    @Override
    public String toString() {
        return "Student{id='" + id + "', fullName='" + fullName
             + "', score='" + score + "', grade='" + grade + "'}";
    }
}

// ---- Mapper ----

class RowMapper {
    /**
     * Populates obj's @Column-annotated fields by matching column names
     * from headerRow against dataRow (CSV-style).
     * Missing columns are left as null and a message is printed.
     */
    public static void map(Object obj, String[] headerRow, String[] dataRow)
            throws IllegalAccessException {

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Column.class)) continue;

            String columnName = field.getAnnotation(Column.class).name();

            // Find column index in header
            int colIndex = -1;
            for (int i = 0; i < headerRow.length; i++) {
                if (headerRow[i].trim().equalsIgnoreCase(columnName)) {
                    colIndex = i;
                    break;
                }
            }

            field.setAccessible(true);
            if (colIndex >= 0 && colIndex < dataRow.length) {
                field.set(obj, dataRow[colIndex].trim());
            } else {
                System.out.println("  [WARN] Column '" + columnName + "' not found in header — left null");
            }
        }
    }
}

// ---- Driver ----

public class ColumnMapper {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Column Mapper using @Column + Reflection ===\n");

        // Header row from a CSV (notice: 'score' is present but 'grade' has no @Column)
        String[] header = { "id", "full_name", "score" };
        String[] data   = { "S101", "Riya Sharma", "92" };

        Student student = new Student();
        System.out.println("Mapping row: " + java.util.Arrays.toString(data));
        RowMapper.map(student, header, data);

        System.out.println("\nMapped object:");
        System.out.println(student);

        // Test missing column
        System.out.println("\n--- Test: missing 'score' column ---");
        String[] headerMissing = { "id", "full_name" };
        String[] dataMissing   = { "S102", "Arjun Patel" };

        Student student2 = new Student();
        RowMapper.map(student2, headerMissing, dataMissing);
        System.out.println("Mapped object: " + student2);
    }
}
