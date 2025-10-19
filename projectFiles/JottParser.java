package projectFiles;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import provided.JottTree;
import provided.Token;

import java.text.ParseException;
import java.util.ArrayList;

import static projectFiles.JottTokenizer.tokenize;

public class JottParser {

    /**
     * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens) throws ParserSyntaxError, ParseException {
        ArrayList<JottTree> nodes = new ArrayList<>();
		if(tokens.isEmpty()) {
            return null;
        }
        else{
            while(!tokens.isEmpty()){
                JottTree function = FunctionDefNode.parseFunctionDef(tokens);
                if(function == null) {
                    return null;
                }
                else{
                    nodes.add(function);
                }
            }
        }
        return nodes.get(0);
    }
    //TODO delete before turning in
    //expect result Def:ID_KEYWORD main:ID_KEYWORD [:L_BRACKET ]:R_BRACKET colon:COLON Void:ID_KEYWORD {:L_BRACE fcHeader:FC_HEADER print:ID_KEYWORD [:L_BRACKET 5:NUMBER ]:R_BRACKET ;:SEMICOLON fcHeader:FC_HEADER print:ID_KEYWORD [:L_BRACKET foo bar:STRING ]:R_BRACKET ;:SEMICOLON }:R_BRACE
    public static void main(String[] args) throws ParserSyntaxError, ParseException {
        ArrayList<Token> tokens = tokenize("provided/test.jott");
        /**for(Token token : tokens) {
            System.out.print(token.getToken());
            System.out.print(token.getTokenType());
       }*/
        JottTree tree = parse(tokens);
        System.out.println(tree);
    }
}
