package projectFiles;


import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionCallNode extends ExpressionNode implements BodyStmtNode {
    private IDNode id;
    private ParamsNode params;

    public FunctionCallNode(IDNode id, ParamsNode params){
        this.id = id;
        this.params = params;
    }


    //Example given the tokens from the code of ::add[5, 2, 6];
    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() != TokenType.FC_HEADER){
            throw new ParserSyntaxError("Expecting fc header, got: " + tokens.get(0));
        }
        tokens.remove(0);
        IDNode name = IDNode.parseIDNode(tokens);
        ParamsNode params = ParamsNode.parseParamsNode(tokens);

        return new FunctionCallNode(name, params);
    }

    @Override
    public String convertToJott() {
        return "::" + id.convertToJott() + "[" + params.convertToJott() + "]";
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
        if (id == null || params == null) return false;
        if (!id.validateTree()) return false;
        if (!params.validateTree()) return false;
        return true;
    }

}
