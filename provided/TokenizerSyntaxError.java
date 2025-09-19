package provided;

public class TokenizerSyntaxError extends Exception {

    public TokenizerSyntaxError(String message) {
        super(message);
    }

    /**
     * <pre>
     * Creates an error message in the following format
     * Syntax Error
     * Invalid token "{token}". {additional message}
     * {filename}:{line number}
     *</pre>
     * @param message any additional message to be added to the error
     * @param token the token that caused the error
     * @param fileName the name of the file the error occurred in
     * @param lineNum the line number the error occurred on
     *
     * @return the error message
     */
    public static String createTokenizerSyntaxErrorMessage(String message, String token, String fileName, int lineNum) {
        return "Syntax Error\n" +
                "Invalid token \""+ token +"\". " + message + "\n"
                + fileName + ":" + lineNum;
    }
}
