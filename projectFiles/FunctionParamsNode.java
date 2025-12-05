package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class FunctionParamsNode implements JottTree {
    private ArrayList<IDNode> paramID;
    private LinkedHashMap<IDNode, String> params;

    public FunctionParamsNode(LinkedHashMap<IDNode, String> params, ArrayList<IDNode> paramID) {
        this.paramID = paramID;
        this.params = params;
    }


    public static FunctionParamsNode parseFunctionParams(ArrayList<Token> tokens) throws ParserSyntaxError {
        LinkedHashMap<IDNode, String> params = new LinkedHashMap<>();
        ArrayList<IDNode> paramID = new ArrayList<>();
        //handles  1 param
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            IDNode paramName = IDNode.parseIDNode(tokens);
            if(tokens.get(0).getTokenType() != TokenType.COLON){
                throw new ParserSyntaxError("Expected a Colon", tokens.get(0));
            }
            tokens.remove(0);
            String type = tokens.get(0).getToken();
            if(!VariableTypes.isValidVariableType(type)){
                throw new ParserSyntaxError("Expected a valid variable type but got: " + type, tokens.get(0));
            }
            tokens.remove(0);
            paramID.add(paramName);
            params.put(paramName, type);

            //more than one param
            while(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
                if(tokens.get(0).getTokenType() != TokenType.COMMA){
                    throw new ParserSyntaxError("Expected a Comma", tokens.get(0));
                }
                tokens.remove(0);
                paramName =IDNode.parseIDNode(tokens);

                if(tokens.get(0).getTokenType() != TokenType.COLON){
                    throw new ParserSyntaxError("Expected a Colon", tokens.get(0));
                }
                tokens.remove(0);
                type = tokens.get(0).getToken();
                if(!VariableTypes.isValidVariableType(type)){
                    throw new ParserSyntaxError("Expected a valid variable type but got: " + type, tokens.get(0));
                }
                tokens.remove(0);
                paramID.add(paramName);
                params.put(paramName, type);
            }
        }
        return new FunctionParamsNode(params, paramID);
    }

    @Override
    public String convertToJott() {
        StringBuilder result = new StringBuilder();
        if(params.isEmpty()) {
            return "";
        }
        else{
            Set<IDNode> keySet = params.keySet();
            for(IDNode key : keySet){
                result.append(key.convertToJott());
                result.append(":");
                result.append(params.get(key));
                result.append(",");
            }
            result.setLength(result.length()-1);
        }
        return result.toString();
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }

    //Returns a list of parameter types (in order) for global function declarations.
    //Example: ["Integer", "String"]
    public ArrayList<String> getParamTypes() {
        ArrayList<String> types = new ArrayList<>();
        for (String type : params.values()) {
            types.add(type);
        }
        return types;
    }

    //Declares each parameter in the given symbol table (usually the function scope).
    //This lets function parameters behave like pre-declared variables.
    public void declareParams(SymbolTable funcTable) throws SemanticSyntaxError {
        for (Map.Entry<IDNode, String> entry : params.entrySet()) {
            IDNode idNode = entry.getKey();
            String paramName = entry.getKey().convertToJott();
            String paramType = entry.getValue();

            // Check for duplicate parameter names
            if (funcTable.lookup(paramName) != null) {
                throw new SemanticSyntaxError("Duplicate parameter name: " + paramName, idNode.getToken());
            }
            // Create the parameter symbol
            Symbol paramSymbol = new Symbol(paramName, paramType, -1);

            // Mark parameters as initialized — they come from the function call
            paramSymbol.initialized = true;

            paramSymbol.lineNum = 0;

            // Add parameter to the function's local symbol table
            funcTable.addSymbol(paramName, paramSymbol, idNode.getToken());

        }
    }

    public void declareParams(SymbolTable funcTable, ParamsNode paramsNode) throws SemanticSyntaxError {
        ArrayList<ParamsTNode> paramValues = new ArrayList<>(paramsNode.params);
        for (Map.Entry<IDNode, String> entry : params.entrySet()) {
            Object currentValue = paramValues.get(0).getExpression().evaluate();
            paramValues.remove(0);
            IDNode idNode = entry.getKey();
            String paramName = entry.getKey().convertToJott();
            String paramType = entry.getValue();

            // Check for duplicate parameter names
            if (funcTable.lookup(paramName) != null) {
                throw new SemanticSyntaxError("Duplicate parameter name: " + paramName, idNode.getToken());
            }
            // Create the parameter symbol
            Symbol paramSymbol = new Symbol(paramName, paramType, -1);

            // Mark parameters as initialized — they come from the function call
            paramSymbol.initialized = true;
            paramSymbol.setValue(currentValue);
            paramSymbol.lineNum = 0;

            // Add parameter to the function's local symbol table
            funcTable.addSymbol(paramName, paramSymbol, idNode.getToken());

        }
    }

    @Override
    public boolean validateTree() throws SemanticSyntaxError {
        // separates the hash into two vars, param name and type
        for (Map.Entry<IDNode, String> entry : params.entrySet()) {
            IDNode idNode = entry.getKey();
            String paramName = entry.getKey().convertToJott();
            String paramType = entry.getValue();

            // Check Valid parameter type
            if (!(paramType.equals("Integer") || paramType.equals("Double") ||
                    paramType.equals("String")  || paramType.equals("Boolean"))) {
                throw new SemanticSyntaxError(
                        "Invalid parameter type '" + paramType + "' for parameter '" + paramName + "'", idNode.getToken()
                );
            }

            // Check Reserved keyword used as parameter name
            if (paramName.equals("if") || paramName.equals("while") ||
                    paramName.equals("return") || paramName.equals("Def") ||
                    paramName.equals("Void") || paramName.equals("Integer") ||
                    paramName.equals("Double") || paramName.equals("String") ||
                    paramName.equals("Boolean")) {
                throw new SemanticSyntaxError(
                        "Invalid parameter name (reserved keyword): " + paramName, idNode.getToken()
                );
            }
        }

        // all parameters are valid
        return true;
    }
}
