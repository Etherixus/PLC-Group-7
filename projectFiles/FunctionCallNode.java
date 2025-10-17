package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

import static provided.JottTokenizer.tokenize;

public class FunctionCallNode implements OperandNode, BodyNode {
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
            //check for function name (add)
            if (tokens.get(1).getTokenType() == TokenType.ID_KEYWORD) {
                //check for l bracket ([)
                if (tokens.get(2).getTokenType() == TokenType.L_BRACKET) {
                    //check for parameters
                    int index = 3;
                    ArrayList<Token> listOfParams = new ArrayList<>();
                    //until we reach a right bracket first we check if the current index is a number or an id (the bottom if statement),
                    //next interation will check if there is a comma, if there is a comma then we check ahead to see if there is a number or id
                    //if not then we throw and error
                    while(tokens.get(index).getTokenType() != TokenType.R_BRACKET){
                        if (tokens.get(index).getTokenType() == TokenType.COMMA){
                            if (tokens.get(index + 1).getTokenType() == TokenType.NUMBER || tokens.get(index).getTokenType() == TokenType.ID_KEYWORD){
                              index += 1;
                              continue;
                            }
                            else {
                                Token token = tokens.get(index);
                                throw new ParserSyntaxError(
                                        ParserSyntaxError.createParserSyntaxError("Expected number or ID but got " + tokens.get(index).getTokenType().toString(),
                                                token.getFilename(), token.getLineNum()));
                            }
                        }

                        if (tokens.get(index).getTokenType() == TokenType.NUMBER || tokens.get(index).getTokenType() == TokenType.ID_KEYWORD){
                            listOfParams.add(tokens.get(index));
                            index += 1;
                        }
                        else {
                            Token token = tokens.get(index);
                            throw new ParserSyntaxError(
                                    ParserSyntaxError.createParserSyntaxError("Expected number or ID but got " + tokens.get(index).getTokenType().toString(),
                                            token.getFilename(), token.getLineNum()));
                        }
                    }
                    //check for r bracket (])
                    if (tokens.get(index + 1).getTokenType() == TokenType.R_BRACKET) {
                        //check for r bracket (;)
                        if (tokens.get(index + 2).getTokenType() == TokenType.SEMICOLON) {
                            ArrayList<Token> tempTokenList = new ArrayList<>();
                            tempTokenList.add(tokens.remove(0));
                            //if we get a valid node then we return it and store the name and the parameters
                            return new FunctionCallNode(IDNode.parseIDNode(tempTokenList), ParamsNode.parseParamsNode(listOfParams));
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        return "";
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
