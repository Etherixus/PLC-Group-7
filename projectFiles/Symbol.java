package projectFiles;

import java.util.ArrayList;

// The Symbol class represents a single named entity in a program
// either a variable or a function.
public class Symbol {
    public String name;                  // the literal name of the symbol in the source code
    public String type;                  // data type like Integer, Double, String, Boolean, etc
    public boolean isFunction;           // tells us if the symbol is a function or a variable
    public ArrayList<String> paramTypes; // used for validating function calls to check if arguments match expected types
    public String returnType;            // if function, checksif the body actaully returns the correect type
    public boolean initialized = false;  // for tracking uninitialized variables
    public int lineNum;                  // useful for error reporting
    public Object value;



    //Variable constructor
    public Symbol(String name, String type, int lineNum) {
        this.name = name;
        this.type = type;
        this.isFunction = false;
        this.paramTypes = new ArrayList<>();
        this.initialized = false;  // variables start uninitialized
        this.returnType = null;    // not applicable
        this.lineNum = lineNum;
    }

    // Function Constructor
    public Symbol(String name, String returnType, ArrayList<String> paramTypes, int lineNum) {
        this.name = name;
        this.returnType = returnType;
        this.paramTypes = paramTypes;
        this.isFunction = true;
        this.initialized = true;   // functions are always "defined"
        this.type = null;          // not used for functions
        this.lineNum = lineNum;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }


}
