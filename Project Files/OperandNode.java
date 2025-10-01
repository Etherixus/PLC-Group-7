import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public interface OperandNode extends JottTree {

    public OperandNode parseOperand();

    public static OperandNode parseOperand(ArrayList<Token> tokens) {

        if(tokens.isEmpty()) {
            return null;
        }

        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            return parseIDNode(tokens);
        } else if(tokens.get(0).getTokenType() == TokenType.NUMBER) {
            return parseNumberNode(tokens);
        } else if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            return parseFunctionCallNode(tokens);
        } else {
            return null;
        }
    }
}