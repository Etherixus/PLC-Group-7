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
}
