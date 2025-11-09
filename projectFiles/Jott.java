package projectFiles;

import projectFiles.JottParser;
import projectFiles.JottTokenizer;
import provided.JottTree;
import provided.Token;
import java.io.File;
import java.util.ArrayList;

/**
 * Jott.java — Main driver for the Jott interpreter/compiler.
 * Reads a .jott file, tokenizes it, parses it into an AST,
 * and runs semantic analysis (validateTree).
 */
public class Jott {

    public static void main(String[] args) {
        try {

            // 1 Check for input file
            //if (args.length < 1) {
            //    System.err.println("Usage: java Jott <filename.jott>");
            //    return;
            //}

            String filename = "C:/Users/logan/IdeaProjects/PLC-Group-7/parserTestCases/codeAfterReturn.jott";
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


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
