package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public interface FunctionDefNode extends JottTree{
    static FunctionDefNode parseFunctionDef(ArrayList<Token> tokens) throws ParseException, ParserSyntaxError {
        if(tokens.isEmpty()){
            throw new ParseException("No tokens given", -1);
        }
        else {
            if (tokens.get(0).getToken().equals("Def")) {
                throw new ParseException("Function definition expected", -1);
            }
            else {
                tokens.remove(0);
                if (tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
                    throw new ParseException("Function Def must be followed by ID", -1);
                }
                else {
                    IDNode.parseIDNode(tokens);
                    if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
                        throw new ParseException("Function Def must be followed by LBRACE", -1);
                    }
                    else{
                        tokens.remove(0);
                        FunctionParamsNode.parseFunctionParams(tokens);
                        if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.COLON) {
                            throw new ParseException("Function Params must be followed by COLON", -1);
                        }
                        else{
                            tokens.remove(0);
                            FunctionReturnNode.parseFunctionReturn(tokens);
                            if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
                                throw new ParseException("Function Return must be followed by LBracket", -1);
                            }
                            else{
                                tokens.remove(0);
                                FBodyNode.parseFBody(tokens);
                                if(tokens.isEmpty() || tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
                                    throw new ParseException("Function Body must be followed by RBracket", -1);
                                }
                                else{
                                    tokens.remove(0);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
