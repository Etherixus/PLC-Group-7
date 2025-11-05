package projectFiles;

import provided.JottTree;
import provided.Token;

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
        try{

            // 1. Create a Global symbol table because this is the start of semantic analysis
            // and it will be the parent of all other child symbol tables
            SymbolTable globalTable = new SymbolTable(null);

            // 2: Set this table as the current scope (for all child nodes)
            // FunctionDefNode and others can now access it using SymbolTable.getCurrentTable()
            SymbolTable.setCurrentTable(globalTable);

            // 3. Add built in functions such as print, concat, and length to the global symbol table
            ArrayList<String> any = new ArrayList<>();
            any.add("Any");
            globalTable.addSymbol("print", new Symbol("print", "Void", any, -1));
            globalTable.addSymbol("concat", new Symbol("concat", "String",
                    new ArrayList<>(java.util.List.of("String", "String")), -1));
            globalTable.addSymbol("length", new Symbol("length", "Integer",
                    new ArrayList<>(java.util.List.of("String")), -1));

            // 4: Validate each function (which declares itself)
            boolean allValid = true;

            // pass 1: declare all functions in global (no locals created here)
            for (FunctionDefNode f : functionsDefs) {
                f.declare(globalTable);
            }

            // pass 2: validate each function (creates its own local func scope)
            for (FunctionDefNode f : functionsDefs) {
                if (!f.validateTree()) allValid = false;
            }

            // Step 5: Check that main exists
            Symbol mainFunc = globalTable.lookup("main");
            if (mainFunc == null) {
                System.err.println("Semantic Error: Missing main[]:Void function.");
                allValid = false;
            } else if (!mainFunc.returnType.equals("Void")) {
                System.err.println("Semantic Error: main[] must return Void.");
                allValid = false;
            }

            if (allValid) {
                System.out.println("Semantic analysis completed successfully.");
            }
            return allValid;

        } catch (SemanticSyntaxError e) {
            System.err.println("Semantic Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected Error in ProgramNode: " + e.getMessage());
            return false;

        }

    }
}
