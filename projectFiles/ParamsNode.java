package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class ParamsNode implements JottTree{
    private ArrayList<ExpressionNode> expressions = new ArrayList<>();

    public ParamsNode(ArrayList<ExpressionNode> expressions) {
        this.expressions = expressions;
    }

    public static ParamsNode parseParamsNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        ArrayList<ExpressionNode> expressions = new ArrayList<>();

        // Check if there are no params (ε)
        // e.g., ::func[]   -> immediately see R_BRACKET
        if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
            return new ParamsNode(expressions); // empty parameter list
        }

        // Otherwise, parse the first expression
        ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
        expressions.add(expr);
        // Parse any additional ", <expr>" sequences
        while (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.COMMA) {
            tokens.remove(0); // consume comma
            ExpressionNode nextExpr = ExpressionNode.parseExpressionNode(tokens);
            expressions.add(nextExpr);
        }

        return new ParamsNode(expressions);
    }

    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < expressions.size(); i++) {
            sb.append(expressions.get(i).convertToJott());
            if (i < expressions.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
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
