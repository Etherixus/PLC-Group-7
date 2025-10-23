package Unused;


import projectFiles.BodyNode;
import projectFiles.ParserSyntaxError;
import projectFiles.VarDecNode;
import provided.JottTree;
import provided.Token;

import java.text.ParseException;
import java.util.ArrayList;

/**public interface FBodyNode extends JottTree {
    static FBodyNode parseFBody(ArrayList<Token> tokens) throws ParseException, ParserSyntaxError {
        String currentNode = tokens.get(0).getToken();
        if(currentNode.equals("Double") || currentNode.equals("Integer") || currentNode.equals("Boolean") || currentNode.equals("String")) {
            return VarDecNode.parseVarDecNode(tokens);
        }
        else{
            return BodyNode.parseBodyNode(tokens);
        }
   }
}
 */
