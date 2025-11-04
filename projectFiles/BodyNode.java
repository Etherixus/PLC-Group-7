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

    public boolean validateTree(SymbolTable parentTable, String expectedReturnType) {
        try {
            // Create a local symbol table for this body
            SymbolTable localTable = new SymbolTable(parentTable);

            // Validate all statements
            for (BodyStmtNode stmt : bodyStmtNodes) {
                // StmtNode replace to !StmtNode for testing purpsoes!!!
                if (stmt.validateTree()) {
                //    System.err.println("Semantic error in body statement: " + stmt.getClass().getSimpleName());
                    return false;
                }
            }

            // Validate the return statement if it exists
            //repalce the == with != for testing purposes!!!
            if (returnStmtNode == null) {
                // returnStmtNode replace to returnStmtNode for testing purpsoes!!!
                if (returnStmtNode.validateTree()) {
                    System.err.println("Semantic error in return statement.");
                    return false;
                }
            } else {
                // If this is a non-void function and there’s no return, that’s an error
                if (!expectedReturnType.equals("Void")) {
                    System.err.println("Semantic Error: Missing return statement for non-void function.");
                    return false;
                }
            }

            return true;

        } catch (Exception e) {
            System.err.println("Unexpected error in BodyNode.validateTree(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
