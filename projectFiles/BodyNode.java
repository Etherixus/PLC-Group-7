package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;


import java.util.ArrayList;

public class BodyNode implements JottTree {
    ArrayList<BodyStmtNode> bodyStmtNodes;
    ReturnStmtNode returnStmtNode;
    boolean validIfReturns;


    public BodyNode(ArrayList<BodyStmtNode> bodyStmtNodes, ReturnStmtNode returnStmtNode) {
        this.bodyStmtNodes = bodyStmtNodes;
        this.returnStmtNode = returnStmtNode;
        validIfReturns = false;
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

    public boolean hasReturn(){
        return returnStmtNode != null;
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
    public boolean validateTree(SymbolTable parentTable, String expectedReturnType) throws SemanticSyntaxError {

            // Create a local scope for this body
            SymbolTable localTable = new SymbolTable(parentTable);

            ArrayList<IfStmtNode> ifNodes = new ArrayList<>();
            // Validate each body statement in order
            for (BodyStmtNode stmt : bodyStmtNodes) {

                // Variable Declaration to symbol table
                if (stmt instanceof VarDecNode) {
                    VarDecNode varDec = (VarDecNode) stmt;
                    varDec.validateTree();
                    varDec.declare(localTable);
                }

                // Assignment
                else if (stmt instanceof AsmtNode) {
                    ((AsmtNode) stmt).validateTree(localTable);
                }

                // While Loop
                else if (stmt instanceof WhileLoopNode) {
                    ((WhileLoopNode) stmt).validateTree(localTable, expectedReturnType);
                }

                // Function Call
                else if (stmt instanceof FunctionCallNode) {
                    ((FunctionCallNode) stmt).validateTree(localTable);
                }
                else if (stmt instanceof IfStmtNode) {
                    ((IfStmtNode) stmt).validateTree(localTable, expectedReturnType);
                    ifNodes.add((IfStmtNode) stmt);
                }

                // Any Other Node Type
                else {
                    stmt.validateTree();
                }
            }

            for (IfStmtNode stmt : ifNodes) {
                if(stmt.hasValidReturn()){
                    validIfReturns = true;
                } else{
                    validIfReturns = false;
                    break;
                }
            }

            // Validate Return Statement
            if (returnStmtNode != null) {
                if (expectedReturnType.equals("Void") && returnStmtNode.hasReturnValue()) {
                    throw new SemanticSyntaxError(
                            "Cannot return a value from a Void function.", returnStmtNode.expr.getToken()
                    );
                }

                returnStmtNode.validateTree(localTable, expectedReturnType);
            }

            // if all nodes within the body are validated then return true
            return true;

    }

    @Override
    public boolean validateTree() {
        return false;
    }

    // Checks if this body contains a 'Return' statement that returns a value
    public boolean hasReturnValue() {
        // If no return statement exists, definitely false
        if (returnStmtNode == null) {
            return false;
        }
        // Delegate check to ReturnStmtNode (whether it has an expression)
        return returnStmtNode.hasReturnValue();
    }

    public boolean hasValidIfReturnPaths(){
        return validIfReturns;
    }

}
