package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){

		try {
			Scanner file = new Scanner(new File(filename));

			// Read file into a list of characters (tokenList)
			ArrayList<Character> tokenList = new ArrayList<>();
			while (file.hasNextLine()) {
				String line = file.nextLine();
				for (char c : line.toCharArray()) {
					tokenList.add(c);
				}
				// keep newlines too
				tokenList.add('\n');
			}
			file.close();

			// Process tokenList
			while (!tokenList.isEmpty()) {
				//more stuff to do here....
        switch(tokenList.get(0)){
          case "\n":
            break; 
        } 

			}


		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
}