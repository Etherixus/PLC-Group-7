package projectFiles;



import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;

public class FunctionDefNode implements JottTree{
    private final IDNode id;
    private final FunctionParamsNode params;
    private final FunctionReturnNode returnType;
    private final BodyNode body;


    public FunctionDefNode(IDNode id, FunctionParamsNode params, FunctionReturnNode returnType, BodyNode Fbody) {
        this.id = id;
        this.params = params;
        this.returnType = returnType;
        this.body = Fbody;
    }

    public static FunctionDefNode parseFunctionDef(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(!tokens.get(0).getToken().equals("Def")){
            throw new ParserSyntaxError("Expected Def Keyword", tokens.get(0));
        }
        tokens.remove(0);

        IDNode funcName = IDNode.parseIDNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACKET){
            throw new ParserSyntaxError("Expected Bracket Left Bracket", tokens.get(0));
        }
        tokens.remove(0);
        FunctionParamsNode funcParam = FunctionParamsNode.parseFunctionParams(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACKET){
            throw new ParserSyntaxError("Expected Bracket Right Bracket", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.COLON){
            throw new ParserSyntaxError("Expected Colon.", tokens.get(0));
        }
        tokens.remove(0);
        FunctionReturnNode funcReturn = FunctionReturnNode.parseFunctionReturn(tokens);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected Bracket Left Bracket", tokens.get(0));
        }
        tokens.remove(0);
        BodyNode body = BodyNode.parseBodyNode(tokens);

        if (tokens.isEmpty()) {
            throw new ParserSyntaxError("Expected closing '}' before end of file in function definition.");
        }

        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new ParserSyntaxError("Expected '}', but got: " + tokens.get(0).getToken());
        }

        tokens.remove(0);

        return new FunctionDefNode(funcName, funcParam, funcReturn, body);
    }

    @Override
    public String convertToJott() {
        String output = "Def ";
        output += id.convertToJott();
        output += "[";
        output += params.convertToJott();
        output += "]:";
        output += returnType.convertToJott();
        output += "{";
        output += body.convertToJott();
        output += "}";
        return output;

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


    // Declares this function in the global symbol table.
    // Called from ProgramNode during the declaration pass.
    public void declare(SymbolTable globalTable) throws SemanticSyntaxError {
        String funcName = id.convertToJott();  // reuse what you already have
        String retType = returnType.convertToJott(); // simple way to get type text

        // Empty parameter list for now
        ArrayList<String> paramTypes = params.getParamTypes();

        // Add once to global
        globalTable.addSymbol(funcName, new Symbol(funcName, retType, paramTypes, -1));
    }

    @Override
    public boolean validateTree() {
        // Validate structure
        if (id == null || returnType == null || body == null) {
            throw new SemanticSyntaxError("Incomplete function definition.", id.getToken());
        }

        // Build a symbol for this function
        String funcName = id.convertToJott();
        String retType = returnType.convertToJott();

        // Access the global symbol table from ProgramNode
        // (ProgramNode will pass it to children via SymbolTable.current)
        SymbolTable globalTable = SymbolTable.getCurrentTable();
        if (globalTable == null) {
            throw new SemanticSyntaxError("No active symbol table for function validation.", id.getToken());
        }

        // Create a function-local symbol table
        SymbolTable funcTable = new SymbolTable(globalTable);

        // declare parameters in the local function scope
        params.declareParams(funcTable);

        // Special semantic rule: Void functions cannot return a value
        if (retType.equals("Void") && body.hasReturnValue()) {
            throw new SemanticSyntaxError("Cannot return a value from a Void function.", id.getToken());
        }

        // Validate its body node and everything in it (recursively)
        if (!body.validateTree(funcTable, retType)) {
            throw new SemanticSyntaxError("Invalid body in function " + funcName, id.getToken());
        }
        if(!retType.equals("Void") && !body.hasReturnValue()){
            throw new SemanticSyntaxError("Missing return statement for non-Void function: " + funcName, id.getToken());
        }

        return true;
    }
}
