package projectFiles;
import java.util.Arrays;

import java.util.Set;

public class VariableTypes {
    private static final Set<String> variableTypes = Set.of("Integer", "Double", "Boolean", "String");

    public static boolean isValidVariableType(String type){
        return variableTypes.contains(type);
    }
}
