package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public interface BodyNode {

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            Token curToken = tokens.get(0);
            if(curToken.getToken().equals("Return")){
                ArrayList<Token> restOfTokens = (ArrayList<Token>) tokens.subList(1, tokens.size());
                return parseReturnStmt(restOfTokens);
            } else if(curToken.getToken().equals("If")){

            } else if(curToken.getToken().equals("While")){

            } else {

            }
        } else if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            return FunctionCallNode.parseFunctionCallNode(tokens);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected an If, While, Assignment, Function Call, or return statement",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }
        return null;
    }

    public static BodyNode parseReturnStmt(ArrayList<Token> tokens) throws ParseException {
        return ExpressionNode.parseExpressionNode(tokens);
    }

    public String convertToJott();
}
