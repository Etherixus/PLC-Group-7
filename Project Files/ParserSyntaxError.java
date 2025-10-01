public class ParserSyntaxError extends Exception {
    public ParserSyntaxError(String message) {
        super(message);
    }

    /**
     * <pre>
     * Creates an error message in the following format
     *
     * Syntax Error
     * {message}
     * {filename}:{line}
     * </pre>
     * @param message the message added to the error
     * @param filename the name of the file where the error occurred
     * @param line the line that the error occurred on
     * @return the message
     */
    public static String createParserSyntaxError(String message, String filename, int line) {
        return "Syntax Error\n" +
                message + "\n" +
                filename + ":" + line;
    }
}
