package syntaxtree;
import java.util.ArrayList;



public class ProcedureStatementNode extends StatementNode {

	private VariableNode variable = null;
	private ArrayList<ExpressionNode> express = new ArrayList();
	
	public void addExpNode(ExpressionNode input) {
		express.add(input);
		    }
	public void addAllExpNode(ArrayList<ExpressionNode> input) {
		express.addAll(input);
			}
	public VariableNode getVariable() {
		        return this.variable;
	}
	public void setVariable(VariableNode input) {
		        this.variable = input;
		  }
	public ArrayList<ExpressionNode> getExpNode() {
		        return express;
		    }
	 public void setExpNode(ArrayList<ExpressionNode> input) {
		         this.express = input;
		     }
	 @Override
	     public String indentedToString(int level) {
	         String answer = this.indentation(level);
	         answer += "Procedure:";
	         answer += this.variable + "/n";
	         for (ExpressionNode exp : express) {
	             answer += exp.indentedToString(level + 1);
	         }
	         return answer;
	     }
	 } 
