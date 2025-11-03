package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class RelOpNode extends ExpressionNode implements JottTree {

    String relOp;

    public RelOpNode(String relOp) {
        this.relOp = relOp;
    }

    public static RelOpNode parseRelOpNode(ArrayList<Token> tokenList) throws ParserSyntaxError {
        if(tokenList.get(0).getTokenType() != TokenType.REL_OP){
            throw new ParserSyntaxError("Expected <=, >=, >, <, ==, !=, but got: ", tokenList.get(0));
        }
        else{
            return new RelOpNode(tokenList.remove(0).getToken());
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
