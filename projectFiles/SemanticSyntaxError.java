package projectFiles;

import provided.Token;

public class SemanticSyntaxError extends RuntimeException {

    private final String message;
    private final String filename;
    private final int lineNum;

    /**
     * Constructor for semantic errors without token context.
     */
    public SemanticSyntaxError(String message) {
        super(message);
        this.message = message;
        this.filename = "unknown";
        this.lineNum = -1;
    }


    // constructor for semantic errors with token context
    public SemanticSyntaxError(String message, Token token) {
        super(message);
        this.message = message;
        this.filename = token.getFilename();
        this.lineNum = token.getLineNum();
    }


     // constructor for semantic errors with token context and optional immediate printing.
    public SemanticSyntaxError(String message, Token token, boolean print) {
        super(message);
        this.message = message;
        this.filename = token.getFilename();
        this.lineNum = token.getLineNum();

        if (print) {
            System.out.println(getMessage());
        }
    }

    @Override
    public String getMessage() {
        return "Semantic Error\n" +
                message + "\n" +
                filename + ".jott:" + lineNum;
    }

    public String getFilename() {
        return filename;
    }

    public int getLineNum() {
        return lineNum;
    }
}
