package projectFiles;

import provided.Token;
import provided.JottTree;
import provided.TokenType;


import java.util.ArrayList;

public class StringNode extends ExpressionNode implements JottTree {
    private String string;
    
    
    public StringNode(String string) {
        this.string = string;
    }

    public static StringNode parseStringNode(ArrayList<Token>tokenList) throws ParserSyntaxError{
       if(tokenList.get(0).getTokenType() != TokenType.STRING){
           throw new ParserSyntaxError("Expecter String, got: ", tokenList.get(0));
       }
       else{
           return new StringNode(tokenList.remove(0).getToken());
       }
    }
    @Override
    public String toString() {
        return string;
    }

    private static boolean isValidStringLiteral(String s) {
        if (s == null) return false;
        /*  Tokenizer produces strings that include the surrounding double quotes
        // Accept only if it starts and ends with a double quote and contains
         only letters, digits and spaces in between.*/

        if (s.length() < 2) return false;
        if (s.charAt(0) != '"' || s.charAt(s.length() - 1) != '"') return false;
        String inner = s.substring(1, s.length() - 1);
        for (char c : inner.toCharArray()) {
            if (!(Character.isLetter(c) || Character.isDigit(c) || c == ' ')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String convertToJott() {
        return string;
    }

    @Override
    public String convertToJava(String className) {return "";}

    @Override
    public String convertToC() {return "";}

    @Override
    public String convertToPython() {return "";}

    @Override
    public boolean validateTree() { return isValidStringLiteral(string); }
}

