package projectFiles;

import java.text.ParseException;
import java.util.ArrayList;

public class StringNode implements JottTree{
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
            tokenList.remove(0);
            return new IDNode(string);
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

    @Override
    public String convertToJott() {
        return string;
    }
}

