package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionNode implements JottTree, BodyStmtNode {
    //The Expression Node stores the production body of
    // < operand > | < operand > < relop > < operand > |
    //< operand > < mathop > < operand > | < string_literal > |
    //< bool >
    private ArrayList<Object> sequence;

    public ExpressionNode(ArrayList<Object> sequence){
        this.sequence = sequence;
    }

    public static ExpressionNode parseExpressionNode(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        ArrayList<Object> parseSequence = new ArrayList<>();

        if (tokens == null || tokens.isEmpty()) {
            throw new ParserSyntaxError("Unexpected end of input: expected boolean value.");
        }
        Token first = tokens.get(0);

        // CASE 1: <bool> → True | False
        if (first.getTokenType() == TokenType.ID_KEYWORD) {

            BooleanNode boolNode = BooleanNode.parseBooleanNode(tokens);
            parseSequence.add(boolNode);
            return new ExpressionNode(parseSequence);
        }

        // CASE 2: <string_literal>
        if (first.getTokenType() == TokenType.STRING) {
            StringNode stringNode = StringNode.parseStringNode(tokens);
            parseSequence.add(stringNode);
            return new ExpressionNode(parseSequence);
        }

        // CASE 3: <operand>
        // deal with the Operand
        if( tokens.size() == 1){
            OperandNode operandResult = OperandNode.parseOperand(tokens);
            // if its null then throw error
            if (operandResult == null) {
                throw new ParserSyntaxError("Invalid start of <expr> at line " + first.getLineNum());
            }
            else{
                parseSequence.add(operandResult);
                return new ExpressionNode(parseSequence);
            }
        }

        // CASE 4: <operand> [ <relop> <operand> | <mathop> <operand> ]
        // deal with the leftOperand
        OperandNode leftOperand = OperandNode.parseOperand(tokens);
        // if its null then throw error
        if (leftOperand == null) {
            throw new ParserSyntaxError("Invalid start of <expr> at line " + first.getLineNum());
        }
        // otherwise add it to the sequence
        parseSequence.add(leftOperand);

        // Check for the middle operand to see if it's a <relop> or <mathop>
        Token middleOperand = tokens.get(1);

        if (middleOperand.getTokenType() == TokenType.REL_OP || middleOperand.getTokenType() == TokenType.MATH_OP) {
            parseSequence.add(middleOperand); // consume operator

            // Solve for Right Operand after parsing the middle
            OperandNode rightOperand = OperandNode.parseOperand(tokens);
            parseSequence.add(rightOperand);

            return new ExpressionNode(parseSequence);
        }
        else{
            throw new ParserSyntaxError("Unexpected end of input: expected rel or math op.");
        }

    }


    @Override
    public String convertToJott() {
        StringBuilder sb = new StringBuilder();

        for (Object obj : sequence) {
            if (obj instanceof OperandNode) {
                sb.append(((OperandNode) obj).convertToJott());
            } else if (obj instanceof RelOpNode) {
                sb.append(((RelOpNode) obj).convertToJott());
            } else if (obj instanceof BooleanNode) {
                sb.append(((BooleanNode) obj).convertToJott());
            } else if (obj instanceof StringNode) {
                sb.append(((StringNode) obj).convertToJott());
            } else if (obj instanceof Token) {
                // For math operators stored as Token
                sb.append(((Token) obj).getToken());
            }
        }

        return sb.toString();
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

    public ArrayList<Object> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<Object> sequence) {
        this.sequence = sequence;
    }
}

