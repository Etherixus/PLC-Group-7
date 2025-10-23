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
    public boolean validateTree() {return false;}
}

