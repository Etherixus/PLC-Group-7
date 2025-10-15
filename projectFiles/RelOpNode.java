package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class RelOpNode implements JottTree {

    String relOp;

    public RelOpNode(String relOp) {
        this.relOp = relOp;
    }

    public static RelOpNode parseRelOpNode(ArrayList<Token> tokenList) throws ParseException {
        if (tokenList == null || tokenList.isEmpty()) {
            throw new ParseException("Unexpected end of input: expected an identifier.", -1);
        }

        Token token = tokenList.get(0);

        // Expecting an Relation Operator token
        if (token.getTokenType() == TokenType.REL_OP) {
            String keyword = token.getToken();
            tokenList.remove(0);
            return new RelOpNode(keyword);
        }

        // If the token isn't a Relation Operator
        throw new ParseException(
                "Invalid token '" + token.getToken() + "' at line " + token.getLineNum()
                        + ": expected an identifier (variable name).",
                token.getLineNum()
        );
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
