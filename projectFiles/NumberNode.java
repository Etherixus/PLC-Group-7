package projectFiles;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class NumberNode extends ExpressionNode {
    Token number;
    String type;

    public NumberNode(Token number, String type) {
        this.number = number;
        this.type = type;
    }

    public static NumberNode parseNumberNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        Token currentToken = tokens.get(0);
        String type = null;
        if(currentToken.getTokenType() != TokenType.NUMBER){
            throw new ParserSyntaxError("Expected a Number, got: ", tokens.get(0));
        }
        else{
            if(currentToken.getToken().contains(".")){
                type = "Double";
                tokens.remove(0);
                return new NumberNode(currentToken, type);
            }
            else{
                type = "Integer";
                tokens.remove(0);
                return new NumberNode(currentToken, type);
            }
        }
    }

    @Override
    public String convertToJott() {
        return number.getToken();
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
}
