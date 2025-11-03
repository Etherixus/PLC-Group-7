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
        return number.getToken();
    }

    @Override
    public String convertToC() {
        return number.getToken();
    }

    @Override
    public String convertToPython() {
        return number.getToken();
    }

    @Override
    public boolean validateTree() {
        if (number == null) return false;
        if (number.getTokenType() != TokenType.NUMBER) return false;

        String tok = number.getToken();
        if (tok == null || tok.isEmpty()) return false;

        // Valid Tokens:
        //  - digits (e.g. 123)
        //  - digits.decimal (e.g. 12.34)
        //  - .digits (e.g. .5)
        // Disallow multiple decimals or standalone "." 
        return tok.matches("(\\d+(\\.\\d+)?|\\.\\d+)");
    }
    public boolean isNegative(){
        String t = number.getToken();
        if (t == null || t.isEmpty()) return false;
        return t.charAt(0) == '-';
    }
}
