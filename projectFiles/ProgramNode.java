package projectFiles;

import provided.JottTree;
import provided.Token;

import java.text.ParseException;
import java.util.ArrayList;

public class ProgramNode implements JottTree {
    ArrayList<FunctionDefNode> functionsDefs;

    public ProgramNode(ArrayList<FunctionDefNode> functionsDefs) {
        this.functionsDefs = functionsDefs;
    }

    public static ProgramNode parseProgram(ArrayList<Token> tokens) throws ParseException {
        ArrayList<FunctionDefNode> functionsDefs = new ArrayList<>();
        while (!tokens.isEmpty()) {
            functionsDefs.add(FunctionDefNode.parseFunctionDef(tokens));
        }
        return new ProgramNode(functionsDefs);
    }

    @Override
    public String convertToJott() {
        String output = "";
        for (FunctionDefNode functionDef : functionsDefs) {
            output += functionDef.convertToJott();
        }
        return output;
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
