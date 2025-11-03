package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public interface BodyStmtNode extends JottTree {

    static BodyStmtNode parseBodyStmt(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD && tokens.get(1).getTokenType() == TokenType.ASSIGN) {
            return AsmtNode.parseAsmtNode(tokens);
        }
        if(tokens.get(0).getTokenType() == TokenType.FC_HEADER) {
            FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
            if(tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
                throw new ParserSyntaxError("Expecting semicolon got: ", tokens.get(0));
            }
            tokens.remove(0);
            return functionCallNode;
        }
        if(tokens.get(0).getToken().equals("While")) {
            return WhileLoopNode.parseWhileLoopNode(tokens);
        }
        if(tokens.get(0).getToken().equals("If")) {
            return IfStmtNode.parseIfStmtNode(tokens);
        }
        //must be vardec then, if not chuck an error
        return VarDecNode.parseVarDecNode(tokens);
    }

    public String convertToJott();
}
