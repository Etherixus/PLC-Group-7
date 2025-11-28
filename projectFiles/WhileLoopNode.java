package projectFiles;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class WhileLoopNode implements BodyStmtNode{
    ExpressionNode expressionNode;
    BodyNode bodyNode;

    public WhileLoopNode(ExpressionNode expressionNode, BodyNode bodyNode) {
        this.expressionNode = expressionNode;
        this.bodyNode = bodyNode;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        ExpressionNode expressionNode1;
        BodyNode bodyNode1;
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD && !tokens.get(0).getToken().equals("While")){
            throw new ParserSyntaxError("Expected While, got: " + tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected [, got: " + tokens.get(0));
        }
        tokens.remove(0);
        expressionNode1 = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected ], got: " + tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected {, got: " + tokens.get(0));
        }
        tokens.remove(0);
        bodyNode1 = BodyNode.parseBodyNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE){
            throw new ParserSyntaxError("Expected }, got: " + tokens.get(0));
        }
        tokens.remove(0);
        return new WhileLoopNode(expressionNode1, bodyNode1);
    }

    @Override
    public String convertToJott(){
        String output = "While[";
        output += expressionNode.convertToJott();
        output += "]{";
        output += bodyNode.convertToJott();
        output += "}";
        return output;
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
        // Check the while condition type
        String condType = expressionNode.getType(table);

        // get the token so we can get the line number
        Token t = expressionNode.getToken();


        if (!condType.equals("Boolean")) {
            throw new SemanticSyntaxError("While condition must be Boolean but got " + condType, t);
        }

        SymbolTable loopScope = new SymbolTable(table);

        if (!bodyNode.validateTree(loopScope, expectedReturnType)) {
            throw new SemanticSyntaxError("Invalid statements inside While loop body.", t);
        }

        // Everything passed
        return true;
    }

    @Override
    public boolean validateTree() {
        return false;
    }

    @Override
    public void execute() {
        //todo
    }
}
