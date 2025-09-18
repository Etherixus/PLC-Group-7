package provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import java.io.File;
import java.util.ArrayList;
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

		ArrayList<Token> finalTokenList = new ArrayList<>();

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

            int curLineNumber = 0;
			// Process tokenList
			while (!tokenList.isEmpty()) {


				if(Character.isDigit(tokenList.get(0)) || tokenList.get(0) == '.') {
					String currNumber = "";
                    boolean hasSeenDecimal = false;
					do{
						if(tokenList.get(0) == '.'){
							if(!hasSeenDecimal && Character.isDigit(tokenList.get(1))){
                                hasSeenDecimal = true;
								currNumber = currNumber + tokenList.get(0) + tokenList.get(1);
								tokenList.remove(0);
								tokenList.remove(0);
							} else{
                                if(hasSeenDecimal){
                                    throw new TokenizerSyntaxError(
                                            TokenizerSyntaxError.createTokenizerSyntaxErrorMessage(
                                                    "Number can only have one decimal", currNumber+tokenList.get(0), filename, curLineNumber));
                                } else {
                                    throw new TokenizerSyntaxError(
                                            TokenizerSyntaxError.createTokenizerSyntaxErrorMessage(
                                                    "\".\" expects following digit", "."+tokenList.get(1), filename, curLineNumber));
                                }

							}
						} else {
							currNumber = currNumber + tokenList.get(0).toString();
							tokenList.remove(0);
						}
					} while(Character.isDigit(tokenList.get(0)) || tokenList.get(0) == '.');
					finalTokenList.add(new Token(currNumber, filename, curLineNumber, TokenType.NUMBER));
                    continue;
				}

                //more stuff to do here....
                switch (tokenList.get(0)) {
                    case '\n':
                        curLineNumber++;
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
                        while (!tokenList.isEmpty()) {
                            if (tokenList.get(0) != '\n') {
                                tokenList.remove(0);
                            } else {
                                break;
                            }
                        }
                    case ';':
                        Token semicolon = new Token("semicolon", filename, curLineNumber, TokenType.SEMICOLON);
                        finalTokenList.add(semicolon);
                        tokenList.remove(0);
                        break;

                    case '!', '+', '-', '*', '/':
                        Token mathOp = new Token(tokenList.get(0).toString(), filename, curLineNumber, TokenType.MATH_OP);
                        finalTokenList.add(mathOp);
                        tokenList.remove(0);
                        break;
                    case ',': //comma
                        Token comma = new Token("comma", filename, curLineNumber, TokenType.COMMA);
                        finalTokenList.add(comma);
                        tokenList.remove(0);
                        break;
                    case '[': //lbracket
                        Token lbracket = new Token("lbracket", filename, curLineNumber, TokenType.L_BRACKET);
                        finalTokenList.add(lbracket);
                        tokenList.remove(0);
                        break;
                    case ']': //rbracket
                        Token rbracket = new Token("comma", filename, curLineNumber, TokenType.R_BRACKET);
                        finalTokenList.add(rbracket);
                        tokenList.remove(0);
                        break;
                    case '{': //lbrace
                        Token lbrace = new Token("lbrace", filename, curLineNumber, TokenType.L_BRACE);
                        finalTokenList.add(lbrace);
                        tokenList.remove(0);
                        break;
                    case '}': //rbrace
                        Token rbrace = new Token("rbrace", filename, curLineNumber, TokenType.R_BRACE);
                        finalTokenList.add(rbrace);
                        tokenList.remove(0);
                        break;
                    case '=': //equals
                        if (tokenList.get(1) == '=') {
                            Token doubleEq = new Token("doubleEq", filename, curLineNumber, TokenType.REL_OP);
                            finalTokenList.add(doubleEq);
                            tokenList.remove(1);
                            tokenList.remove(0);
                        } else {
                            Token assign = new Token("assign", filename, curLineNumber, TokenType.ASSIGN);
                            finalTokenList.add(assign);
                            tokenList.remove(0);
                        }
                        break;
                    case '>': //gthan
                        if (tokenList.get(1) == '=') {
                            Token gthanEq = new Token("gthanEq", filename, curLineNumber, TokenType.REL_OP);
                            finalTokenList.add(gthanEq);
                            tokenList.remove(1);
                            tokenList.remove(0);
                        } else {
                            Token gthan = new Token("gthan", filename, curLineNumber, TokenType.REL_OP);
                            finalTokenList.add(gthan);
                            tokenList.remove(0);
                        }
                        break;
                    case '<': //lthan
                        if (tokenList.get(1) == '=') {
                            Token lthanEq = new Token("lthanEq", filename, curLineNumber, TokenType.REL_OP);
                            finalTokenList.add(lthanEq);
                            tokenList.remove(1);
                            tokenList.remove(0);
                        } else {
                            Token lthan = new Token("lthan", filename, curLineNumber, TokenType.REL_OP);
                            finalTokenList.add(lthan);
                            tokenList.remove(0);
                        }
                }
			}
            return finalTokenList;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (TokenizerSyntaxError e) {
            System.err.println(e.getMessage());
            return null;
        }
	}

    //todo remove before turning in
    public static void main(String[] args) {
        ArrayList<Token> tokens = tokenize("provided/test.jott");
        if (tokens != null) {for (Token token : tokens) {
            System.out.println(token.getToken());
        }}

    }
}