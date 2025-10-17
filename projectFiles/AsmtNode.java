package projectFiles;

import provided.Token;

import java.text.ParseException;
import java.util.ArrayList;

public class AsmtNode implements BodyNode {
    IDNode idNode;
    ExpressionNode expressionNode;

    public AsmtNode(IDNode idNode, ExpressionNode expressionNode) {
        this.idNode = idNode;
        this.expressionNode = expressionNode;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws ParseException {
        IDNode idNode = IDNode.parseIDNode(tokens);
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        return new AsmtNode(idNode, expressionNode);
    }

    public String convertToJott() {
        return idNode.convertToJott() + " = " + expressionNode.convertToJott();
    }
}
