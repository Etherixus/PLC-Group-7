package projectFiles;

import provided.Token;

public class SemanticSyntaxError extends RuntimeException {

    private final String message;
    private final String filename;
    private final int lineNum;

    // constructor for semantic errors
    public SemanticSyntaxError(String message, Token token) {
        super(message);
        this.message = message;
        this.filename = token.getFilename();
        this.lineNum = token.getLineNum();
    }


    @Override
    public String getMessage() {
        String shortName = filename;
        if (shortName != null && shortName.contains("/")) {
            shortName = shortName.substring(shortName.lastIndexOf("/") + 1);
        }

        // Remove duplicated ".jott.jott" or double extensions
        if (shortName != null && shortName.endsWith(".jott.jott")) {
            shortName = shortName.replace(".jott.jott", ".jott");
        }

        return "Semantic Error\n" +
                message + "\n" +
                shortName + ":" + lineNum;
    }

    public String getFilename() {
        return filename;
    }

    public int getLineNum() {
        return lineNum;
    }
}
