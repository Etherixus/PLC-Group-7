package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class RelOpNode extends ExpressionNode implements JottTree {

    String relOp;
    Token token;

    public RelOpNode(String relOp, Token token) {
        this.relOp = relOp;
        this.token = token;
    }

    @Override
    public Token getToken() {
        return token;
    }

    public static RelOpNode parseRelOpNode(ArrayList<Token> tokenList) throws ParserSyntaxError {
        if(tokenList.get(0).getTokenType() != TokenType.REL_OP){
            throw new ParserSyntaxError("Expected <=, >=, >, <, ==, !=, but got: ", tokenList.get(0));
        }
        else{
            Token t = tokenList.remove(0);
            return new RelOpNode(t.getToken(), t);
        }
    }

    @Override
    public String convertToJott() {
        return relOp;
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
        if (relOp == null) return false;
        return relOp.equals("<=") || relOp.equals(">=") || relOp.equals(">") ||
                relOp.equals("<") || relOp.equals("==") || relOp.equals("!=");
    }
}
