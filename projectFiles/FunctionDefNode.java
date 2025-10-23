package projectFiles;



import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionDefNode implements JottTree{
    private final IDNode id;
    private final FunctionParamsNode params;
    private final FunctionReturnNode returnType;
    private final BodyNode body;

    public FunctionDefNode(IDNode id, FunctionParamsNode params, FunctionReturnNode returnType, BodyNode Fbody) {
        this.id = id;
        this.params = params;
        this.returnType = returnType;
        this.body = Fbody;
    }

    public static FunctionDefNode parseFunctionDef(ArrayList<Token> tokens) throws ParseException, ParserSyntaxError {
        if(!tokens.get(0).getToken().equals("Def")){
            throw new ParserSyntaxError("Expected Def Keyword", tokens.get(0));
        }
        tokens.remove(0);

        IDNode funcName = IDNode.parseIDNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected Bracket Left Bracket", tokens.get(0));
        }
        tokens.remove(0);
        FunctionParamsNode funcParam = FunctionParamsNode.parseFunctionParams(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected Bracket Right Bracket", tokens.get(0));
        }
        tokens.remove(0);
        FunctionReturnNode funcReturn = FunctionReturnNode.parseFunctionReturn(tokens);
        if(tokens.get(0).getTokenType() != TokenType.COLON){
            throw new ParserSyntaxError("Expected Colon.", tokens.get(0));
        }
        tokens.remove(0);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected Bracket Left Bracket", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE || tokens.isEmpty()){
            throw new ParserSyntaxError("Expected RBrace Right Bracket", tokens.get(0));
        }
        tokens.remove(0);
        return new FunctionDefNode(funcName, funcParam, funcReturn, body);
    }

    @Override
    public String convertToJott() {
        String output = "Def";
        output += id.convertToJott();
        output += "[";
        output += params.convertToJott();
        output += "]";
        output += returnType.convertToJott();
        output += "{";
        output += body.convertToJott();
        output += "}";
        return output;

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
