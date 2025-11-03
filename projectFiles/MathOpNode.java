package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class MathOpNode extends ExpressionNode implements JottTree {
    private String operation;

    public MathOpNode(String operation){
        this.operation = operation;
    }

    public static MathOpNode parseMathOpNode(ArrayList<Token> tokens) throws ParserSyntaxError{
       if(tokens.get(0).getTokenType() != TokenType.MATH_OP){
           throw new ParserSyntaxError("Expected +, *, /, -, but got: ", tokens.get(0));
       }
       else{
           return new MathOpNode(tokens.remove(0).getToken());
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
<<<<<<< HEAD
        if (operation == null) return false;
=======
        if (operation == null) {
            return false;
        }
>>>>>>> f6d656fb4244936a37b9ba2ca77d6fa67b47d064
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
