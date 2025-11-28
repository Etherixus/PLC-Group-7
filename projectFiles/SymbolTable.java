package projectFiles;

import provided.Token;

import java.util.HashMap;

public class SymbolTable {
    private final HashMap<String, Symbol> symbols = new HashMap<>();
    private SymbolTable parent;
    private static SymbolTable currentTable;

    // Constructor for creating a new scope.
    // SymbolTable global = new SymbolTable(null); (global scope)
    // SymbolTable funcScope = new SymbolTable(global); (inside of a function)
    public SymbolTable(SymbolTable parent){
        this.parent = parent;
    }

    // allows access the global table from inside nodes
    public static SymbolTable getCurrentTable() {
        return currentTable;
    }

    // sets the current symbol table during semantic analysis
    public static void setCurrentTable(SymbolTable table) {
        currentTable = table;
    }

    public SymbolTable getParent() {
        return parent;
    }



    // Adds a new symbol (variable or function) to the current scope.
    public void addSymbol(String name, Symbol symbol, Token token) throws SemanticSyntaxError {
        if (symbols.containsKey(name)) {
            Symbol existing = symbols.get(name);
            throw new SemanticSyntaxError("Redeclaration of symbol" + name +
                    "' (previously declare on line " + existing.lineNum + ")", token);
        }
        symbols.put(name, symbol);
    }

    public void addSymbol(String name, Symbol symbol) throws SemanticSyntaxError {
        addSymbol(name, symbol, null);  // just forwards with null token
    }

    // Looks up a symbol by name in this scope or its parent scopes.
    // Lookup Order
    // 1. Current Scope (for local variables)
    // 2. Parent Scope (for variables in parent scope or global func/vars
    // returns the symbol if found or null if not declared
    public Symbol lookup(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null) return symbol;
        if (parent != null) return parent.lookup(name);
        return null;
    }



     // Marks a variable as initialized after assignment.
     // Safe no-op for functions (ignored if isFunction == true).
    public void markInitialized(String name) {
        Symbol s = lookup(name);
        if (s != null && !s.isFunction) {
            s.initialized = true;
        }
    }

    //Checks whether a variable was initialized before being used.
    //Throws a SemanticError if used before initialization.
    public void checkInitialized(String name, Token token) throws SemanticSyntaxError {
        Symbol s = lookup(name);
        if (s == null) {
            throw new SemanticSyntaxError("Undeclared variable: '" + name + "'", token);
        }

        if (!s.isFunction && !s.initialized) {
            throw new SemanticSyntaxError(
                    "Variable '" + name + "' used before initialization",
                    token
            );
        }
    }

}
