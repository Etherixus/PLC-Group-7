package Unused;

import projectFiles.FunctionCallNode;
import projectFiles.IDNode;
import projectFiles.NumberNode;
import projectFiles.ParserSyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * This code is unused when the code parses down it was getting skipped and just calling the node directly
 *
public interface OperandNode extends JottTree {

    public OperandNode parseOperand();

    public static OperandNode parseOperand(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {

        if(tokens.isEmpty()) {
            return null;
        }

        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            return IDNode.parseIDNode(tokens);
        } else if(tokens.get(0).getTokenType() == TokenType.NUMBER) {
            return NumberNode.parseNumberNode(tokens);
        } else if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            return FunctionCallNode.parseFunctionCallNode(tokens);
        } else {
            return null;
        }
    }
}
 */