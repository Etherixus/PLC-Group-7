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
            Token number = tokens.remove(0);
            return new NumberNode(number);
        } else {
            Token token = tokens.get(0);
            throw new ParserSyntaxError(
                    ParserSyntaxError.createParserSyntaxError("Expected number but got " + tokens.get(0).getTokenType().toString(),
                            token.getFilename(), token.getLineNum()));
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

        // Valid formats (tokenizer allows):
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

    @Override
    public OperandNode parseOperand() {
        return null;
    }
}
