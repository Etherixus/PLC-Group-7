package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;


import java.util.ArrayList;

public class ReturnStmtNode implements JottTree {
    ExpressionNode expr;

    public ReturnStmtNode(ExpressionNode expr){
        this.expr = expr;
    }

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("Return")){
            throw new ParserSyntaxError("Expected Return, Got: ", tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.SEMICOLON){
            throw new ParserSyntaxError("Expected Semicolon, Got: ", tokens.get(0));
        }
        tokens.remove(0);
        return new ReturnStmtNode(expressionNode);
    }
    @Override
    public String convertToJott(){
        return "Return " + expr.convertToJott() + ";";
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


    //Validates this return statement against the expected return type.
    public boolean validateTree(SymbolTable symbolTable, String expectedType) {
        try {
            // if its null then we should expect its type to be void
            if (expr == null) {
                // Valid only if the function expects "Void"
                if (!expectedType.equals("Void")) {
                    System.err.println("Semantic Error: Return statement missing a value for non-void function.");
                    return false;
                }
                return true;
            }

            // if expression exists, get its type from the symbol table
            String actualType = expr.getType(symbolTable);

            // Compare actual return type with expected
            if (!actualType.equals(expectedType)) {
                System.err.println("Semantic Error: Return type mismatch. Expected " +
                        expectedType + " but got " + actualType + ".");
                return false;
            }
            // if everything matches — this return statement is valid
            return true;

        } catch (Exception e) {
            System.err.println("Unexpected error in ReturnStmtNode.validateTree(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasReturnValue() {
        return expr != null; // true if "Return <expr>"
    }


    @Override
    public boolean validateTree() {
        return false;
    }
}
