package projectFiles;

import provided.Token;
import provided.TokenType;
import java.text.ParseException;
import java.util.ArrayList;

public class IDNode implements OperandNode, FunctionDefNode {
    private String keyword;

    public IDNode(String keyword) {
        this.keyword = keyword;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokenList) throws ParseException {
        if (tokenList == null || tokenList.isEmpty()) {
            throw new ParseException("Unexpected end of input: expected an identifier.", -1);
        }

        Token token = tokenList.get(0);

        // Expecting an identifier token
        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            String keyword = token.getToken();
            tokenList.remove(0);
            return new IDNode(keyword);
        }

        // If the token isn't an identifier
        throw new ParseException(
            "Invalid token '" + token.getToken() + "' at line " + token.getLineNum()
            + ": expected an identifier (variable name).",
            token.getLineNum()
        );
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public String convertToJott() {
        return keyword;
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

    @Override
    public OperandNode parseOperand() {
        return null;
    }
}
