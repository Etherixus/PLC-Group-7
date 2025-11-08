package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class BooleanNode extends ExpressionNode implements JottTree {
    private boolean value;
    private Token token;

    public BooleanNode(boolean value, Token token) {
        this.value = value;
        this.token = token;
    }

    @Override
    public Token getToken(){
        return token;
    }

    public static BooleanNode parseBooleanNode(ArrayList<Token> tokenList) throws ParserSyntaxError {
        Token token;
        if(tokenList.get(0).getTokenType() == TokenType.ID_KEYWORD){
            if(tokenList.get(0).getToken().equals("True") || tokenList.get(0).getToken().equals("False")){
                if(tokenList.get(0).getToken().equals("True")){
                    token = tokenList.remove(0);
                    return new BooleanNode(true,token );
                }
                else{
                    token = tokenList.remove(0);
                    return new BooleanNode(false, token);
                }
            }
            else{
                throw new ParserSyntaxError("Expected True or False, got: ", tokenList.get(0));
            }
        }
        else{
            throw new ParserSyntaxError("Expected ID_KEYWORD, got: ", tokenList.get(0));
        }
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
        // The parser constructs a BooleanNode only when it sees the
        // keywords "True" or "False". At this stage the node stores
        // the boolean value itself; a simple sanity check is sufficient.
        // Ensure the stored value serializes to a canonical boolean string.
        String s = Boolean.toString(value);
        return "true".equals(s) || "false".equals(s);
    }
}

