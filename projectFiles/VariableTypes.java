package projectFiles;

import java.util.Set;

public class VariableTypes {
    private static final Set<String> variableTypes = Set.of("Num", "Str", "Bool", "Void");

    public static boolean isValidVariableType(String type){
        return variableTypes.contains(type);
    }

    public static boolean isNumeric(String t){ return "Num".equals(t); }
    public static boolean isString (String t){ return "Str".equals(t); }
    public static boolean isBoolean(String t){ return "Bool".equals(t); }
    public static boolean isVoid   (String t){ return "Void".equals(t); }
}
