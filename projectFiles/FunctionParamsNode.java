package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;


public class FunctionParamsNode implements JottTree, FunctionDefNode{
    private String paramID;
    private String paramType;
    private static ArrayList<FunctionParamsNode> params;
    public FunctionParamsNode(String paramID, String paramType){
        this.paramID = paramID;
        this.paramType = paramType;
    }

    public String getParamID() {
        return paramID;
    }
    public String getParamType() {
        return paramType;
    }

    public static ArrayList<FunctionParamsNode> parseFunctionParams(ArrayList<Token> tokens) throws ParseException {
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            tokens.remove(0);
            return params;
        }
        else if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
            throw new ParseException("Invalid function parameters", -1);
        }
        else{
            String paramID = tokens.get(0).getToken();
            tokens.remove(0);
            if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.COLON){
                throw new ParseException("Type and ID must be seperated by a Colon", -1);
            }
            else{
                tokens.remove(0);
                if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
                    throw new ParseException("Invalid Function Parameter", -1);
                }
                else{
                    String paramType = tokens.get(0).getToken();
                    tokens.remove(0);
                    FunctionParamsNode node = new FunctionParamsNode(paramID, paramType);
                    params.add(node);
                    if (tokens.get(0).getTokenType() == TokenType.COMMA){
                        tokens.remove(0);
                        parseFunctionParams(tokens);
                    }
                }
            }
        }
        return params;
    }

    @Override
    public String convertToJott() {
        if(params.isEmpty()){
            return "";
        }
        else{
            StringBuilder str = new StringBuilder();
            for(FunctionParamsNode param : params){
                str.append(param.getParamID());
                str.append(":");
                str.append(param.getParamType());
                str.append(",");
            }
            str.setLength(str.length() - 1);
            return str.toString();
        }
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
