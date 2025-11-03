package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class ParamsNode implements JottTree{
    ArrayList<ParamsTNode> params;

    public ParamsNode(ArrayList<ParamsTNode> params) {
        this.params = params;
    }

    public static ParamsNode parseParamsNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected [, but got: ", tokens.get(0));
        }
        tokens.remove(0);

        ArrayList<ParamsTNode> params = new ArrayList<ParamsTNode>();
        try{
            while(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
                params.add(ParamsTNode.parseParamsTNode(tokens));

                if(tokens.get(0).getTokenType() == TokenType.COMMA){
                    tokens.remove(0);
                }
            }
        }
        catch (IndexOutOfBoundsException e){
            throw new ParserSyntaxError("Expected ], but list of tokens ended");
        }

        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected ], but got: ", tokens.get(0));
        }
        tokens.remove(0);
        return new ParamsNode(params);
    }

    @Override
    public String convertToJott() {
        String result = "";
        for(int i = 0; i < params.size(); i++){
            result += params.get(i).convertToJott();

            if(i != params.size()-1){
                result += ",";
            }
        }
        return result;
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
        if (params == null) return false;
        if (params.size() == 0) return true;
        for (ParamsTNode p : params) {
            if (p == null) return false;
            if (p.getExpression() == null) return false;
            if (!p.getExpression().validateTree()) return false;
        }
        return true;
    }
}
