package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionDefNode implements JottTree{
    private final IDNode id;
    private final ArrayList<FunctionParamsNode> params;
    private final FunctionReturnNode returnType;
    private final FBodyNode body;
    public FunctionDefNode(IDNode id, ArrayList<FunctionParamsNode> params, FunctionReturnNode returnType, FBodyNode body) {
        this.id = id;
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public static FunctionDefNode parseFunctionDef(ArrayList<Token> tokens) throws ParseException, ParserSyntaxError {
        if(tokens.isEmpty()){
            throw new ParseException("No tokens given", -1);
        }
        else {
            if (!tokens.get(0).getToken().equals("Def")) {
                throw new ParseException("Function definition expected", -1);
            }
            else {
                tokens.remove(0);
                if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
                    throw new ParseException("Function Def must be followed by ID", -1);
                }
                else {
                    IDNode id = IDNode.parseIDNode(tokens);
                    if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
                        throw new ParseException("Function Def must be followed by LBracket", -1);
                    }
                    else{
                        tokens.remove(0);
                        ArrayList<FunctionParamsNode> fparams = FunctionParamsNode.parseFunctionParams(tokens);
                        //if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
                            //throw new ParseException("Function Params must be followed by R_BRACKET", -1);
                        //}
                        if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.COLON) {
                            throw new ParseException("Function Params must be followed by COLON", -1);
                        }
                        else{
                            tokens.remove(0);
                            FunctionReturnNode fReturn = FunctionReturnNode.parseFunctionReturn(tokens);
                            if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_BRACE) {
                                throw new ParseException("Function Return must be followed by LBrace", -1);
                            }
                            else{
                                tokens.remove(0);
                                FBodyNode fBody = FBodyNode.parseFBody(tokens);
                                if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_BRACE) {
                                    throw new ParseException("Function Body must be followed by RBrace", -1);
                                }
                                else{
                                    tokens.remove(0);
                                    return new FunctionDefNode(id, fparams, fReturn, fBody);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();

        sb.append("Def ");
        sb.append(id.convertToJott());
        sb.append("[");

        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                sb.append(params.get(i).convertToJott());
                if (i < params.size() - 1) {
                    sb.append(",");
                }
            }
        }

        sb.append("]:");
        sb.append(returnType.convertToJott());
        //sb.append("{");
        sb.append(body.convertToJott());
        //sb.append("}");

        return sb.toString();
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
