package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class MathOpNode implements JottTree {
    Token operation;

    public MathOpNode(Token operation){
        this.operation = operation;
    }

    public static MathOpNode parseMathOpNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() == TokenType.MATH_OP) {
            return new MathOpNode(tokens.get(0));
        } else {
            Token token = tokens.get(0);
            throw new ParserSyntaxError(
                    ParserSyntaxError.createParserSyntaxError("Expected Math Op but got " + tokens.get(0).getTokenType().toString(),
                            token.getFilename(), token.getLineNum()));
        }
    }

    @Override
    public String convertToJott() {
        return "";
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
