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
        return false;
    }
}
