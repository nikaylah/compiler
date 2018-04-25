package syntaxtree;

import scanner.TokenType;

/**
 * General representation of any expression.
 * @author Eric
 */
public abstract class ExpressionNode extends SyntaxTreeNode {
    
	protected TokenType type;
	
	public ExpressionNode(){
		type = null;
	}
	
	public ExpressionNode(TokenType t){
		this.type = t;
	}
	
	public TokenType getType(){
		return type;
	}
	
	public void setType(TokenType t){
		this.type = t;
	}
}
