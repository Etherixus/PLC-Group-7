package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

import static provided.JottTokenizer.tokenize;

public class FunctionCallNode implements OperandNode, BodyStmtNode {
    private IDNode id;
    private ParamsNode params;

    public FunctionCallNode(IDNode id, ParamsNode params){
        this.id = id;
        this.params = params;
    }

    @Override
    public OperandNode parseOperand() {
        return null;
    }

    //Example given the tokens from the code of ::add[5, 2, 6];
    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        //check for function header (::)
        if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            tokens.remove(0);
            //check for function name (add)
            if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
                Token funcToken = tokens.get(0);
                tokens.remove(0);
                //check for l bracket ([)
                if (tokens.get(0).getTokenType() == TokenType.L_BRACKET) {
                    tokens.remove(0);
                    //check for parameters
                    ArrayList<Token> listOfParams = new ArrayList<>();
                    //until we reach a right bracket first we check if the current index is a number or an id (the bottom if statement),
                    //next interation will check if there is a comma, if there is a comma then we check ahead to see if there is a number or id
                    //if not then we throw and error
                    while(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
                        if (tokens.get(0).getTokenType() == TokenType.COMMA){
                            if (tokens.get(0).getTokenType() == TokenType.NUMBER || tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
                              tokens.remove(0);
                              continue;
                            }
                            else {
                                Token token = tokens.get(0);
                                throw new ParserSyntaxError(
                                        ParserSyntaxError.createParserSyntaxError("Expected number or ID but got " + tokens.get(0).getTokenType().toString(),
                                                token.getFilename(), token.getLineNum()));
                            }
                        }

                        if (tokens.get(0).getTokenType() == TokenType.NUMBER || tokens.get(0).getTokenType() == TokenType.ID_KEYWORD || tokens.get(0).getTokenType() == TokenType.STRING){
                            listOfParams.add(tokens.get(0));
                            tokens.remove(0);
                        }
                        else {
                            Token token = tokens.get(0);
                            throw new ParserSyntaxError(
                                    ParserSyntaxError.createParserSyntaxError("Expected number, ID, String but got " + tokens.get(0).getTokenType().toString(),
                                            token.getFilename(), token.getLineNum()));
                        }
                    }
                    //check for r bracket (])
                    if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
                        tokens.remove(0);
                        //check for r bracket (;)
                        if (tokens.get(0).getTokenType() == TokenType.SEMICOLON) {
                            ArrayList<Token> tempTokens = new ArrayList<>();
                            tempTokens.add(funcToken);
                            tokens.remove(0);
                            //if we get a valid node then we return it and store the name and the parameters
                            return new FunctionCallNode(IDNode.parseIDNode(tempTokens), ParamsNode.parseParamsNode(listOfParams));
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        return "::" + id.convertToJott() + "[" + params.convertToJott() + "];";
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
