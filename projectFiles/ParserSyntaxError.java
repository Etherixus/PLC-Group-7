package projectFiles;

import provided.Token;

public class ParserSyntaxError extends Exception {
    public ParserSyntaxError() {}

    // Constructor that accepts a message
    public ParserSyntaxError(String message)
    {
        super("Syntax Error:\n" + message);
    }

    public ParserSyntaxError(String message, Token token){
        super("Syntax Error:\n" + message);
        System.err.println("Syntax Error:\n" + message);
        System.err.println(token.getFilename() + ": " + token.getLineNum());
    }

    public ParserSyntaxError(String message, Token token, boolean print) {
        super("Syntax Error:\n" + message);
        if (print) {
            System.err.println("Syntax Error:\n" + message);
            System.err.println(token.getFilename() + ": " + token.getLineNum());
        }
    }
}
