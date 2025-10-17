package projectFiles;

import provided.Token;
import provided.TokenType;

import java.beans.Expression;
import java.text.ParseException;
import java.util.ArrayList;

public class ReturnStmtNode {
    ExpressionNode expr;

    public ReturnStmtNode(ExpressionNode expr){
        this.expr = expr;
    }

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws ParseException {
        ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
        return new ReturnStmtNode(expr);
    }

    public String convertToJott(){
        return "Return " + expr.convertToJott();
    }
}
