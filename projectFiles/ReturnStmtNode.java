package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;


import java.util.ArrayList;

public class ReturnStmtNode implements JottTree {
    ExpressionNode expr;

    public ReturnStmtNode(ExpressionNode expr){
        this.expr = expr;
    }

    public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("return")){
            throw new ParserSyntaxError("Expected Return, Got: ", tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.SEMICOLON){
            throw new ParserSyntaxError("Expected Semicolon, Got: ", tokens.get(0));
        }
        tokens.remove(0);
        return new ReturnStmtNode(expressionNode);
    }
    @Override
    public String convertToJott(){
        return "Return " + expr.convertToJott() + ";";
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
