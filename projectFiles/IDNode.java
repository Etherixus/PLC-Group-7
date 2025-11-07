package projectFiles;

import provided.Token;
import provided.TokenType;

import java.util.ArrayList;

public class IDNode extends ExpressionNode {
    private String keyword;

    public IDNode(String keyword) {
        this.keyword = keyword;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokenList) throws ParserSyntaxError {
        if (tokenList.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new ParserSyntaxError("Expected ID or Keyword", tokenList.get(0));
        }
        String key = tokenList.get(0).getToken();
        tokenList.remove(0);
        return new IDNode(key);
    }

    public String getType(SymbolTable table) throws SemanticSyntaxError {
        // gets the symbol from the table by the node's keyword
        Symbol sym = table.lookup(keyword);
        if (sym == null) {
            throw new SemanticSyntaxError("Undeclared identifier: " + keyword);
        }

        // If both sym.type and sym.returnType are available, use sym.type first
        // because it’s the more appropriate one for this situation.
        if (sym.type != null) return sym.type;
        if (sym.returnType != null) return sym.returnType;

        // Defensive fallback (should rarely trigger)
        throw new SemanticSyntaxError("Unknown type for identifier: " + keyword);
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public String convertToJott() {
        return keyword;
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
        if (keyword == null) return false;
        // Basic identifier check: starts with a letter, followed by letters/digits/underscores
        return keyword.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }

}
