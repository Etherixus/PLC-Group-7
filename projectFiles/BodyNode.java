package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;


import java.util.ArrayList;

public class BodyNode implements JottTree {
    ArrayList<BodyStmtNode> bodyStmtNodes;
    ReturnStmtNode returnStmtNode;


    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        ArrayList<BodyStmtNode> bodyStmtNodes = new ArrayList<>();
        while(!tokens.isEmpty() && ((tokens.get(0).getTokenType() == TokenType.ID_KEYWORD && !tokens.get(0).getToken().equals("Return"))
                || tokens.get(0).getTokenType() == TokenType.ASSIGN
                || tokens.get(0).getTokenType() == TokenType.FC_HEADER)){
            bodyStmtNodes.add(BodyStmtNode.parseBodyStmt(tokens));
        }

        ReturnStmtNode returnState = null;
        if(!tokens.isEmpty() && (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD && tokens.get(0).getToken().equals("Return"))){
            returnState = ReturnStmtNode.parseReturnStmtNode(tokens);
        }
        return new BodyNode(bodyStmtNodes, returnState);
    }


    @Override
    public String convertToJott(){
        String result = "";
        for(BodyStmtNode stmt: bodyStmtNodes){
            result += stmt.convertToJott();
            if(stmt instanceof FunctionCallNode){
                result += ";";
            }
        }
        if(returnStmtNode != null){
            result += returnStmtNode.convertToJott();
        }
        return result;
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
