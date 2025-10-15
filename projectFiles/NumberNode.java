package projectFiles;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class NumberNode implements OperandNode {
    Token number;

    public NumberNode(Token number) {
        this.number = number;
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() == TokenType.NUMBER) {
            return new NumberNode(tokens.get(0));
        } else {
            Token token = tokens.get(0);
            throw new ParserSyntaxError(
                    ParserSyntaxError.createParserSyntaxError("Expected number but got " + tokens.get(0).getTokenType().toString(),
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

    public boolean isNegative(){
        return number.getToken().toCharArray()[0] == '-';
    }

    @Override
    public OperandNode parseOperand() {
        return null;
    }
}
