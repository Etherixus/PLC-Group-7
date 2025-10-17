package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class BodyNode {
    ArrayList<BodyStmtNode> bodyStmtNodes;
    ReturnStmtNode returnStmtNode;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
    }

    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes) {
        this.bodyStmtNodes = bodyStmtNodes;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        if(tokens.get(0).getTokenType() == TokenType.L_BRACE){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a { but none was found",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }
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
                    "Expected an body statement, or return statement",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }

        if(tokens.get(0).getTokenType() == TokenType.R_BRACE){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a } but none was found",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }
        return null;
    }


    public String convertToJott(){
        String jott = "{";
        //todo add the conversion here
        jott = jott + "}";
        return jott;
    }
}
