package projectFiles;

import provided.JottTree;
import provided.Token;
import provided.TokenType;

import java.beans.Expression;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExpressionNode implements JottTree, BodyStmtNode {
    //The Expression Node stores the production body of
    // < operand > | < operand > < relop > < operand > |
    //< operand > < mathop > < operand > | < string_literal > |
    //< bool >
    private ExpressionNode Left;
    private ExpressionNode Middle;
    private ExpressionNode Right;

    public ExpressionNode(){

    }

    public ExpressionNode(ExpressionNode Left, ExpressionNode Middle, ExpressionNode Right) {
        this.Left = Left;
        this.Middle = Middle;
        this.Right = Right;
    }

    public static ExpressionNode parseExpressionNode(ArrayList<Token> tokens) throws ParserSyntaxError {
        ExpressionNode left = null;
        ExpressionNode middle = null;
        ExpressionNode right = null;

        if((tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False")) && left == null){
            BooleanNode boo = BooleanNode.parseBooleanNode(tokens);
            left = boo;
        }

        if(tokens.get(0).getTokenType() == TokenType.ID_KEYWORD){
            IDNode id = IDNode.parseIDNode(tokens);
            left = id;
        }
        if(tokens.get(0).getTokenType() == TokenType.NUMBER && left == null){
            NumberNode number = NumberNode.parseNumberNode(tokens);
            left = number;
        }
        if(tokens.get(0).getTokenType() == TokenType.FC_HEADER && left == null){
            FunctionCallNode func = FunctionCallNode.parseFunctionCallNode(tokens);
            left = func;
        }
        if(tokens.get(0).getTokenType() == TokenType.STRING && left == null){
            StringNode str = StringNode.parseStringNode(tokens);
            left = str;
        }

        if(left == null){
            throw new ParserSyntaxError("Expected Expression but got: " + tokens.get(0).getTokenType(), tokens.get(0));
        }

        if(tokens.get(0).getTokenType() == TokenType.REL_OP || tokens.get(0).getTokenType() == TokenType.MATH_OP){
            if(tokens.get(0).getTokenType() == TokenType.REL_OP){
                middle = RelOpNode.parseRelOpNode(tokens);
            }
            else{
                middle = MathOpNode.parseMathOpNode(tokens);
            }
        }

        if(middle != null){
            right = ExpressionNode.parseExpressionNode(tokens);
        }

        return new ExpressionNode(left, middle, right);
    }


    @Override
    public String convertToJott() {
        if(Middle == null){
            return Left.convertToJott();
        }
        else{
            return Left.convertToJott() + Middle.convertToJott() + Right.convertToJott();
        }
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

    public String getType(SymbolTable table) throws SemanticSyntaxError {
        // Base case: no operator, just return the type of the left operand
        if (Middle == null) {
            if (Left instanceof NumberNode) return ((NumberNode) Left).getType();
            if (Left instanceof StringNode) return "String";
            if (Left instanceof BooleanNode) return "Boolean";
            if (Left instanceof IDNode) {
                String name = ((IDNode) Left).convertToJott();
                Symbol sym = table.lookup(name);
                if (sym == null)
                    throw new SemanticSyntaxError("Undeclared identifier '" + name + "'");


                if (sym.type != null) return sym.type;
                if (sym.returnType != null) return sym.returnType;

                return "Unknown";
            }
            if (Left instanceof FunctionCallNode) {
                return ((FunctionCallNode) Left).getReturnType(table);
            }
            return "Unknown";
        }

        String leftType;
        if (Left instanceof NumberNode) {
            leftType = ((NumberNode) Left).getType();
        } else if (Left instanceof ExpressionNode) {
            leftType = ((ExpressionNode) Left).getType(table);
        } else {
            leftType = Left.getType(table);
        }


        String rightType;
        if (Right == null) {
            throw new SemanticSyntaxError("Invalid expression: missing right operand in ExpressionNode");
        } else {
            rightType = Right.getType(table);
        }


        // Handle math operators (+, -, *, /)
        if (Middle instanceof MathOpNode) {
            if (leftType.equals("Double") || rightType.equals("Double")) return "Double";
            if (leftType.equals("Integer") && rightType.equals("Integer")) return "Integer";
            throw new SemanticSyntaxError("Invalid operands for math operator: "
                    + leftType + " and " + rightType);
        }

        // Handle relational operators (<, >, ==, etc.)
        if (Middle instanceof RelOpNode) {
            return "Boolean";
        }

        return "Unknown";
    }

    @Override
    public boolean validateTree() {
        // Left must exist and be valid
        if (Left == null) return false;
        if (!Left.validateTree()) return false;

        // If there's no operator, it's a single operand expression and is valid
        if (Middle == null) return true;

        // There is an operator: operator node must be valid and right operand must exist and be valid
        if (!Middle.validateTree()) return false;
        if (Right == null) return false;
        if (!Right.validateTree()) return false;
        return true;
    }
}

