package projectFiles;


import provided.Token;
import provided.TokenType;
import java.util.ArrayList;


public class IDNode implements OperandNode {
    private String keyword;

    public IDNode(String keyword) {
        this.keyword = keyword;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokenList) {
        if (tokenList == null || tokenList.isEmpty()) {
            return null; // nothing to parse
        }

        Token token = tokenList.get(0);

        if (token.getTokenType() == TokenType.ID_KEYWORD) {
            return new IDNode(token.getToken());
        }

        return null; // not an ID_KEYWORD token
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public OperandNode parseOperand() {
        return null;
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
