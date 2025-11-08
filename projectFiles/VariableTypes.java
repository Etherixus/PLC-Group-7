package projectFiles;

import java.util.Set;
/*
* The point of this class is just to check the values of variables and nothing else, technically it could be used to
* check the type of a function as well except an additional check would have to be made to see if the function is void as that is a valid function type but not variable type
* */
public class VariableTypes {
    private static final Set<String> variableTypes = Set.of("Integer", "String", "Boolean", "Double");

    public static boolean isValidVariableType(String type){
        return variableTypes.contains(type);
    }

//    public static boolean isNumeric(String t){ return "Num".equals(t); }
//    public static boolean isString (String t){ return "Str".equals(t); }
//    public static boolean isBoolean(String t){ return "Bool".equals(t); }
//    public static boolean isVoid   (String t){ return "Void".equals(t); }
}
