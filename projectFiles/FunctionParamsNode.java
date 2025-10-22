package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;


public class FunctionParamsNode implements JottTree {
    private ArrayList<IDNode> paramID;
    private static LinkedHashMap<IDNode, String> params;

    public FunctionParamsNode(LinkedHashMap<IDNode, String> params, ArrayList<IDNode> paramID) {
        this.paramID = paramID;
        this.params = params;
    }


    public static FunctionParamsNode parseFunctionParams(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        LinkedHashMap<IDNode, String> params = new LinkedHashMap<>();
        ArrayList<IDNode> paramID = new ArrayList<>();
        //handles  1 param
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            IDNode paramName = IDNode.parseIDNode(tokens);
            if(tokens.get(0).getTokenType() != TokenType.COLON){
                throw new ParserSyntaxError("Expected a Colon", tokens.get(0));
            }
            tokens.remove(0);
            String type = tokens.get(0).getToken();
            tokens.remove(0);
            paramID.add(paramName);
            params.put(paramName, type);

            //more than one param
            while(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
                if(tokens.get(0).getTokenType() != TokenType.COMMA){
                    throw new ParserSyntaxError("Expected a Comma", tokens.get(0));
                }
                tokens.remove(0);
                paramName =IDNode.parseIDNode(tokens);

                if(tokens.get(0).getTokenType() != TokenType.COLON){
                    throw new ParserSyntaxError("Expected a Colon", tokens.get(0));
                }
                tokens.remove(0);
                type = tokens.get(0).getToken();
                tokens.remove(0);
                paramID.add(paramName);
                params.put(paramName, type);
            }
        }
        return new FunctionParamsNode(params, paramID);
    }

    @Override
    public String convertToJott() {
        StringBuilder result = new StringBuilder();
        if(params.isEmpty()){
            return "";
        }
        else{
            Set<IDNode> keySet = params.keySet();
            for(IDNode key : keySet){
                result.append(key.convertToJott());
                result.append(":");
                result.append(params.get(key));
                result.append(",");
            }
            result.setLength(result.length()-1);
        }
        return result.toString();
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
