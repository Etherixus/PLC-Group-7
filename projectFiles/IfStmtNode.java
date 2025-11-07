package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode{
    private ExpressionNode expressionNode;
    private BodyNode body;
    private ArrayList<ElseIfNode> elseIfNodes;
    private ElseNode elseNode;

    public IfStmtNode(ExpressionNode expressionNode, BodyNode body, ArrayList<ElseIfNode> elseIfNodes, ElseNode elseNode) {
        this.expressionNode = expressionNode;
        this.body = body;
        this.elseIfNodes = elseIfNodes;
        this.elseNode = elseNode;
    }

    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("If")){
            throw new ParserSyntaxError("Expected If got:", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected [ got:", tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expressionNode1 = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected ] got:", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected { got:", tokens.get(0));
        }
        tokens.remove(0);
        BodyNode body1 = BodyNode.parseBodyNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE){
            throw new ParserSyntaxError("Expected } got:", tokens.get(0));
        }
        tokens.remove(0);
        ArrayList<ElseIfNode> elseIfNodes1 = new ArrayList<>();
        while(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD && tokens.get(0).getToken().equals("Elseif")){
            elseIfNodes1.add(ElseIfNode.parseElseNode(tokens));
        }
        ElseNode elseNode1 = ElseNode.parseElseNode(tokens);
        return new IfStmtNode(expressionNode1, body1, elseIfNodes1, elseNode1);
    }

    public String convertToJott() {
        String result = "If[";
        result += expressionNode.convertToJott();
        result += "]{";
        result += body.convertToJott();
        result += "}";
        for(ElseIfNode elseIfNode : elseIfNodes){
            result += elseIfNode.convertToJott();
        }
        if(elseNode != null){
            result += elseNode.convertToJott();
        }
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
        // expression and body must exist and validate
        if (expressionNode == null) return false;
        if (!expressionNode.validateTree()) return false;

        if (body == null) return false;
        if (!body.validateTree()) return false;

        // validate any ElseIf nodes
        if (elseIfNodes != null) {
            for (ElseIfNode eif : elseIfNodes) {
                if (eif == null) return false;
                if (!eif.validateTree()) return false;
            }
        }

        // validate optional else node
        if (elseNode != null && !elseNode.validateTree()) return false;

        return true;
    }

}