package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class ProgramNode implements JottTree {
    ArrayList<FunctionDefNode> functionsDefs;

    public ProgramNode(ArrayList<FunctionDefNode> functionsDefs) {
        this.functionsDefs = functionsDefs;
    }

    public static ProgramNode parseProgram(ArrayList<Token> tokens) throws ParserSyntaxError {
        ArrayList<FunctionDefNode> functionsDefs = new ArrayList<>();
        while (!tokens.isEmpty()) {
            functionsDefs.add(FunctionDefNode.parseFunctionDef(tokens));
        }
        return new ProgramNode(functionsDefs);
    }

    @Override
    public String convertToJott() {
        String output = "";
        for (FunctionDefNode functionDef : functionsDefs) {
            output += functionDef.convertToJott();
        }
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

    @Override
    public boolean validateTree(){

            // Create a Global symbol table because this is the start of semantic analysis
            // and it will be the parent of all other child symbol tables
            SymbolTable globalTable = new SymbolTable(null);

            // Set this table as the current scope (for all child nodes)
            // FunctionDefNode and others can now access it using SymbolTable.getCurrentTable()
            SymbolTable.setCurrentTable(globalTable);

            // Add built in functions such as print, concat, and length to the global symbol table
            ArrayList<String> any = new ArrayList<>();
            any.add("Any");
            globalTable.addSymbol("print", new Symbol("print", "Void", any, -1));
            globalTable.addSymbol("concat", new Symbol("concat", "String",
                    new ArrayList<>(java.util.List.of("String", "String")), -1));
            globalTable.addSymbol("length", new Symbol("length", "Integer",
                    new ArrayList<>(java.util.List.of("String")), -1));

            // Validate each function (which declares itself)
            boolean allValid = true;

            // pass 1: declare all functions in global (no locals created here)
            for (FunctionDefNode f : functionsDefs) {
                f.declare(globalTable);
            }

            // pass 2: validate each function (creates its own local func scope)
            for (FunctionDefNode f : functionsDefs) {
                if (!f.validateTree()) allValid = false;
            }


            Symbol mainFunc = globalTable.lookup("main");
            // Check that main exists
            if (mainFunc == null) {
                throw new SemanticSyntaxError(
                        "Missing main[]:Void function.",
                        new Token("main", "main.jott", -1, TokenType.ID_KEYWORD)
                );
                // check main should only be returning void
            } else if (!mainFunc.returnType.equals("Void")) {
                throw new SemanticSyntaxError(
                        "main[] must return Void.",
                        new Token("main", "main.jott", -1, TokenType.ID_KEYWORD)
                );
                // main must not have any parameters
            } else if (mainFunc.paramTypes != null && !mainFunc.paramTypes.isEmpty()) {
                throw new SemanticSyntaxError(
                        "main[] must not have parameters.",
                        new Token("main", "main.jott", -1, TokenType.ID_KEYWORD)
                );
            }

            // if everything is valid then return true
            return allValid;

    }

    public void execute(){
        for (FunctionDefNode f : functionsDefs) {
            if (f.getName().equals("main")) {
                f.execute();
                return;
            }
        }
    }
}
