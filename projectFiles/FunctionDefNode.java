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
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE || tokens.isEmpty()){
            throw new ParserSyntaxError("Expected RBrace Right Bracket", tokens.get(0));
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
        String funcName = id.toString();  // reuse what you already have
        String retType = returnType.toString(); // simple way to get type text

        // Empty parameter list for now
        ArrayList<String> paramTypes = params.getParamTypes();
        Symbol funcSymbol = new Symbol(funcName, retType, paramTypes, -1); // empty param list
        globalTable.addSymbol(funcName, funcSymbol);

        System.out.println("Declared function: " + funcName + " returns " + retType);
    }

    @Override
    public boolean validateTree() { // Example of semantic validation inside the node
    try{
        // 1. Validate structure
        if (id == null || returnType == null || body == null) {
            System.err.println("Semantic Error: Incomplete function definition.");
            return false;
        }

        // 2. Build a symbol for this function
        String funcName = id.convertToJott();
        String retType = returnType.convertToJott();
        ArrayList<String> paramTypes = new ArrayList<>();

        // 3. Access the global symbol table from ProgramNode
        // (ProgramNode will pass it to children via SymbolTable.current)
        SymbolTable globalTable = SymbolTable.getCurrentTable();
        if (globalTable == null) {
            System.err.println("Semantic Error: No active symbol table for function validation.");
            return false;
        }

        // 4. Check if function is already declared
        if (globalTable.lookup(funcName) != null) {
            System.err.println("Semantic Error: Function '" + funcName + "' redeclared.");
            return false;
        }

        // 5. Declare this function in the global scope
        Symbol funcSymbol = new Symbol(funcName, retType, paramTypes, -1);
        globalTable.addSymbol(funcName, funcSymbol);
        System.out.println("Declared function: " + funcName + " returns " + retType);

        // 6. Create a function-local symbol table
        SymbolTable funcTable = new SymbolTable(globalTable);
        params.declareParams(funcTable);

        // 7. Validate its body (recursively)
        if (!body.validateTree(funcTable, retType)) {
            System.err.println("Semantic Error: Invalid body in function " + funcName);
            return false;
        }

        System.out.println("Validated function: " + funcName);
        return true;

    } catch (SemanticSyntaxError e) {
        System.err.println("Semantic Error: " + e.getMessage());
        return false;
    } catch (Exception e) {
        System.err.println("Unexpected Error in FunctionDefNode: " + e.getMessage());
        return false;
    }
    }
}
