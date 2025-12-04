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

    /* The default implementation simply returns {@code true} to avoid
     * breaking semantic analysis for unimplemented node types.
     * This should be overridden in all concrete {@code BodyStmtNode} subclasses.
     * </p>
            *
            * @param symbolTable the current scope’s symbol table
     * @return {@code true} if this statement is semantically valid; {@code false} otherwise
     */
    default boolean validateTree(SymbolTable symbolTable) throws SemanticSyntaxError {
        return true;
    }

    public Object execute();

}
