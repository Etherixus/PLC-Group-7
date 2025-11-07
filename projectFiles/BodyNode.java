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

    // validates every aspect of things inside of a body bode like variable, assignments, return statements, loops, etc
    public boolean validateTree(SymbolTable parentTable, String expectedReturnType) {
        try {
            // Create a local scope for this body
            SymbolTable localTable = new SymbolTable(parentTable);

            // Validate each body statement in order
            for (BodyStmtNode stmt : bodyStmtNodes) {

                // Variable Declaration to symbol table
                if (stmt instanceof VarDecNode) {
                    VarDecNode varDec = (VarDecNode) stmt;
                    try {
                        varDec.declare(localTable);
                    } catch (SemanticSyntaxError e) {
                        System.err.println("Semantic Error in variable declaration: " + e.getMessage());
                        return false;
                    }
                }

                // Assignment
                else if (stmt instanceof AsmtNode) {
                    if (!((AsmtNode) stmt).validateTree(localTable)) {
                        System.err.println("Semantic Error in assignment statement.");
                        return false;
                    }
                }

                // While Loop
                else if (stmt instanceof WhileLoopNode) {
                    if (!((WhileLoopNode) stmt).validateTree(localTable)) {
                        System.err.println("Semantic Error in while loop statement.");
                        return false;
                    }
                }

                // Function Call
                else if (stmt instanceof FunctionCallNode) {
                    if (!((FunctionCallNode) stmt).validateTree(localTable)) {
                        System.err.println("Semantic Error in function call statement.");
                        return false;
                    }
                }

                // Any Other Node Type
                else {
                    if (!stmt.validateTree()) {
                        System.err.println("Semantic Error in body statement: "
                                + stmt.getClass().getSimpleName());
                        return false;
                    }
                }
            }

            // Validate Return Statement
            if (returnStmtNode != null) {
                if (!returnStmtNode.validateTree(localTable, expectedReturnType)) {
                    System.err.println("Semantic Error: Return type mismatch in function (expected "
                            + expectedReturnType + ")");
                    return false;
                }
            } else {
                // Missing return for non-void functions
                if (!expectedReturnType.equals("Void")) {
                    System.err.println("Semantic Error: Missing return statement for non-void function.");
                    return false;
                }
            }

            // All statements validated successfully
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
