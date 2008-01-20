/*
 * OrgPseudoNode.java
 *
 * Created on Sobota, 2007, september 29, 10:32
 *
 * KEEP IT SIMPLE, STUPID
 * some things just: YOU AREN'T GONNA NEED IT
 */

package tree8080;

import compiler8080.*;
import tree8080Abstract.*;
/**
 *
 * @author vbmacher
 */
public class OrgPseudoNode extends PseudoNode {
    private ExprNode expr;
    
    /** Creates a new instance of OrgPseudoNode */
    public OrgPseudoNode(ExprNode expr, int line, int column) {
        super(line, column);
        this.expr = expr;
    }
    
    /// compile time ///

    public int getSize() { return 0; }

    public void pass1() {}

    public String getName() { return ""; }

    // org only changes current address
    // if expr isnt valuable, then error exception is thrown
    // it cant help even more passes, because its recursive:
    // org label
    // mvi a,50
    // label: hlt
    // label address cant be evaluated
    public int pass2(compileEnv parentEnv, int addr_start) throws Exception {
        int val = addr_start;
        try { val = expr.eval(parentEnv, addr_start); }
        catch(NeedMorePassException e) {
            throw new Exception("[" + line + "," + column 
                    + "] ORG expression can't be ambiguous");
        }
        return val;
    }

    public void pass4(HEXFileHandler hex) throws Exception {
        hex.setNextAddress(expr.getValue());
    }
    
}
