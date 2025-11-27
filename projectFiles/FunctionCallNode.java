package projectFiles;


import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionCallNode extends ExpressionNode implements BodyStmtNode {
    private IDNode id;
    private ParamsNode params;

    public FunctionCallNode(IDNode id, ParamsNode params){
        this.id = id;
        this.params = params;
    }

    @Override
    public Token getToken() {
        return id.getToken();
    }


    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() != TokenType.FC_HEADER){
            throw new ParserSyntaxError("Expecting fc header, got: " + tokens.get(0));
        }
        tokens.remove(0);
        IDNode name = IDNode.parseIDNode(tokens);
        ParamsNode params = ParamsNode.parseParamsNode(tokens);

        return new FunctionCallNode(name, params);
    }

    // returns the return type of the function call
    public String getReturnType(SymbolTable table) throws SemanticSyntaxError {
        final String funcName = id.convertToJott();

        // Look up the function symbol
        Symbol sym = table.lookup(funcName);
        // if the lookup returns null then throw an error
        if (sym == null) {
            Token t = id.getToken();
            throw new SemanticSyntaxError("Call to unknown function " + funcName, t);
        }

        validateTree(table);

        return sym.returnType;
    }

    public boolean validateTree(SymbolTable table) throws SemanticSyntaxError {
        String funcName = id.convertToJott();
        Token t = id.getToken();

        // Lookup function in the symbol table chain
        Symbol funcSymbol = table.lookup(funcName);
        if (funcSymbol == null) {
            throw new SemanticSyntaxError("Undeclared function '" + funcName + "'", t);
        }

        // Must be a function, not a variable
        if (!funcSymbol.isFunction) {
            throw new SemanticSyntaxError("'" + funcName + "' is not a function", t);
        }

        // gets expected parameter count
        int expectedCount = (funcSymbol.paramTypes != null) ? funcSymbol.paramTypes.size() : 0;
        int actualCount = 0;

        if (params != null && params.getParamsExprList() != null) {
            actualCount = params.getParamsExprList().size();

            // Validate each parameter expression type (may throw SemanticSyntaxError)
            for (ExpressionNode param : params.getParamsExprList()) {
                param.getType(table);
            }
        }
        // Compare argument count
        if (expectedCount != actualCount) {
            throw new SemanticSyntaxError(
                    "Function '" + funcName + "' expects " + expectedCount +
                            " argument(s) but got " + actualCount, t
            );
        }

        // type checking for each parameter
        for (int i = 0; i < expectedCount; i++) {
            String expectedType = funcSymbol.paramTypes.get(i);
            String actualType = params.getParamsExprList().get(i).getType(table);

            // Allow "Any" for the print function
            if (!expectedType.equals("Any") && !expectedType.equals(actualType)) {
                throw new SemanticSyntaxError(
                        "Parameter " + (i + 1) + " of function '" + funcName +
                                "' expects " + expectedType + " but got " + actualType, t
                );
            }
        }

        return true;
    }

    @Override
    public String convertToJott() {
        return "::" + id.convertToJott() + "[" + params.convertToJott() + "]";
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

    @Override
    public boolean validateTree() {
        return id != null && params != null && id.validateTree() && params.validateTree();
    }

    @Override
    public void execute() {

        String funcName = id.convertToJott();

        // Built-in print
        if (funcName.equals("print")) {

            // Loop through argument expressions
            for (ExpressionNode expr : params.getParamsExprList()) {
                Object value = expr.evaluate();
                System.out.println(value);
            }

            return;
        }

        throw new RuntimeException("Function not implemented yet: " + funcName);
    }

    @Override
    public Object evaluate() {

        String funcName = id.convertToJott();

        if (funcName.equals("print")) {

            for (ExpressionNode expr : params.getParamsExprList()) {
                Object value = expr.evaluate();
                System.out.println(value);
            }

            return null; // print returns Void
        }

        throw new RuntimeException("Function not implemented yet: " + funcName);
    }




}
