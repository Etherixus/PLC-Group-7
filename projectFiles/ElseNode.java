package projectFiles;


import provided.JottTree;
import provided.Token;
import provided.TokenType;


import java.util.ArrayList;

public class ElseNode implements JottTree {
    BodyNode body;

    public ElseNode(BodyNode body) {
        this.body = body;
    }

    public static ElseNode parseElseNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        if(tokens.get(0).getTokenType() != TokenType.ID_KEYWORD || !tokens.get(0).getToken().equals("Else")){
            throw new ParserSyntaxError("Expected Else got: ", tokens.get(0));
        }
        tokens.remove(0);
        if(tokens.get(0).getTokenType() != TokenType.L_BRACE){
            throw new ParserSyntaxError("Expected { got: ", tokens.get(0));
        }

        tokens.remove(0);
        BodyNode body =BodyNode.parseBodyNode(tokens);
        if(tokens.get(0).getTokenType() != TokenType.R_BRACE){
            throw new ParserSyntaxError("Expected } got: ", tokens.get(0));
        }
        tokens.remove(0);
        return new ElseNode(body);
    }

    public boolean hasReturn(){
        return body.hasReturn();
    }

    @Override
    public String convertToJott() {
        String result = "Else{";
        result += body.convertToJott();
        result += "}";
        return result;
    }

    @Override
    public String convertToJava(String className) {
        return "";
    }

    @Override
    public String convertToC() {
        return "";
    }

    @Override
    public String convertToPython() {
        return "";
    }


    public boolean validateTree(SymbolTable symbolTable, String expectedType)
            throws SemanticSyntaxError {
        if (body == null) return false;
        return body.validateTree(symbolTable, expectedType);
    }

    @Override
    public boolean validateTree() {
        if (body == null) return false;
        return body.validateTree();
    }

    public void execute()
    {
        body.execute();
    }
}
