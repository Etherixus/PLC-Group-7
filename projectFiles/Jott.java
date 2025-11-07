package projectFiles;

import provided.JottParser;
import provided.JottTokenizer;
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

            String filename = "C:/Users/logan/IdeaProjects/PLC-Group-7/provided/testtest.jott";
            File file = new File(filename);

            if (!file.exists()) {
                System.err.println("Error: File not found → " + filename);
                return;
            }

            System.out.println("Reading file: " + filename);

            // 2 Tokenize input
            ArrayList<Token> tokens = JottTokenizer.tokenize(filename);
            System.out.println("Tokenization complete. Total tokens: " + tokens.size());

            // 3 Parse tokens into an AST
            ProgramNode program = (ProgramNode) JottParser.parse(tokens);
            System.out.println("Parsing complete.");

            // 4 Run semantic validation
            System.out.println("Starting semantic analysis...");
            boolean valid = program.validateTree();

            // 5 Output result
            if (valid) {
                System.out.println("Program is semantically valid!");
            } else {
                System.err.println("Semantic validation failed.");
            }

        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
