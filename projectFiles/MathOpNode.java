package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class MathOpNode extends ExpressionNode implements JottTree {
    private String operation;
    private Token token;

    public MathOpNode(String operation, Token token) {
        this.operation = operation;
        this.token = token;
    }

    @Override
    public Token getToken() {
        return token;
    }

    public static MathOpNode parseMathOpNode(ArrayList<Token> tokens) throws ParserSyntaxError{
       if(tokens.get(0).getTokenType() != TokenType.MATH_OP){
           throw new ParserSyntaxError("Expected +, *, /, -, but got: ", tokens.get(0));
       }
       else{
           Token t = tokens.remove(0);
           return new MathOpNode(t.getToken(), t);
       }
    }

    @Override
    public String convertToJott() {
        return operation;
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
        if (operation == null) {
            return false;
        }

        return operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/");
    }

    public String getType()
    {
        return "MathOp";
    }


    //to be used for expression validate function
    public String evaluate()
    {
        return operation;
    }

}
