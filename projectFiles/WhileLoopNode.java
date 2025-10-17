package projectFiles;

import provided.Token;

import java.text.ParseException;
import java.util.ArrayList;

public class WhileLoopNode implements BodyNode{
    ExpressionNode expressionNode;
    BodyNode bodyNode;

    public WhileLoopNode(ExpressionNode expressionNode, BodyNode bodyNode) {
        this.expressionNode = expressionNode;
        this.bodyNode = bodyNode;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
        return new WhileLoopNode(expressionNode, bodyNode);
    }

    @Override
    public String convertToJott(){
        return "While[" + expressionNode.convertToJott() + "]{";
    }
}
