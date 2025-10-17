package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public interface BodyStmtNode {

    public static BodyStmtNode parseBodyStmt(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            Token curToken = tokens.get(0);
            if(curToken.getToken().equals("If")){
                tokens.remove(0);
                return IfStmtNode.parseIfStmtNode(tokens);
            } else if(curToken.getToken().equals("While")){
                tokens.remove(0);
                return WhileLoopNode.parseWhileLoopNode(tokens);
            } else {
                try {
                    return AsmtNode.parseAsmtNode(tokens);
                } catch(Exception e){
                    bodyStmtError(tokens.get(0));
                    return null;
                }
            }
        } else if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            return FunctionCallNode.parseFunctionCallNode(tokens);
        } else {
            bodyStmtError(tokens.get(0));
            return null;
        }
    }

    public static void bodyStmtError(Token token) throws ParserSyntaxError{
        throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                "Expected an If, While, Assignment, or Function Call statement but none were found.",
                token.getFilename(),
                token.getLineNum()));
    }

    public String convertToJott();
}
