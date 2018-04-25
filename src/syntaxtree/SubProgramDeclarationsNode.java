
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations.
 * @author Erik
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {
    
    private ArrayList<SubProgramDeclarationsNode> procs = new ArrayList<SubProgramDeclarationsNode>();
    
    public void addSubProgramDeclaration(SubProgramNode subProgramNode) {
        procs.add( subProgramNode);
    }
    
    public void addAll(SubProgramDeclarationsNode aSubProgram){
    	procs.add(aSubProgram);
    }
    
    public ArrayList<SubProgramDeclarationsNode> getProcs(){
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
        for( SubProgramDeclarationsNode subProg : procs) {
            answer += subProg.indentedToString( level + 1);
        }
        return answer;
    }
    
  
    
}
