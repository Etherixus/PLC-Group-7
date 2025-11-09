package testers;

import projectFiles.JottParser;
import projectFiles.JottTokenizer;
import projectFiles.SemanticSyntaxError;
import provided.JottTree;
import provided.Token;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JottSemanticTester {
    ArrayList<JottSemanticTester.TestCase> testCases;

    private static class TestCase{
        String testName;
        String fileName;
        boolean error;

        public TestCase(String testName, String fileName, boolean error) {
            this.testName = testName;
            this.fileName = fileName;
            this.error = error;
        }
    }

    private boolean tokensEqualNoFileData(Token t1, Token t2){
        return t1.getTokenType() == t2.getTokenType() &&
                t1.getToken().equals(t2.getToken());
    }

    private void createTestCases(){
        this.testCases = new ArrayList<>();
        testCases.add(new JottSemanticTester.TestCase("funcCallParamInvalid", "funcCallParamInvalid.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("function Not Defined", "funcNotDefined.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("function return in expression", "funcReturnInExpr.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("function wrong param type", "funcWrongParamType.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("Hello World In Jott", "helloWorld.jott", false ));
        testCases.add(new JottSemanticTester.TestCase("If stmt returns", "ifStmtReturns.jott", false ));
        testCases.add(new JottSemanticTester.TestCase("Larger Valid program", "largerValid.jott", false ));
        testCases.add(new JottSemanticTester.TestCase("Main Return Not an Int", "mainReturnNotInt.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("mismatched return", "mismatchedReturn.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("Missing function params", "missingFuncParams.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("Missing main function", "missingMain.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("Missing return", "missingReturn.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("No return in all if paths", "noReturnIf.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("No return statement with while", "noReturnWhile.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("providedExample1", "providedExample1.jott", false ));
        testCases.add(new JottSemanticTester.TestCase("return id from void function", "returnId.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("valid while loop", "validLoop.jott", false ));
        testCases.add(new JottSemanticTester.TestCase("return statement in a void function", "voidReturn.jott", true ));
        testCases.add(new JottSemanticTester.TestCase("While as a variable", "whileKeyword.jott", true ));
    }

    private boolean semanticTest(JottSemanticTester.TestCase test, String orginalJottCode){
        try {
            ArrayList<Token> tokens = JottTokenizer.tokenize("phase3testcases/" + test.fileName);

            if (tokens == null) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("\t\tExpected a list of tokens, but got null");
                System.err.println("\t\tPlease verify your tokenizer is working properly");
                return false;
            }
            //System.out.println(tokenListString(tokens));
            ArrayList<Token> cpyTokens = new ArrayList<>(tokens);
            JottTree root = JottParser.parse(tokens);

            if(root == null){
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("\t\tParsing Failed");
                return false;
            } else {

                boolean value = root.validateTree();
                if(test.error && value){
                    return false;
                } else if(test.error && !value){
                    return true;
                }

            }


            String jottCode = root.convertToJott();


            try {
                FileWriter writer = new FileWriter("phase3testcases/semanticTestTemp.jott");
                if (jottCode == null) {
                    System.err.println("\tFailed Test: " + test.testName);
                    System.err.println("Expected a program string; got null");
                    return false;
                }
                writer.write(jottCode);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Token> newTokens = JottTokenizer.tokenize("phase3testcases/semanticTestTemp.jott");

            if (newTokens == null) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("Tokenization of files dot not match.");
                System.err.println("Similar files should have same tokenization.");
                System.err.println("Expected: " + tokenListString(tokens));
                System.err.println("Got: null");
                return false;
            }

            if (newTokens.size() != cpyTokens.size()) {
                System.err.println("\tFailed Test: " + test.testName);
                System.err.println("Tokenization of files dot not match.");
                System.err.println("Similar files should have same tokenization.");
                System.err.println("Expected: " + tokenListString(cpyTokens));
                System.err.println("Got:    : " + tokenListString(newTokens));
                return false;
            }


            return true;
        } catch (SemanticSyntaxError semanticSyntaxError){
            System.err.println(semanticSyntaxError.getMessage());
            if(test.error){return true;}
            else {return false;}
        }
        catch (Exception e){
            System.err.println("\tFailed Test: " + test.testName);
            System.err.println("Unknown Exception occured.");
            e.printStackTrace();
            return false;
        }
    }

    private String tokenListString(ArrayList<Token> tokens){
        StringBuilder sb = new StringBuilder();
        for (Token t: tokens) {
            sb.append(t.getToken());
            sb.append(":");
            sb.append(t.getTokenType().toString());
            sb.append(" ");
        }
        return sb.toString();
    }

    private boolean runTest(JottSemanticTester.TestCase test){
        System.out.println("Running Test: " + test.testName);
        String orginalJottCode;
        try {
            orginalJottCode = new String(
                    Files.readAllBytes(Paths.get("phase3testcases/" + test.fileName)));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return semanticTest(test, orginalJottCode);

    }

    public static void main(String[] args) {
        System.out.println("NOTE: System.err may print at the end. This is fine.");
        JottSemanticTester tester = new JottSemanticTester();

        int numTests = 0;
        int passedTests = 0;
        tester.createTestCases();
        for(JottSemanticTester.TestCase test: tester.testCases){
            numTests++;
            if(tester.runTest(test)){
                passedTests++;
                System.out.println("\tPassed\n");
            }
            else{
                System.out.println("\tFailed\n");
            }
        }

        System.out.printf("Passed: %d/%d%n", passedTests, numTests);
    }
}
