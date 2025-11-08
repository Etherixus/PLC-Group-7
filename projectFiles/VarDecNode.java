package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class VarDecNode implements JottTree, BodyStmtNode {
    private String varType;
    private IDNode varName;

    public VarDecNode(String varType, IDNode varName) {
        this.varName = varName;
        this.varType = varType;
    }
    public static VarDecNode parseVarDecNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
            throw new ParserSyntaxError("Expected Id or keyword",tokens.get(0));
        }
        else {
            String type = tokens.get(0).getToken();
            if(!VariableTypes.isValidVariableType(type)){
                throw new ParserSyntaxError("Expected a valid variable type but got: " + type, tokens.get(0));
            }
            tokens.remove(0);
            IDNode name = IDNode.parseIDNode(tokens);
            if(tokens.get(0).getTokenType() != TokenType.SEMICOLON){
                throw new ParserSyntaxError("Expected Semicolon",tokens.get(0));
            }
            tokens.remove(0);
            return new VarDecNode(type, name);
        }
    }

    public void declare(SymbolTable table) throws SemanticSyntaxError {
        String variableName = varName.convertToJott();
        Token t = varName.getToken();

        // Check if this variable name already exists in the same scope
        if (table.lookup(variableName) != null) {
            throw new SemanticSyntaxError("Redeclaration of variable: " + variableName, t);
        }

        // Create a new symbol and register it in the symbol table
        Symbol symbol = new Symbol(variableName, this.varType, -1);
        symbol.returnType = this.varType;
        symbol.isFunction = false;
        table.addSymbol(variableName, symbol, t);
    }


    @Override
    public String convertToJott() {
        return this.varType + " " + this.varName.convertToJott() +";";
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

    /**
     * Performs semantic validation on this variable declaration.
     *
     * In the current implementation, this returns true because
     * the actual semantic work (declaring the symbol) happens
     * inside BodyNode.validateTree(), which calls declare().
     *
     * @return true (no internal semantic check is done here)
     */
    @Override
    public boolean validateTree() {
        if(!varName.toString().equals(varName.toString().toLowerCase())){
            throw new SemanticSyntaxError("Variable name must start with a lowercase", varName.getToken());
        }
        return true;
    }
}
