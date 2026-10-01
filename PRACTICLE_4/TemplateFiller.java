// ============================================================
//  PRACTICLE 4 - Part A - Task 3: Template Filler (Advanced)
//  File: PRACTICLE_4/TemplateFiller.java
// ============================================================

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TemplateFiller {
    public static void main(String[] args) {
        System.out.println("=== Template Filler ===\n");
        
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};
        
        System.out.println("Template: " + template);
        System.out.println("\nAvailable placeholders:");
        for (int i = 0; i < names.length; i++) {
            System.out.println("  {" + names[i] + "} → " + values[i]);
        }
        System.out.println("  {date} → (not supplied)");
        
        // Compile pattern for {placeholder}
        Pattern pattern = Pattern.compile("\\{(\\w+)\\}");
        Matcher matcher = pattern.matcher(template);
        
        StringBuilder result = new StringBuilder();
        int lastEnd = 0;
        
        while (matcher.find()) {
            // Append text before the placeholder
            result.append(template, lastEnd, matcher.start());
            
            // Get the placeholder name
            String placeholder = matcher.group(1);
            
            // Look up the value
            String value = null;
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(placeholder)) {
                    value = values[i];
                    break;
                }
            }
            
            // Append value or [?] if not found
            if (value != null) {
                result.append(value);
            } else {
                result.append("[?]");
            }
            
            lastEnd = matcher.end();
        }
        
        // Append remaining text
        result.append(template.substring(lastEnd));
        
        System.out.println("\n=== Filled Template ===");
        System.out.println(result.toString());
    }
}
