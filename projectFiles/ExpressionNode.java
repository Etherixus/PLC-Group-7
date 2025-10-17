package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionNode implements JottTree {
    //The Expression Node stores the production body of
    // < operand > | < operand > < relop > < operand > |
    //< operand > < mathop > < operand > | < string_literal > |
    //< bool >
    private ArrayList<Token> sequence = new ArrayList<Token>();

    public ExpressionNode(ArrayList<Token> sequence){
        this.sequence = sequence;
    }

    public static ExpressionNode parseExpressionNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        ArrayList<Token> parseSequence = new ArrayList<Token>();

        if (tokens == null || tokens.isEmpty()) {
            throw new ParserSyntaxError("Unexpected end of input: expected boolean value.");
        }
        Token first = tokens.get(0);

        // CASE 1: <bool> → True | False
        if (first.getTokenType() == TokenType.ID_KEYWORD &&
                (first.getToken().equals("True") || first.getToken().equals("False"))) {

            BooleanNode boolNode = BooleanNode.parseBooleanNode(tokens);
            parseSequence.add(first);
            return new ExpressionNode(parseSequence);
        }

        // CASE 2: <string_literal>
        if (first.getTokenType() == TokenType.STRING) {
            parseSequence.add(tokens.remove(0));
            return new ExpressionNode(parseSequence);
        }

        // CASE 3: <operand>
        // deal with the Operand
        OperandNode operandResult = OperandNode.parseOperand(tokens);
        // if its null then throw error
        if (operandResult == null) {
            throw new ParserSyntaxError("Invalid start of <expr> at line " + first.getLineNum());
        }
        else{
            parseSequence.add(first);
            return new ExpressionNode(parseSequence);
        }

        // CASE 4: <operand> [ <relop> <operand> | <mathop> <operand> ]
        // deal with the leftOperand
        OperandNode leftOperand = OperandNode.parseOperand(tokens);
        // if its null then throw error
        if (leftOperand == null) {
            throw new ParserSyntaxError("Invalid start of <expr> at line " + first.getLineNum());
        }
        // otherwise add it to the sequence
        parseSequence.add(first);

        // If no more tokens or next token isn’t an operator, it’s just <operand>
        if (tokens.isEmpty()) {
            return new ExpressionNode(parseSequence);
        }

        Token second = tokens.get(1);
        Token third = tokens.get(2);

        // Check for <relop> or <mathop>
        if (second.getTokenType() == TokenType.REL_OP || second.getTokenType() == TokenType.MATH_OP) {
            parseSequence.add(second); // consume operator

            OperandNode rightOperand = OperandNode.parseOperand(tokens);
            parseSequence.addAll(rightOperand.getSequence());

            return new ExpressionNode(parseSequence);
        }

        // Otherwise, simple <operand>
        return new ExpressionNode(parseSequence);
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

    public ArrayList<Token> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<Token> sequence) {
        this.sequence = sequence;
    }
}

