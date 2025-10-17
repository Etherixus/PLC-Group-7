package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class ElseIfNode {
    ExpressionNode expressionNode;
    BodyNode body;

    public ElseIfNode(ExpressionNode expressionNode, BodyNode body) {
        this.expressionNode = expressionNode;
        this.body = body;
    }

    public static ElseIfNode parseElseNode(ArrayList<Token> tokens) throws ParseException, ParserSyntaxError {
        if(tokens.get(0).getTokenType() == TokenType.L_BRACKET){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a [ after an Elseif but one was not found.",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()));
        }
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() == TokenType.R_BRACKET){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a ] after expression but one was not found.",
                    tokens.get(0).getFilename(),
                    tokens.get(0).getLineNum()
            ));
        }
        BodyNode body = BodyNode.parseBodyNode(tokens);
        return new ElseIfNode(expressionNode, body);
    }

    public String convertToJott() {
        return "ElseIf[" + expressionNode.convertToJott() + "]" + body.convertToJott();
    }
}
