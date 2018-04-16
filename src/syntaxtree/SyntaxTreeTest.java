package syntaxtree;

import scanner.TokenType;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * JUnit testing for the SyntaxTree stand-alone.
 *
 * @author nikaylahwoody
 */
public class SyntaxTreeTest {

	/**
	 * creating a syntaxtree test for the bitcoin example /comparing the indentedToString() to the expected tree.
	 */
	@Test
	public void testBitcoinExample() {
		ProgramNode programNode = new ProgramNode("sample");
		
		//Declarations Node
		DeclarationsNode declarationsNode = new DeclarationsNode();
		
		VariableNode bitcoins = new VariableNode("bitcoins");
		VariableNode dollars = new VariableNode("dollars");
		VariableNode euros = new VariableNode("euros");
		
		
		declarationsNode.addVariable(bitcoins);
		declarationsNode.addVariable( dollars );
		declarationsNode.addVariable( euros );
		
		
		programNode.setVariables(declarationsNode);
		
		CompoundStatementNode cSNode = new CompoundStatementNode();
		
		AssignmentStatementNode aSNDollars = new AssignmentStatementNode();
		aSNDollars.setLvalue( dollars );
		aSNDollars.setExpression(new ValueNode("1000000"));
		
		
		AssignmentStatementNode aSNEuros = new AssignmentStatementNode();
		aSNEuros.setLvalue(euros);
		OperationNode onMultiply = new OperationNode(TokenType.MULTIPLY);
		onMultiply.setLeft( dollars );
		onMultiply.setRight(new ValueNode("102"));
		aSNEuros.setExpression(onMultiply);
		
		
		AssignmentStatementNode aSNBitcoins = new AssignmentStatementNode();
		aSNBitcoins.setLvalue( bitcoins );
		OperationNode onDivide = new OperationNode(TokenType.DIV);
		onDivide.setLeft( dollars );
		onDivide.setRight(new ValueNode("400"));
		aSNBitcoins.setExpression(onDivide);
		
		cSNode.addStatement(aSNDollars);
		cSNode.addStatement(aSNEuros);
		cSNode.addStatement(aSNBitcoins);
		
		programNode.setMain(cSNode);
		
		// SubProgramDeclarationsNode
		SubProgramDeclarationsNode sPDNode = new SubProgramDeclarationsNode();
		
		programNode.setFunctions(sPDNode);
		
		// Compare the strings
		String expectedTree = "Program: sample\n" + 
				"|-- Declarations\n" + 
				"|-- --- Name: bitcoins\n" + 
				"|-- --- Name: dollars\n" + 
				"|-- --- Name: euros\n" + 
				"|-- SubProgramDeclarations\n" + 
				"|-- Compound Statement\n" + 
				"|-- --- Assignment\n" + 
				"|-- --- --- Name: dollars\n" + 
				"|-- --- --- Value: 1000000\n" + 
				"|-- --- Assignment\n" + 
				"|-- --- --- Name: euros\n" + 
				"|-- --- --- Operation: MULTIPLY\n" + 
				"|-- --- --- --- Name: dollars\n" + 
				"|-- --- --- --- Value: 102\n" + 
				"|-- --- Assignment\n" + 
				"|-- --- --- Name: bitcoins\n" + 
				"|-- --- --- Operation: DIV\n" + 
				"|-- --- --- --- Name: dollars\n" + 
				"|-- --- --- --- Value: 400\n";
		
		String returnedTree = programNode.indentedToString(0);
		
		assertEquals(expectedTree, returnedTree);
	}
}
