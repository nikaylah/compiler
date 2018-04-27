package parser;
//fix it under sub program declaration and anything that uses the id, us the addfunction, addprocedure and such and such stuff

import scanner.Scanner;

import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;
import syntaxtree.ProgramNode;

import java.io.*;
import java.util.ArrayList;
import static scanner.TokenType.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.StringReader;

/**
 * @author nikaylahwoody This parser will recognize whether an input string of
 *         tokens is a valid Mini-Pascal program (which is defined in the
 *         grammer)
 */
public class Parser {

	// Instance Variables
	private Token lookahead;
	private Scanner scanner;
	private SymbolTable symTable;

	// Constructors

	public Parser(String filename, boolean b) {

		symTable = new SymbolTable();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
		} catch (FileNotFoundException ex) {
			error("No file");
		}
		InputStreamReader isr = new InputStreamReader(fis);
		scanner = new Scanner(isr);

		try {
			lookahead = scanner.nextToken();
		} catch (IOException ex) {
			error("Scan error");
		}

		symTable = new SymbolTable();
	}

	// Methods

	/**
	 * executes the rule for the program symbol in the expression grammer to see
	 * the program symbol
	 */
	public ProgramNode program() {

		match(TokenType.PROGRAM);
		String name = lookahead.getLexeme(); // ----use this and add it to those
												// 4 stops thats use id
		match(TokenType.ID);
		if (!symTable.addProgram(name))
			error("This name already exists in symbol table");
		ProgramNode program = new ProgramNode(name);
		match(TokenType.SEMICOLON);
		// errors should occur if it is not the program token type
		program.setVariables(declarations());
		program.setFunctions(subprogram_declarations());
		program.setMain(compound_statement());
		match(TokenType.PERIOD);
		System.out.println(program.indentedToString(0));
		return program;
	}

	public ArrayList<String> identifier_list() {
		ArrayList<String> idList = new ArrayList<>();
		idList.add(lookahead.getLexeme());
		match(TokenType.ID);
		if (lookahead.getTokenType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			idList.addAll(identifier_list());
		}
		// else lambda case
		return idList;
	}
	
	
	/**
	 * @return
	 */
	public DeclarationsNode declarations() {
		DeclarationsNode declarations = new DeclarationsNode();
		if (lookahead.getTokenType() == VAR) {
			match(TokenType.VAR);
			ArrayList<String> IDList = identifier_list();
			for (String id : IDList) {
				declarations.addVariable(new VariableNode(id));
			}
			match(TokenType.COLON);
			type(IDList);
			match(TokenType.SEMICOLON);
			declarations.addDeclarations(declarations());
		}
		return declarations;
	}

	/**
	 * @param idList with an arraylist of names added to symbol table
	 * @return type
	 */
	/**
	 * @param idList
	 */
	public void type(ArrayList<String> idList) {
		int beginidx, endidx;
		if (lookahead.getTokenType() == TokenType.INTEGER || lookahead.getTokenType() == TokenType.REAL) {
			standard_type();
		} else {
			match(TokenType.ARRAY);
			match(TokenType.LEFTBRACE);
			beginidx = Integer.parseInt(lookahead.getLexeme());
			match(TokenType.NUMBER);
			match(TokenType.COLON);
			endidx = Integer.parseInt(lookahead.getLexeme());
			match(TokenType.NUMBER);
			match(TokenType.RIGHTBRACE);
			match(TokenType.OF);
			TokenType t = standard_type();
			//symantic
            for (String anIdList : idList) {
                if (!symTable.addArray(anIdList, t, beginidx, endidx))
                    error(anIdList + " already exists in symbol table");
            }
			if (lookahead.getTokenType() == INTEGER || lookahead.getTokenType() == REAL){
			t = standard_type();
            for (String anIdList : idList) {
                if (!symTable.addVariable(anIdList, t)) error(anIdList + " already exists in symbol table");
            }
			}else
				error("type");
		}

	}
	
	/**
	 * @return the declared item type
	 */
	public TokenType standard_type() {
		TokenType t = null;
		if (lookahead.getTokenType() == INTEGER) {
			t = INTEGER;
			match(INTEGER);
		} else if (lookahead.getTokenType() == REAL) {
			t = REAL;
			match(REAL);
		} else
			error("standard_type");
		return t;
	}

	
	/**
	 * @return subprogramnode for a declared function and procedure
	 */
	public SubProgramDeclarationsNode subprogram_declarations() {
        SubProgramDeclarationsNode subDecNode = new SubProgramDeclarationsNode();
if (lookahead.getTokenType() == TokenType.FUNCTION || lookahead.getTokenType() == TokenType.PROCEDURE) {
	subDecNode.addSubProgramDeclaration(subprogram_declaration());
	match(TokenType.SEMICOLON);
	subDecNode.addAll(subprogram_declarations().getProcs());
}
// else lambda case
return subDecNode;
}


	/**
	 * @return subprogramnode for a declared function and procedure
	 */
	public SubProgramNode subprogram_declaration() {
		SubProgramNode subprogramDec = subprogram_head();
		subprogramDec.setVariables(declarations());
		subprogramDec.setFunctions(subprogram_declarations());
		subprogramDec.setMain(compound_statement());
		return subprogramDec;
	}


	/**
	 * @return subprogramnode for a declared function and procedure
	 */
	public SubProgramNode subprogram_head() {
		SubProgramNode subprogramHead = null;
		if (lookahead.getTokenType() == TokenType.FUNCTION) {
			match(TokenType.FUNCTION);
			String funcName = lookahead.getLexeme();
			if (!symTable.addFunction(funcName, null)) error(funcName + " already exists in symbol table");
			subprogramHead = new SubProgramNode(funcName);
			match(TokenType.ID);
			arguments();
			match(TokenType.COLON);
			TokenType t = standard_type();
			symTable.addFunction(funcName, t);
			match(TokenType.SEMICOLON);
		} else if (lookahead.getTokenType() == PROCEDURE) {
			match(PROCEDURE);
			String procedureName = lookahead.getLexeme();
			subprogramHead = new SubProgramNode(procedureName);
			if (!symTable.addProcedure(procedureName)) error(procedureName + " already exists in symbol table");
			match(TokenType.ID);
			arguments();
			symTable.addProcedure(procedureName);
			match(TokenType.SEMICOLON);
		} else
			error("subprogram_head");
		return subprogramHead;
	}
	

	/**
	 * @return
	 */
	public ArrayList<VariableNode> arguments() {
		 ArrayList<VariableNode> args = new ArrayList();
		if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			parameter_list();
			match(TokenType.RIGHTPARA);
		}
		return args;
	}
	
	
	/**
	 * @return
	 */
	public ArrayList<VariableNode> parameter_list() {
		ArrayList<String> idList = identifier_list();
		ArrayList<VariableNode> args = new ArrayList();
		match(TokenType.COLON);
		if (lookahead.getTokenType() == TokenType.SEMICOLON) {
			match(TokenType.SEMICOLON);
			args.addAll(parameter_list());
		}
		return args;
	}


	/**
	 * @return compound statement for the function and procedure
	 */
	public CompoundStatementNode compound_statement() {
		CompoundStatementNode compound;
		match(TokenType.BEGIN);
		compound = optional_statements();
		match(TokenType.END);
		return compound;
	}
	

	/**
	 * @return compound statement for the function and procedure
	 */
	public CompoundStatementNode optional_statements() {
		CompoundStatementNode comp = new CompoundStatementNode();
		if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.BEGIN
				|| lookahead.getTokenType() == TokenType.IF || lookahead.getTokenType() == TokenType.WHILE)
			comp.addAll(statement_list());
		return comp;

	}

	/**
	 * @return arraylist of statement nodes
	 */
	public ArrayList<StatementNode> statement_list() {
		ArrayList<StatementNode> stateNode = new ArrayList();
		stateNode.add(statement());
		if (lookahead.getTokenType() == TokenType.SEMICOLON) {
			match(TokenType.SEMICOLON);
			stateNode.addAll(statement_list());
		}
		return stateNode;
	}

	/**
	 * @return state for a statement
	 */
	public StatementNode statement() {
		StatementNode state = null;
		if (lookahead.getTokenType() == TokenType.ID) {
			if (!symTable.doesExist(lookahead.getLexeme())) error(lookahead.getLexeme() + " has not been declared");
			if (symTable.isVariableName(lookahead.getLexeme())) {
				AssignmentStatementNode assign = new AssignmentStatementNode();
				assign.setLvalue(variable());
				match(TokenType.ASSIGN);
				assign.setExpression(expression());
				return assign;

			} else if (symTable.isProcedureName(lookahead.getLexeme())) {
				return procedure_statement();
			} else
				error(lookahead.getLexeme() + " not found in symbol table.");
		} else if (lookahead.getTokenType() == TokenType.BEGIN)
			state = compound_statement();
		else if (lookahead.getTokenType() == TokenType.IF) {
			IfStatementNode ifNode = new IfStatementNode();
			match(TokenType.IF);
			ifNode.setTest(expression());
			match(TokenType.THEN);
			ifNode.setThenStatement(statement());
			match(TokenType.ELSE);
			ifNode.setElseStatement(statement());

			return ifNode;
			
		} else if (lookahead.getTokenType() == TokenType.WHILE) {
			WhileStatementNode whileNode = new WhileStatementNode();
			match(TokenType.WHILE);
			whileNode.setTest(expression());
			match(TokenType.DO);
			whileNode.setDoStatement(statement());
			return whileNode;
		} else {
			error("statement");
		}

		return state;
	}

	/**
	 * @return variable
	 */
	public VariableNode variable() {
		VariableNode variable = new VariableNode(lookahead.getLexeme());
		match(ID);
		if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
			match(TokenType.LEFTBRACE);
			expression();
			match(TokenType.RIGHTBRACE);
		}

		return variable;
	}

	/**
	 * @return procedureStateNode
	 */
	public ProcedureStatementNode procedure_statement() {
		ProcedureStatementNode procedureStateNode = new ProcedureStatementNode();
		String ProcedureName = lookahead.getLexeme();
		procedureStateNode.setVariable(new VariableNode(lookahead.getLexeme()));
		match(TokenType.ID);
		if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			procedureStateNode.addAllExpNode(expression_list());
			match(TokenType.RIGHTPARA);
		}
		symTable.addProcedure(ProcedureName);
		return procedureStateNode;
	}

	/**
	 * @return exressNodeList as an arraylist of expression nodes
	 */
	public ArrayList<ExpressionNode> expression_list() {
		ArrayList<ExpressionNode> exressNodeList = new ArrayList();
		exressNodeList.add(expression());
		if (lookahead.getTokenType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			exressNodeList.addAll(expression_list());
		}
		return exressNodeList;
	}

	/**
	 * @return an expression node
	 */
	public ExpressionNode expression() {
		ExpressionNode left = simple_expression();
		TokenType leftType = left.getType();
		if (isRelop(lookahead.getTokenType())) {
			OperationNode operationNode = new OperationNode(lookahead.getTokenType());
			if (leftType .equals(TokenType.REAL)) operationNode.setType(TokenType.REAL);
			else operationNode.setType(TokenType.INTEGER);
			operationNode.setLeft(left);
			match(lookahead.getTokenType());
			operationNode.setRight(simple_expression());
			return operationNode;
		}
		return left;
	}

	/**
	 * @return an expression node
	 */
	public ExpressionNode simple_expression() {
		ExpressionNode express = null;
		if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.NUMBER
				|| lookahead.getTokenType() == TokenType.LEFTPARA || lookahead.getTokenType() == TokenType.NOT) {
			express = term();
			express = simple_part(express);
		} else if (lookahead.getTokenType() == TokenType.PLUS || lookahead.getTokenType() == TokenType.MINUS) {
			UnaryOperationNode unaryNode = sign();
			express = term();
			unaryNode.setExpression(simple_part(express));
			return express;
		} else
			error("simple_expression");
		return express;
	}

	/**
	 * @param an expression node
	 * @return
	 */
	public ExpressionNode simple_part(ExpressionNode positionLeft) {
		if (isAddop(lookahead.getTokenType())) {
			OperationNode operation = new OperationNode(lookahead.getTokenType());
			match(lookahead.getTokenType());
			ExpressionNode right = term();
			operation.setLeft(positionLeft);
			operation.setRight(right);
			return simple_part(operation);

		} else {
			return positionLeft;
		}
	}

	/**
	 * @return an expression node
	 */
	public ExpressionNode term() {
		ExpressionNode left = factor();
		return term_part(left);
	}

	/**
	 * @param posLeft
	 * @return an expression node
	 */
	public ExpressionNode term_part(ExpressionNode posLeft) {
		if (isMulop(lookahead.getTokenType())) {
			OperationNode operation = new OperationNode(lookahead.getTokenType());
			match(lookahead.getTokenType());
			ExpressionNode right = factor();
			operation.setLeft(posLeft);
			operation.setRight(term_part(right));
		}

		return posLeft;

	}

	/**
	 * @return expression node
	 */
	public ExpressionNode factor() {
		ExpressionNode express = null;
		express = new VariableNode(lookahead.getLexeme());
		match(TokenType.ID);

		if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
			match(TokenType.LEFTBRACE);
			expression();
			match(TokenType.RIGHTBRACE);

		} else if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			expression_list();
			match(TokenType.RIGHTPARA);
		} else if (lookahead.getTokenType() == TokenType.NUMBER) {
			TokenType t;
			express = new ValueNode(lookahead.getLexeme());
			if (express.contains(".")) t = TokenType.REAL;
			else t = TokenType.INTEGER;
			match(TokenType.NUMBER);
			return express;
		}

		else if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			express = expression();
			match(TokenType.RIGHTPARA);

		} else if (lookahead.getTokenType() == TokenType.NOT) {
			UnaryOperationNode unaryNode = new UnaryOperationNode(NOT);
			match(TokenType.NOT);
			unaryNode.setExpression(factor());
			return unaryNode;
		} else
			error("factor");
		return express;
	}

	/**
	 * @return unaryoperationnode containing not, + or - and the expression
	 */
	public UnaryOperationNode sign() {
		UnaryOperationNode UnaryOPNode = null;
		if (lookahead.getTokenType() == PLUS) {
			UnaryOPNode = new UnaryOperationNode(PLUS);
			match(TokenType.PLUS);
		} else if (lookahead.getTokenType() == MINUS) {
			UnaryOPNode = new UnaryOperationNode(MINUS);
			match(TokenType.MINUS);
		} else
			error("sign");
		return UnaryOPNode;
	}

	public void Relop() {
		if (lookahead.getTokenType() == TokenType.EQUAL) {
			match(TokenType.EQUAL);
		}
		if (lookahead.getTokenType() == TokenType.NOTEQUAL) {
			match(TokenType.NOTEQUAL);
		}
		if (lookahead.getTokenType() == TokenType.LESSTHAN) {
			match(TokenType.LESSTHAN);
		}
		if (lookahead.getTokenType() == TokenType.LESSTHANEQUAL) {
			match(TokenType.LESSTHANEQUAL);
		}
		if (lookahead.getTokenType() == TokenType.GREATERTHANEQUAL) {
			match(TokenType.GREATERTHANEQUAL);
		}
		if (lookahead.getTokenType() == TokenType.GREATERTHAN) {
			match(TokenType.GREATERTHAN);
		}
	}

	public void Addop() {
		if (lookahead.getTokenType() == TokenType.PLUS) {
			match(TokenType.PLUS);
		}
		if (lookahead.getTokenType() == TokenType.MINUS) {
			match(TokenType.MINUS);
		}
		if (lookahead.getTokenType() == TokenType.OR) {
			match(TokenType.OR);
		}

	}

	/**
	 * 
	 */
	public void Mulop() {
		if (lookahead.getTokenType() == TokenType.ASTERISK) {
			match(TokenType.ASTERISK);
		}
		if (lookahead.getTokenType() == TokenType.SLASH) {
			match(TokenType.SLASH);
		}
		if (lookahead.getTokenType() == TokenType.DIV) {
			match(TokenType.DIV);
		}
		if (lookahead.getTokenType() == TokenType.MOD) {
			match(TokenType.MOD);
		}
		if (lookahead.getTokenType() == TokenType.AND) {
			match(TokenType.AND);
		}
	}

	/**
	 * @param t
	 * @return
	 */
	public boolean isRelop(TokenType t) {
		if (t == TokenType.EQUAL || t == TokenType.NOTEQUAL || t == TokenType.LESSTHAN || t == TokenType.LESSTHANEQUAL
				|| t == TokenType.GREATERTHANEQUAL || t == TokenType.GREATERTHAN)
			return true;
		return false;
	}

	public boolean isAddop(TokenType t) {
		if (t == TokenType.PLUS || t == TokenType.MINUS || t == TokenType.OR)
			return true;
		return false;
	}

	public boolean isMulop(TokenType t) {
		if (t == TokenType.ASTERISK || t == TokenType.SLASH || t == TokenType.DIV || t == TokenType.MOD
				|| t == TokenType.AND)
			return true;
		return false;
	}

	/**
	 * matches the expected token
	 *
	 * @param expected
	 *            the expected tokenType.
	 */
	public void match(TokenType expected) {
		System.out.println("Match " + expected + " " + lookahead.getLexeme());
		if (this.lookahead.getTokenType() == expected) {
			try {
				this.lookahead = scanner.nextToken();
				if (this.lookahead == null) {
					this.lookahead = new Token("End of File", null, 0); // *****
				}
			} catch (IOException ex) {
				error("Scanner exception");
			}
		} else {
			error("Match of " + expected + " found " + this.lookahead.getTokenType() + " instead.");
		}
	}

	/**
	 * prints an error message then exits the program.
	 *
	 * @param message
	 *            prints error message
	 */
	public void error(String message) {
		System.out.println("Error " + message);
		System.exit(1);
	}

	public SymbolTable getSymbolTable() {
		return symTable;
	}

}
