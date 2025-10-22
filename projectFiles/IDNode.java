package projectFiles;

import provided.Token;
import provided.TokenType;
import java.text.ParseException;
import java.util.ArrayList;

public class IDNode implements OperandNode{
    private String keyword;

    public IDNode(String keyword) {
        this.keyword = keyword;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokenList) throws ParserSyntaxError {
        if (tokenList.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new ParserSyntaxError("Expected Id or keyword", tokenList.get(0));
        }
        String key = tokenList.get(0).getToken();
        tokenList.remove(0);
        return new IDNode(key);
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
