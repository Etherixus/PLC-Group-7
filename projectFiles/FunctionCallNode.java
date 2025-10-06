package projectFiles;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

import static provided.JottTokenizer.tokenize;

public class FunctionCallNode implements OperandNode {
    private String name;
    private ArrayList<Token> args = new ArrayList<Token>();

    public FunctionCallNode(String name, ArrayList<Token> args){
        this.name = name;
        this.args = args;
    }

    @Override
    public OperandNode parseOperand() {
        return null;
    }

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            if (tokens.get(1).getTokenType() == TokenType.ID_KEYWORD) {
                if (tokens.get(2).getTokenType() == TokenType.L_BRACKET) {
                    int index = 3;
                    ArrayList<Token> params = new ArrayList<>();
                    while(tokens.get(index).getTokenType() != TokenType.R_BRACKET){
                        if (tokens.get(index).getTokenType() == TokenType.COMMA){
                            if (tokens.get(index + 1).getTokenType() == TokenType.NUMBER || tokens.get(index).getTokenType() == TokenType.ID_KEYWORD){
                              index += 1;
                              continue;
                            }
                            else {
                                Token token = tokens.get(index);
                                throw new ParserSyntaxError(
                                        ParserSyntaxError.createParserSyntaxError("Expected number or ID but got " + tokens.get(0).getTokenType().toString(),
                                                token.getFilename(), token.getLineNum()));
                            }
                        }

                        if (tokens.get(index).getTokenType() == TokenType.NUMBER || tokens.get(index).getTokenType() == TokenType.ID_KEYWORD){
                            params.add(tokens.get(index));
                        }
                    }
                    if (tokens.get(index + 1).getTokenType() == TokenType.R_BRACKET) {
                        if (tokens.get(index + 2).getTokenType() == TokenType.SEMICOLON) {
                            return new FunctionCallNode(tokens.get(1).getToken(), params);
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

    //todo remove before turning in
    public static void main(String[] args) throws ParserSyntaxError {
        System.out.println("hello");
        FunctionCallNode tokens = parseFunctionCallNode(tokenize("/Users/andrew/IdeaProjects/PLC-Group-7/tokenizerTestCases/functionCallNodeTest.jott"));

    }
}
