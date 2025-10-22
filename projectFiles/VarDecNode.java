package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class VarDecNode implements JottTree, FBodyNode {
    private String varType;
    private IDNode varName;

    public VarDecNode(String varType, IDNode varName) {
        this.varName = varName;
        this.varType = varType;
    }
    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
            throw new ParserSyntaxError("Expected Id or keyword",tokens.get(0));
        }
        else {
            String type = tokens.get(0).getToken();
            tokens.remove(0);
            IDNode name = IDNode.parseIDNode(tokens);
            if(tokens.get(0).getTokenType() != TokenType.SEMICOLON){
                throw new ParserSyntaxError("Expected Semicolon",tokens.get(0));
            }
            tokens.remove(0);
            return new VarDecNode(type, name);
        }
    }

    @Override
    public String convertToJott() {
        return this.varType + " " + this.varName.convertToJott();
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
