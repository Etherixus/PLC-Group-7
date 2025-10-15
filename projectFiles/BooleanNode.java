public class BooleanNode implements JottTree {
    private boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public static BooleanNode parseBooleanNode(ArrayList<Token> tokenList) throws ParseException {
        if (tokenList == null || tokenList.isEmpty()) {
            throw new ParseException("Unexpected end of input: expected boolean value.");
        }

        Token token = tokenList.get(0);

        // Expecting a BOOLEAN token type
        if (token.getTokenType() == TokenType.ID_KEYWORD && (token == "true") || (token == "false")){
            boolean val = Boolean.parseBoolean(token.getToken());
            tokenList.remove(0); // consume token
            return new BooleanNode(val);
        }

        // If it’s not a boolean token, throw an error
        throw new ParseException(
            "Invalid token '" + token.getToken() + "' at line " + token.getLineNum()
            + ": expected 'true' or 'false'."
        );
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public String convertToJott() {
        return toString();
    }

    @Override
    public boolean validateTree() {
        return true;
    }
}

