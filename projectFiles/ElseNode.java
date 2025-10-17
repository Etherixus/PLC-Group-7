package projectFiles;

import provided.Token;

import java.text.ParseException;
import java.util.ArrayList;

public class ElseNode {
    BodyNode body;

    public ElseNode(BodyNode body) {
        this.body = body;
    }

    public static ElseNode parseElseNode(ArrayList<Token> tokens) throws ParseException, ParserSyntaxError {
        BodyNode body = BodyNode.parseBodyNode(tokens);
        return new ElseNode(body);
    }

    public String convertToJott() {
        return "Else" + body.convertToJott();
    }
}
