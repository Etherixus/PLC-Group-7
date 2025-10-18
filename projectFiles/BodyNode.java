package projectFiles;

import org.w3c.dom.Node;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class BodyNode implements FBodyNode {
    ArrayList<BodyStmtNode> bodyStmtNodes;
    ReturnStmtNode returnStmtNode;
    private final boolean hasReturnStmt;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
        this.hasReturnStmt = true;
    }

    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = null;
        this.hasReturnStmt = false;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        // Check for opening brace if it doesn't exist throw an error
        if(tokens.get(0).getTokenType() == TokenType.L_BRACE){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a { but none was found",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }


        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        ReturnStmtNode returnStmtNode = null;
        boolean hasReturnStmt = false;
        // Ensure that what is in the body is a keyword, id, or function header
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD || tokens.get(0).getTokenType() == TokenType.FC_HEADER){
           // For every token that is a keyword or id, parse the node as a return stmt or a body stmt
            do{
                // If the keyword is return then parse for a return stmt and break, since there should be no body stmts
                // after the return
                if(tokens.get(0).getToken().equals("Return")){
                    tokens.remove(0);
                    returnStmtNode = ReturnStmtNode.parseReturnStmtNode(tokens);
                    hasReturnStmt = true;
                    break;
                }
                // Otherwise parse for body stmts and add each stmt to the list until there are no more body stmts
                BodyStmtNode bodyStmtNode = BodyStmtNode.parseBodyStmt(tokens);
                bodyStmtNodes.add(bodyStmtNode);
            } while (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD || tokens.get(0).getTokenType() == TokenType.FC_HEADER);
        } else {
            // If it is not a keyword, id or function header it can't possibly be a proper body, throw an error
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                "Expected a body statement, or return statement",
                tokens.get(0).getFilename(),
                tokens.get(0).getLineNum()));
        }

        // Check for the closing brace of the body, if one doesn't exist then throw an error
        if(tokens.get(0).getTokenType() == TokenType.R_BRACE){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a } but none was found",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }
        if(hasReturnStmt){
            return new BodyNode(bodyStmtNodes, returnStmtNode);
        } else {
            return new BodyNode(bodyStmtNodes);
        }
    }

    public boolean hasReturnStmt() {
        return hasReturnStmt;
    }

    @Override
    public String convertToJott(){
        StringBuilder jott = new StringBuilder("{\n");
        if(bodyStmtNodes != null && !bodyStmtNodes.isEmpty()) {
            for(BodyStmtNode bodyStmtNode : bodyStmtNodes) {
                jott.append(" ").append(bodyStmtNode.convertToJott()).append("\n");
            }
        }
        if(returnStmtNode != null) {
            jott.append(" ").append(returnStmtNode.convertToJott()).append("\n");
        }
        jott.append("}");
        return jott.toString();
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
