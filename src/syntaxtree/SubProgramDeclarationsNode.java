
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations.
 * @author Erik
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {
    
    private ArrayList<SubProgramNode> procs = new ArrayList<SubProgramNode>();
    
    public void addSubProgramDeclaration(SubProgramNode aSubProgram) {
        procs.add(aSubProgram);
    }
    
    public void addAll(ArrayList<SubProgramNode> aSubProgram){
    	procs.addAll(aSubProgram);
    }
    
    public ArrayList<SubProgramNode> getProcs(){
    	return procs;
    }
    
    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation( level);
        answer += "SubProgramDeclarations\n";
        for( SubProgramNode subProg : procs) {
            answer += subProg.indentedToString( level + 1);
        }
        return answer.toString();
    }
    
  
    
}
