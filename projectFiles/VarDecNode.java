package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class VarDecNode implements JottTree {
    private String varType;
    private String varName;

    public VarDecNode(String varType, String varName) {
        this.varName = varName;
        this.varType = varType;
    }
    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || tokens.isEmpty()){
            throw new ParseException("Unexpected end of input: Expected ID/Keyword",-1);
        }
        else{
            String type = tokens.get(0).getToken();
            tokens.remove(0);
            if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
                throw new ParseException("Unexpected end of input: expected following ID/Keyword.",-1);
            }
            else{
                String varName = tokens.get(0).getToken();
                tokens.remove(0);
                VarDecNode varDecNode = new VarDecNode(type, varName);
                return varDecNode;
            }
        }
    }

    @Override
    public String convertToJott() {
        return(varName + " " + varType) ;
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
