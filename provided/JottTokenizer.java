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

		ArrayList<Token> finalTokenList = null;

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
				switch (tokenList.get(0)) {
					case '\n':
						tokenList.remove(0);
						break;
					case '\t':
						tokenList.remove(0);
						break;
					case ' ':
						tokenList.remove(0);
						break;
					case '#':
						tokenList.remove(0);
						while(!tokenList.isEmpty()) {
							if(tokenList.get(0) != '\n') {
								tokenList.remove(0);
							}
							else{
								break;
							}
						}
					case ';':
						Token semicolon = new Token("semicolon", filename, 0, TokenType.SEMICOLON);
						finalTokenList.add(semicolon);
						tokenList.remove(0);
						break;

					case '!', '+', '-', '*', '/':
						Token mathOp = new Token("mathOp", filename, 0, TokenType.MATH_OP);
						finalTokenList.add(mathOp);
						tokenList.remove(0);
						break;
          case ',': //comma
            Token comma = new Token("comma", filename, 0, TokenType.COMMA);
            finalTokenList.add(comma);
            tokenList.remove(0);
            break;
            case '[': //lbracket
            Token lbracket = new Token("lbracket", filename, 0, TokenType.L_BRACKET);
            finalTokenList.add(lbracket);
            tokenList.remove(0);
            break;
          case ']': //rbracket
            Token rbracket = new Token("comma", filename, 0, TokenType.R_BRACKET);
            finalTokenList.add(rbracket);
            tokenList.remove(0);
            break;
          case '{': //lbrace
            Token lbrace = new Token("lbrace", filename, 0, TokenType.L_BRACE);
            finalTokenList.add(lbrace);
            tokenList.remove(0);
            break;
          case '}': //rbrace
            Token rbrace = new Token("rbrace", filename, 0, TokenType.R_BRACE);
            finalTokenList.add(rbrace);
            tokenList.remove(0);
            break;     
          case '=': //equals   
            if(tokenList.get(1) == '='){
              Token doubleEq = new Token("doubleEq", filename, 0, TokenType.REL_OP)
              finalTokenList.add(doubleEq);
              tokenList.remove(1);
              tokenList.remove(0);
            }
            else{
              Token assign = new Token("assign", filename, 0, TokenType.ASSIGN)
              finalTokenList.add(assign);
              tokenList.remove(0);
            }
            break;
          case '>': //gthan
            if(token(1) == '='){
              Token gthanEq = new Token("gthanEq", filename, 0, TokenType.REL_OP);
              finalTokenList.add(gthanEq);
              tokenList.remove(1);
              tokenList.remove(0);
            }
            else{
              Token gthan = new Token("gthan", filename, 0, TokenType.REL_OP);
              finalTokenList.add(gthan);
              tokenList.remove(0);
            }
            break;
          case '<': //lthan
            Tif(token(1) == '='){
              Token lthanEq = new Token("lthanEq", filename, 0, TokenType.REL_OP);
              finalTokenList.add(lthanEq);
              tokenList.remove(1);
              tokenList.remove(0);
            }
            else{
              Token lthan = new Token("lthan", filename, 0, TokenType.REL_OP);
              finalTokenList.add(lthan);
              tokenList.remove(0);
        } 
			}


		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
}