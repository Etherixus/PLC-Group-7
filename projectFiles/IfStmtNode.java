package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode{
    private ExpressionNode expressionNode;
    private BodyNode body;
    private ArrayList<ElseIfNode> elseIfNodes;
    private ElseNode elseNode;

    public IfStmtNode(ExpressionNode expressionNode, BodyNode body, ArrayList<ElseIfNode> elseIfNodes, ElseNode elseNode) {
        this.expressionNode = expressionNode;
        this.body = body;
        this.elseIfNodes = elseIfNodes;
        this.elseNode = elseNode;
    }

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("If")){
            throw new ParserSyntaxError("Expected If got:", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected [ got:", tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expressionNode1 = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected ] got:", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected { got:", tokens.get(0));
        }
        tokens.remove(0);
        BodyNode body1 = BodyNode.parseBodyNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE){
            throw new ParserSyntaxError("Expected } got:", tokens.get(0));
        }
        tokens.remove(0);
        ArrayList<ElseIfNode> elseIfNodes1 = new ArrayList<>();
        while (!tokens.isEmpty()
                && tokens.get(0).getTokenType() == TokenType.ID_KEYWORD
                && tokens.get(0).getToken().equals("Elseif")) {
            elseIfNodes1.add(ElseIfNode.parseElseIfNode(tokens));

        }
        ElseNode elseNode1 = null;
        if (!tokens.isEmpty()
                && tokens.get(0).getTokenType() == TokenType.ID_KEYWORD
                && tokens.get(0).getToken().equals("Else")) {
            elseNode1 = ElseNode.parseElseNode(tokens);
        }

        return new IfStmtNode(expressionNode1, body1, elseIfNodes1, elseNode1);

    }

    public String convertToJott() {
        String result = "If[";
        result += expressionNode.convertToJott();
        result += "]{";
        result += body.convertToJott();
        result += "}";
        for(ElseIfNode elseIfNode : elseIfNodes){
            result += elseIfNode.convertToJott();
        }
        if(elseNode != null){
            result += elseNode.convertToJott();
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

    public boolean validateTree(SymbolTable table, String expectedReturnType) throws SemanticSyntaxError {
        try {
            // Check the condition expression
            String condType = expressionNode.getType(table);
            Token t = null;
            if (expressionNode instanceof IDNode) {
                t = ((IDNode) expressionNode).getToken();
            }

            if (!condType.equals("Boolean")) {
                throw new SemanticSyntaxError("If-statement condition must be Boolean, got " + condType, t);
            }

            // Validate the main if-body
            if (!body.validateTree(table, expectedReturnType)) return false;

            // Validate all ElseIf nodes
            if (elseIfNodes != null) {
                for (ElseIfNode eif : elseIfNodes) {
                    if (!eif.validateTree(table, expectedReturnType)) return false;
                }
            }

            // Validate optional Else
            if (elseNode != null && !elseNode.validateTree(table, expectedReturnType)) return false;

            return true;

        } catch (SemanticSyntaxError e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Semantic Error\nUnexpected error: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean validateTree() {
        boolean checkForReturn = false;
        // expression and body must exist and validate
        if (expressionNode == null) return false;
        if (!expressionNode.validateTree()) return false;

        if (body == null) return false;
        if (!body.validateTree()) return false;
        // If the if stmt has a reutrn stmt set flag to check if all others have a return
        if (body.hasReturn()) checkForReturn = true;

        // validate any ElseIf nodes
        if (elseIfNodes != null) {
            for (ElseIfNode eif : elseIfNodes) {
                if (eif == null) return false;
                if (!eif.validateTree()) return false;
                // If any conditional node has a return they all must
                if (eif.hasReturn() && !checkForReturn) return false;
                else if (!eif.hasReturn() && checkForReturn) return false;
            }
        }

        // validate optional else node
        if (elseNode != null && !elseNode.validateTree()) return false;
        // If all other nodes have a return the else must as well
        if (elseNode.hasReturn() && !checkForReturn) return false;
        else if (!elseNode.hasReturn() && checkForReturn) return false;

        return true;
    }

}