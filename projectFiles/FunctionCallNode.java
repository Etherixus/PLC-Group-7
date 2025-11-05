package projectFiles;


import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionCallNode extends ExpressionNode implements BodyStmtNode {
    private IDNode id;
    private ParamsNode params;

    public FunctionCallNode(IDNode id, ParamsNode params){
        this.id = id;
        this.params = params;
    }


    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        if(tokens.get(0).getTokenType() != TokenType.FC_HEADER){
            throw new ParserSyntaxError("Expecting fc header, got: " + tokens.get(0));
        }
        tokens.remove(0);
        IDNode name = IDNode.parseIDNode(tokens);
        ParamsNode params = ParamsNode.parseParamsNode(tokens);

        return new FunctionCallNode(name, params);
    }
    public String getReturnType(SymbolTable table) throws SemanticSyntaxError {
        String funcName = id.convertToJott();
        Symbol sym = table.lookup(funcName);
        if (sym == null) {
            throw new SemanticSyntaxError("Undeclared function: " + funcName);
        }

        return sym.returnType;
    }

    public boolean validateTree(SymbolTable table) {
        try {
            String funcName = id.convertToJott();

            // Lookup function in the symbol table chain
            Symbol funcSymbol = table.lookup(funcName);
            if (funcSymbol == null) {
                System.err.println("Semantic Error: Undeclared function '" + funcName + "'");
                return false;
            }

            // Must be a function, not a variable
            if (!funcSymbol.isFunction) {
                System.err.println("Semantic Error: '" + funcName + "' is not a function.");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("Unexpected error in FunctionCallNode.validateTree(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String convertToJott() {
        return "::" + id.convertToJott() + "[" + params.convertToJott() + "]";
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
        return id != null && params != null && id.validateTree() && params.validateTree();
    }

}
