package projectFiles;

import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class AsmtNode implements BodyStmtNode {
    IDNode idNode;
    ExpressionNode expressionNode;


    public AsmtNode(IDNode idNode, ExpressionNode expressionNode) {
        this.idNode = idNode;
        this.expressionNode = expressionNode;
    }

    public static AsmtNode parseAsmtNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        IDNode idNode = IDNode.parseIDNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.ASSIGN){
            throw new ParserSyntaxError("Expected + but got: " + tokens.get(0));
        }
        tokens.remove(0);
        ExpressionNode expressionNode = ExpressionNode.parseExpressionNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.SEMICOLON){
            throw new ParserSyntaxError("Expected ; but got: " + tokens.get(0));
        }
        tokens.remove(0);

        return new AsmtNode(idNode, expressionNode);
    }
    @Override
    public String convertToJott() {
        return idNode.convertToJott() + " = " + expressionNode.convertToJott() + ";";
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
        // This version isn't used for actual validation.
        // The real logic is in validateTree(SymbolTable) below.
        return false;
    }

    @Override
    public boolean validateTree(SymbolTable table) {
        try {
            // Get variable name
            String varName = idNode.convertToJott();
            // Lookup variable in current scope
            Symbol sym = table.lookup(varName);
            // get token for error processing
            Token t = idNode.getToken();

            // Check variable exists
            if (sym == null) {
                throw new SemanticSyntaxError("Undeclared variable " + varName, t);
            }

            // Get type of right-hand expression
            String exprType = expressionNode.getType(table);

            // Mark variable as initialized
            table.markInitialized(varName);

            // check type mismatch
            if (!sym.returnType.equals(exprType)) {
                throw new SemanticSyntaxError("Variable " + varName + " has undefined type.", t);
            }
            if (sym.returnType == null) {
                throw new SemanticSyntaxError(
                        "Type mismatch in assignment: " + varName +
                                " is " + sym.returnType + " but expression is " + exprType, t);
            }
            // If all checks pass, the assignment is valid
            return true;

        } catch (SemanticSyntaxError e) {
            System.err.println("Semantic Error: " + e.getMessage());
            return false;

        } catch (Exception e) {
            System.out.println("Semantic Error\nUnexpected error: " + e.getMessage());
            return false;
        }
    }
}
