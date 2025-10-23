package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionReturnNode implements JottTree {

    private String functionReturn;

    public FunctionReturnNode(String functionReturn) {
        this.functionReturn = functionReturn;
    }
    public static FunctionReturnNode parseFunctionReturn(ArrayList<Token> tokens) throws ParseException {
        String functionReturn = "";
        if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new ParseException("Expected valid Return Type", -1);
        }
        else{
            functionReturn = tokens.get(0).getToken();
            tokens.remove(0);
            return new FunctionReturnNode(functionReturn);
        }
    }

    @Override
    public String convertToJott() {
        return functionReturn;
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
