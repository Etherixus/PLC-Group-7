package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){

		ArrayList<Token> tokens = new ArrayList<>();
		String content = "";

		try {
			content = Files.readString(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
			return tokens;
		}

		// TODO: turn content into tokens
		return tokens;
	}
}