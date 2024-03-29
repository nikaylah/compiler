package syntaxtree;

/**
 * Represents a single assignment statement.
 * @author Eric
 */
public class AssignmentStatementNode extends StatementNode {
    
    private VariableNode lvalue;
    private ExpressionNode expression;

    public VariableNode getLvalue() {
        return lvalue;
    }

    public void setLvalue(VariableNode lvalue) {
        this.lvalue = lvalue;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    

    
    /**
     * Creates a String representation of this assignment statement node 
     * and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString( int level) {
        String answer = this.indentation( level);
        answer += "Assignment\n";
        answer += this.lvalue.indentedToString( level + 1);
        answer += this.expression.indentedToString( level + 1);
        return answer;
    }
}