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

    public Token getToken(){
        if(Left != null){
            return Left.getToken();
        } else if(Middle != null){
            return Middle.getToken();
        } else if(Right != null){
            return Right.getToken();
        } else {
            return null;
        }
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

    // The reason why this function is called getType and not validateTree is because this function returns the actual
    // types not true or false
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

                table.checkInitialized(name, sym.lineNum);

                // Return declared type
                if (sym.type != null) return sym.type;
                if (sym.returnType != null) return sym.returnType;

                return "Unknown";
            }
            if (Left instanceof FunctionCallNode) {
                return ((FunctionCallNode) Left).getReturnType(table);
            }
            return "Unknown";
        }

        // operator exists — analyze both sides
        if (Left instanceof IDNode) {
            String name = ((IDNode) Left).convertToJott();
            Symbol sym = table.lookup(name);
            if (sym == null)
                throw new SemanticSyntaxError("Undeclared identifier '" + name + "'");
            table.checkInitialized(name, sym.lineNum);
        }
        if (Right instanceof IDNode) {
            String name = ((IDNode) Right).convertToJott();
            Symbol sym = table.lookup(name);
            if (sym == null)
                throw new SemanticSyntaxError("Undeclared identifier '" + name + "'");
            table.checkInitialized(name, sym.lineNum);
        }

        // Recursively determine type of left operand
        String leftType;
        if (Left instanceof NumberNode) {
            leftType = ((NumberNode) Left).getType();
        } else if (Left instanceof ExpressionNode) {
            leftType = ((ExpressionNode) Left).getType(table);
        } else {
            leftType = Left.getType(table);

        }

        // Recursively determine type of right operand
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

        // if the type cant be identified then it returns unknown
        return "Unknown";
    }

    /**
     * Default structural validation method required by the JottTree interface.
     *
     * This method is part of the syntactic validation layer of the compiler.
     * However, in ExpressionNode, all syntactic validation is already handled
     * during parsing (inside parseExpressionNode()).
     *
     * Therefore, this function is not used in semantic analysis
     * and simply returns false by default.
     *
     * thats why its blank
     */
    @Override
    public boolean validateTree() {

        return false;
    }
}

