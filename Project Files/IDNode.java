public class IDNode implements JottTree {
    private String keyword;

    public IDNode(String keyword) {
        this.keyword = keyword;
    }

    public static IDNode parseIDNode(ArrayList<Token> tokenList) {
        if (tokenList == null || tokenList.isEmpty()) {
            return null; // nothing to parse
        }

        Token token = tokenList.get(0);

        if (token.getTokenType() == TokenType.ID_KEYWORD) { 
            return new IDNode(token.getToken());
        }

        return null; // not an ID_KEYWORD token
    }

    @Override
    public String toString() {
        return keyword;
    }
}
