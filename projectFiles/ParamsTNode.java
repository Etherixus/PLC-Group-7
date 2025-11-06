package projectFiles;

import provided.JottTree;
import provided.Token;

import java.util.ArrayList;

public class ParamsTNode implements JottTree {

    private ExpressionNode expression;

    public ParamsTNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public static ParamsTNode parseParamsTNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        return new ParamsTNode(ExpressionNode.parseExpressionNode(tokens));
    }


    @Override
    public String convertToJott() {
        return expression.convertToJott();
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
