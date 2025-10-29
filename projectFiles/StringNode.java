package projectFiles;

import provided.Token;
import provided.JottTree;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class StringNode implements JottTree {
    private String string;
    
    
    public StringNode(String string) {
        this.string = string;
    }

    public static StringNode parseStringNode(ArrayList<Token>tokenList) throws ParseException{
        if (tokenList == null || tokenList.isEmpty()) {
            throw new ParseException("Unexpected end of input: expected a String.", -1);
        }
         Token token = tokenList.get(0);

        // Expecting an identifier token
        if (token.getTokenType() == TokenType.STRING) {
            String string = token.getToken();

            // Validate that the token actually represents a valid string literal
            if (!isValidStringLiteral(string)) {
                throw new ParseException(
                        "Invalid string literal '" + string + "' at line " + token.getLineNum(),
                        token.getLineNum()
                );
            }

            tokenList.remove(0);
            return new StringNode(string);
        }

        // If the token isn't a String
        throw new ParseException(
            "Invalid token '" + token.getToken() + "' at line " + token.getLineNum()
            + ": expected a String.",
            token.getLineNum()
        );
    }
    @Override
    public String toString() {
        return string;
    }
    
    private static boolean isValidStringLiteral(String s) {
        if (s == null) return false;
        /*  Tokenizer produces strings that include the surrounding double quotes
        // Accept only if it starts and ends with a double quote and contains
         only letters, digits and spaces in between.*/
         
        if (s.length() < 2) return false;
        if (s.charAt(0) != '"' || s.charAt(s.length() - 1) != '"') return false;
        String inner = s.substring(1, s.length() - 1);
        for (char c : inner.toCharArray()) {
            if (!(Character.isLetter(c) || Character.isDigit(c) || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String convertToJava(String className) { return string; }

    @Override
    public String convertToC() { return string; }

    @Override
    public String convertToPython() { return string; }

    @Override
    public boolean validateTree() { return isValidStringLiteral(string); }

    @Override
    public String convertToJott() {
        return string;
    }
}

