package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;


import java.util.ArrayList;

public class ElseIfNode implements JottTree {
    private ExpressionNode expressionNode;
    private BodyNode body;

    public ElseIfNode(ExpressionNode expressionNode, BodyNode body) {
        this.expressionNode = expressionNode;
        this.body = body;
    }

    public static ElseIfNode parseElseNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("Elseif")){
            throw new ParserSyntaxError("Expected Elseif got: ", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected [ got:", tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expression = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected ] got:", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected { got:", tokens.get(0));
        }
        tokens.remove(0);
        BodyNode body = BodyNode.parseBodyNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE){
            throw new ParserSyntaxError("Expected } got:", tokens.get(0));
        }
        tokens.remove(0);
        return new ElseIfNode(expression, body);
    }

    @Override
    public String convertToJott() {
        String result = "Elseif[";
        result += expressionNode.convertToJott();
        result += "]{";
        result += body.convertToJott();
        result += "}";
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
