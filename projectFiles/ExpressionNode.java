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
        if(Middle != null){
            return Middle.getToken();
        } else if(Left != null){
            return Left.getToken();
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
                    throw new SemanticSyntaxError("Undeclared identifier '" + name + "'", getToken());

                table.checkInitialized(name, getToken());

                // Return declared type
                if (sym.type != null) return sym.type;
                if (sym.returnType != null) return sym.returnType;

                return "Unknown";
            }
            if (Left instanceof FunctionCallNode) {
                return ((FunctionCallNode) Left).getReturnType(table);
            }
            if (this instanceof NumberNode) {return ((NumberNode) this).getType();}
            if (this instanceof StringNode) {return "String";}
            if (this instanceof BooleanNode) {return "Boolean";}
            if (this instanceof FunctionCallNode) {return ((FunctionCallNode) this).getReturnType(table);}
            if (this instanceof IDNode) {
                String name = ((IDNode) this).convertToJott();
                Symbol sym = table.lookup(name);
                if (sym == null)
                    throw new SemanticSyntaxError("Undeclared identifier '" + name + "'", getToken());

                table.checkInitialized(name, getToken());

                // Return declared type
                if (sym.type != null) return sym.type;
                if (sym.returnType != null) return sym.returnType;

                return "Unknown";
            }
            return "Unknown";
        }

        // operator exists — analyze both sides
        if (Left instanceof IDNode) {
            String name = ((IDNode) Left).convertToJott();
            Symbol sym = table.lookup(name);
            if (sym == null)
                throw new SemanticSyntaxError("Undeclared identifier '" + name + "'", getToken());
            table.checkInitialized(name, getToken());
        }
        if (Right instanceof IDNode) {
            String name = ((IDNode) Right).convertToJott();
            Symbol sym = table.lookup(name);
            if (sym == null)
                throw new SemanticSyntaxError("Undeclared identifier '" + name + "'", getToken());
            table.checkInitialized(name, getToken());

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
            throw new SemanticSyntaxError("Invalid expression: missing right operand in ExpressionNode", getToken());
        } else {
            rightType = Right.getType(table);
        }


        // Handle math operators (+, -, *, /)
        if (Middle instanceof MathOpNode) {
            if (leftType.equals("Double") && rightType.equals("Double")) return "Double";
            if (leftType.equals("Integer") && rightType.equals("Integer")) return "Integer";
            throw new SemanticSyntaxError("Invalid operands for math operator: "
                    + leftType + " and " + rightType, getToken());
        }

        // Handle relational operators (<, >, ==, etc.)
        if (Middle instanceof RelOpNode) {
            if((leftType.equals("Double") && rightType.equals("Double")) ||
                    (leftType.equals("Integer") && rightType.equals("Integer"))){return "Boolean";}
            else{
                throw new SemanticSyntaxError("Expected operands to be the same type but got " + leftType + " and " + rightType, getToken());
            }
        }

        // if the type cant be identified then it returns unknown
        return "Unknown";
    }

    public Object evaluate() {

        // leaf nodes
        if (this instanceof NumberNode) {
            return ((NumberNode) this).getValue();
        }

        if (this instanceof StringNode) {
            return ((StringNode) this).getValue();
        }

        if (this instanceof BooleanNode) {
            return ((BooleanNode) this).getValue();
        }

        if (this instanceof IDNode) {
            String name = ((IDNode) this).convertToJott();
            Symbol sym = SymbolTable.getCurrentTable().lookup(name);

            if (sym == null) {
                throw new RuntimeException("Variable not found at runtime: " + name);
            }

            return sym.getValue();
        }

        if (this instanceof FunctionCallNode) {
            return ((FunctionCallNode) this).evaluate();
        }

        // expression node
        if (Left == null) {
            throw new RuntimeException("ExpressionNode has null Left. Node: " + convertToJott());
        }

        // if there is no operator, just evaluate left
        if (Middle == null) {
            return Left.evaluate();
        }

        Object leftVal = Left.evaluate();
        Object rightVal = Right.evaluate();

        // math ops
        if (Middle instanceof MathOpNode) {
            String op = ((MathOpNode) Middle).getOperator();

            // integers
            if (leftVal instanceof Integer && rightVal instanceof Integer) {
                int l = (Integer) leftVal;
                int r = (Integer) rightVal;

                switch (op) {
                    case "+":
                        return l + r;
                    case "-":
                        return l - r;
                    case "*":
                        return l * r;
                    case "/":
                        if(r == 0)
                        {
                            throw new RuntimeException("Runtime Error:\nDivision by zero error\n"+ getToken().getFilename()+":"+getToken().getLineNum());
                        }
                        return l / r;
                    default:
                        throw new RuntimeException("Unknown math operator: " + op);
                }
            }

            // doubles
            if (leftVal instanceof Double && rightVal instanceof Double) {
                double l = (Double) leftVal;
                double r = (Double) rightVal;

                switch (op) {
                    case "+":
                        return l + r;
                    case "-":
                        return l - r;
                    case "*":
                        return l * r;
                    case "/":
                        if(r == 0){
                            throw new RuntimeException("Runtime Error:\nDivision by zero error\n"+ getToken().getFilename()+":"+getToken().getLineNum());
                        }
                        return l / r;
                    default:
                        throw new RuntimeException("Unknown math operator: " + op);
                }
            }
        }

        // rel ops
        if (Middle instanceof RelOpNode) {
            String op = ((RelOpNode) Middle).getOperator();

            if (leftVal instanceof Integer && rightVal instanceof Integer) {
                int l = (Integer) leftVal;
                int r = (Integer) rightVal;

                switch (op) {
                    case ">":
                        return l > r;
                    case "<":
                        return l < r;
                    case ">=":
                        return l >= r;
                    case "<=":
                        return l <= r;
                    case "==":
                        return l == r;
                    case "!=":
                        return l != r;
                    default:
                        throw new RuntimeException("Unknown relational operator: " + op);
                }
            }
            if (leftVal instanceof Double && rightVal instanceof Double) {
                double l = (Double) leftVal;
                double r = (Double) rightVal;
                switch (op) {
                    case ">":
                        return l > r;
                    case "<":
                        return l < r;
                    case ">=":
                        return l >= r;
                    case "<=":
                        return l <= r;
                    case "==":
                        return l == r;
                    case "!=":
                        return l != r;
                    default:
                        throw new RuntimeException("Unknown relational operator: " + op);
                }
            }
        }

        throw new RuntimeException("Could not evaluate expression: " + convertToJott());
    }




    @Override
    public Object execute() {
        evaluate();
        return null;
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

