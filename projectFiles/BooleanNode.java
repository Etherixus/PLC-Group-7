package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class BooleanNode implements JottTree {
    private boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public static BooleanNode parseBooleanNode(ArrayList<Token> tokenList) throws ParseException {
        if (tokenList == null || tokenList.isEmpty()) {
            throw new ParseException("Unexpected end of input: expected boolean value.", -1);
        }

        Token token = tokenList.get(0);

        // Expecting a BOOLEAN token type
        if (token.getTokenType() == TokenType.ID_KEYWORD && (token.getToken().equals("true") || token.getToken().equals("false"))){
            boolean val = Boolean.parseBoolean(token.getToken());
            tokenList.remove(0); // consume token
            return new BooleanNode(val);
        }

        // If it’s not a boolean token, throw an error
        throw new ParseException(
            "Invalid token '" + token.getToken() + "' at line " + token.getLineNum()
            + ": expected 'true' or 'false'.", -1
        );
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public String convertToJott() {
        return toString();
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
        return true;
    }
}

