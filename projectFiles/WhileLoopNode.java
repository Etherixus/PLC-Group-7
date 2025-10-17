package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class WhileLoopNode implements BodyStmtNode{
    ExpressionNode expressionNode;
    BodyNode bodyNode;

    public WhileLoopNode(ExpressionNode expressionNode, BodyNode bodyNode) {
        this.expressionNode = expressionNode;
        this.bodyNode = bodyNode;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        ExpressionNode expressionNode;
        if(tokens.get(0).getTokenType() == TokenType.L_BRACKET){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a [ but none was found",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }

        expressionNode = ExpressionNode.parseExpressionNode(tokens);

        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a ] but none was found",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }
        BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

        return new WhileLoopNode(expressionNode, bodyNode);
    }

    @Override
    public String convertToJott(){
        return "While[" + expressionNode.convertToJott() + "]" + bodyNode.convertToJott();
    }
}
