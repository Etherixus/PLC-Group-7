package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class AsmtNode implements BodyStmtNode {
    IDNode idNode;
    ExpressionNode expressionNode;

    public AsmtNode(IDNode idNode, ExpressionNode expressionNode) {
        this.idNode = idNode;
        this.expressionNode = expressionNode;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        IDNode idNode = IDNode.parseIDNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.ASSIGN){
            throw new ParserSyntaxError("Expected + but got: " + tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.SEMICOLON){
            throw new ParserSyntaxError("Expected ; but got: " + tokens.get(0));
        }
        tokens.remove(0);

        return new AsmtNode(idNode, expressionNode);
    }
    @Override
    public String convertToJott() {
        return idNode.convertToJott() + " = " + expressionNode.convertToJott() + ";";
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
