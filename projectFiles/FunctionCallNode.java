package projectFiles;

import provided.Token;

import java.util.ArrayList;

public class FunctionCallNode implements OperandNode {


    @Override
    public OperandNode parseOperand() {
        return null;
    }

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws ParserSyntaxError{
        return null;
    }

    @Override
    public String convertToJott() {
        return "";
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

    @Override
    public boolean validateTree() {
        return false;
    }
}
