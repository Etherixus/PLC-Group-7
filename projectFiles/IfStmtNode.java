package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class IfStmtNode implements BodyStmtNode{
    ExpressionNode expressionNode;
    BodyNode body;
    ArrayList<ElseIfNode> elseIfNodes;
    ElseNode elseNode;

    public IfStmtNode(ExpressionNode expressionNode, BodyNode body) {
        this.expressionNode = expressionNode;
        this.body = body;
    }

    public IfStmtNode(ExpressionNode expressionNode, BodyNode body, ElseNode elseNode) {
        this.expressionNode = expressionNode;
        this.body = body;
        this.elseNode = elseNode;
    }

    public IfStmtNode(ExpressionNode expressionNode, BodyNode body, ArrayList<ElseIfNode> elseIfNodes) {
        this.expressionNode = expressionNode;
        this.body = body;
        this.elseIfNodes = elseIfNodes;
    }

    public IfStmtNode(ExpressionNode expressionNode, BodyNode body, ArrayList<ElseIfNode> elseIfNodes, ElseNode elseNode) {
        this.expressionNode = expressionNode;
        this.body = body;
        this.elseIfNodes = elseIfNodes;
        this.elseNode = elseNode;
    }



    public static IfStmtNode parseIfStmtNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        if(tokens.get(0).getTokenType() == TokenType.L_BRACKET){
            tokens.remove(0);
        } else {
            throw new ParserSyntaxError(ParserSyntaxError.createParserSyntaxError(
                    "Expected a [ after an If but one was not found.",
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
        //Logic for ElseIf and Else
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
            if(tokens.get(0).getToken().equals("Elseif")){
                ArrayList<ElseIfNode> elseIfNodes = new ArrayList<>();
                do{
                    tokens.remove(0);
                    ElseIfNode elseIfNode = ElseIfNode.parseElseNode(tokens);
                    elseIfNodes.add(elseIfNode);
                }
                while(tokens.get(0).getToken().equals("Elseif"));
                if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD && tokens.get(0).getToken().equals("Else")){
                    tokens.remove(0);
                    ElseNode elseNode = ElseNode.parseElseNode(tokens);
                    return new IfStmtNode(expressionNode, body, elseIfNodes, elseNode);
                } else {
                    return new IfStmtNode(expressionNode, body, elseIfNodes);
                }
            } else if(tokens.get(0).getToken().equals("Else")){
                tokens.remove(0);
                ElseNode elseNode = ElseNode.parseElseNode(tokens);
                return new IfStmtNode(expressionNode, body, elseNode);
            } else {
                return new IfStmtNode(expressionNode, body);
            }

        } else {
            return new IfStmtNode(expressionNode, body);
        }

    }

    public String convertToJott() {
        StringBuilder jott = new StringBuilder("If[" + expressionNode.convertToJott() + "]" + body.convertToJott());
        if(elseIfNodes != null && !elseIfNodes.isEmpty()) {
            for(ElseIfNode elseIfNode : elseIfNodes) {
                jott.append("\n").append(elseIfNode.convertToJott());
            }
        }
        if(elseNode != null) {
            jott.append("\n").append(elseNode.convertToJott());
        }
        return jott.toString();
    }

}