package projectFiles;

import provided.Token;

public class SemanticSyntaxError extends Exception {
    public SemanticSyntaxError() {}

    public SemanticSyntaxError(String message)
    {
        super("Semantic Error:\n" + message);
    }

    public SemanticSyntaxError(String message, Token token){
        super("Semantic Error:\n" + message);
        System.err.println("Semantic Error:\n" + message);
        System.err.println(token.getToken() + ": " + token.getLineNum());
    }

    public SemanticSyntaxError(String message, Token token, boolean print) {
        super("Semantic Error:\n" + message);
        if (print) {
            System.err.println("Semantic Error:\n" + message);
            System.err.println(token.getToken() + " : " + token.getLineNum());
        }
    }
}
