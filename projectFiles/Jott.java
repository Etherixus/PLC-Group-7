package projectFiles;

import projectFiles.JottParser;
import projectFiles.JottTokenizer;
import provided.JottTree;
import provided.Token;
import java.io.File;
import java.util.ArrayList;

/**
 * Jott.java — Main driver for the Jott interpreter/compiler.
 * Reads a .jott file, tokenizes it, parses it,
 * and runs semantic analysis (validateTree).
 *
 * The program expects a filename as a command line argument
 */
public class Jott {

    public static void main(String[] args) {
        try {

            if (args.length < 1) {
                System.err.println("Usage: java Jott <filename.jott>");
                return;
            }

            //String filename = "phase3testcases/largerValid.jott";
            String filename = args[0];
            File file = new File(filename);

            if (!file.exists()) {
                System.err.println("Error: File not found → " + filename);
                return;
            }


            // Step 1 Tokenize input
            ArrayList<Token> tokens = JottTokenizer.tokenize(filename);
            if (tokens == null) {return;}

            // Step 2 Parse tokens
            JottTree tree = JottParser.parse(tokens);
            if (tree == null) {return;}

            // Step 3 Semantic Analysis
            boolean valid = tree.validateTree();

            // Step 4 Execute
            ProgramNode root = (ProgramNode) tree;
            root.execute();


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /*
     * Prints the message to the console
     * @param msg the message to be printed
     */
    public static void print(String msg) {
        System.out.println(msg);
    }

    /*
     * Used for the built-in concatenate method for jott. Simply concatenates string 2 to the end of string 1
     * @param string1 the base string
     * @param string2 the string that will be added to the end
     * @return the two strings concatenated together
     */
    public static String concat(String string1, String string2) {
        return string1 + string2;
    }

    /*
     * Used for the built-in length method for jott.
     * @param string the string to get the length of
     * @return the length of the string
     */
    public static int length(String string) {
        return string.length();
    }
}
