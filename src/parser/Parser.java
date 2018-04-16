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
 * @author nikaylahwoody 
 * This parser will recognize whether an input string of
 * tokens is a valid Mini-Pascal program (which is defined in the grammer)
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
	 * executes the rule for the program symbol in the expression grammer to see the program symbol
	 */
	public ProgramNode program() {

		match(TokenType.PROGRAM);
		String name = lookahead.getLexeme(); //----use this and add it to those 4 stops thats use id
		match(TokenType.ID);
		if (!symTable.addProgram(name)) error("This name already exists in symbol table");
		match(TokenType.SEMICOLON);
		// errors should occur if it is not the program token type
		program.setVariables(declarations());
		program.setFunctions(subprogram_declarations());
		program.setMain(compound_statement());
		match(TokenType.PERIOD);
		
		//Will write syntax tree and it symbol table contents into files
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new

		FileOutputStream("src/compiler/output/" + name + ".tree"), "utf-8"))){
			writer.write(program.indentedToString(0));
		}catch(Exception ex){
			error("There is a problem with output file.");
		}
		
		try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/compiler/output/"+name+".table","utf-8")))){
			
		}catch (Exception ex){
			error("Problem with output filr.");
		}
		return program;
	}

	public void identifier_list() {
		String name = lookahead.getLexeme();
		match(TokenType.ID);
		if (!symTable.addProgram(name));

		if (lookahead.getTokenType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			identifier_list();
		}else{
			//lambda
		}

	}

	public void declarations() {
		if (lookahead.getTokenType() == TokenType.VAR) {
			match(TokenType.VAR);
			identifier_list();
			match(TokenType.COLON);
			type(null);// reevaluate this one
			match(TokenType.SEMICOLON);
			declarations();
		} else{
			//lambda
		}	
	}

	public void type(ArrayList<String> idList) {
		int beginidx, endidx;
		if(lookahead.getTokenType() == TokenType.INTEGER || lookahead.getTokenType() == TokenType.REAL){
			standard_type();
		}else{
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
			 if (lookahead.getTokenType() == INTEGER || lookahead.getTokenType() == REAL)
				standard_type();
			else
				error("type");
		}
		
		

	}

	public TokenType standard_type() {
		if (lookahead.getTokenType() == TokenType.INTEGER)
			match(TokenType.INTEGER);
		else if (lookahead.getTokenType() == TokenType.REAL)
			match(TokenType.REAL);
		else
			error("standard_type");
		return null;///ummm
	}

	public void subprogram_declarations() {
		if (lookahead.getTokenType() == TokenType.FUNCTION || lookahead.getTokenType() == TokenType.PROCEDURE) {
			subprogram_declaration();
			match(TokenType.SEMICOLON);
			subprogram_declarations();
		} else {
			error("subprogram_declarations");
		}

	}




	public void subprogram_declaration() {
		subprogram_head();
		declarations();
		subprogram_declarations();
		compound_statement();
	}

	public void subprogram_head() {
		if (lookahead.getTokenType() == TokenType.FUNCTION) {
			match(TokenType.FUNCTION);
			match(TokenType.ID);
			arguments();
			match(TokenType.COLON);
			standard_type();
			match(TokenType.SEMICOLON);
		} else {
			match(TokenType.PROCEDURE);
			match(TokenType.ID);
			arguments();
			match(TokenType.SEMICOLON);
		}


	}

	public void arguments() {
		if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			parameter_list();
			match(TokenType.RIGHTPARA);

		} else {
			// lambda case
		}
	}


	public void parameter_list() {
		identifier_list();
		match(TokenType.COLON);
		type(null);//and this one too
		if (lookahead.getTokenType() == TokenType.SEMICOLON) {
			match(TokenType.SEMICOLON);
			parameter_list();
		}


	}

	public void compound_statement() {
		match(TokenType.BEGIN);
		optional_statements();
		match(TokenType.END);


	}

	public void optional_statements() {
		if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.BEGIN || lookahead.getTokenType() == TokenType.IF || lookahead.getTokenType() == TokenType.WHILE)
			statement_list();
		else {
			// Lambda case
		}
	}

	public void statement_list() {
		statement();
		if (lookahead.getTokenType() == TokenType.SEMICOLON) {
			match(TokenType.SEMICOLON);
			statement_list();
		}
	}
	//where I'm at
	public void statement() {
		if (lookahead.getTokenType() == TokenType.ID) {
			if (symTable.isVariableName(lookahead.getLexeme())){
			variable();
			match(TokenType.ASSIGN);
			expression();
			}else if (symTable.isProgramName(lookahead.getLexeme())){
				procedure_statement();
			}else
				error("Name not found in symbol table");
		} else if (lookahead.getTokenType() == TokenType.BEGIN)
			compound_statement();
		else if (lookahead.getTokenType() == TokenType.IF) {
			match(TokenType.IF);
			expression();
			match(TokenType.THEN);
			statement();
			match(TokenType.ELSE);
			statement();
		}
		else if (lookahead.getTokenType() == TokenType.WHILE) {
			match(TokenType.WHILE);
			expression();
			match(TokenType.DO);
			statement();
		}
		else{
			//error
		}

		//what about read and write
	}

	public void variable() {
		match(TokenType.ID);
		if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
			match(TokenType.LEFTBRACE);
			expression();
			match(TokenType.RIGHTBRACE);
		}

	}

	//ignoring procedure statement
	public void procedure_statement() {
		match(TokenType.ID);
		if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			expression_list();
			match(TokenType.RIGHTPARA);
		}
	}

	public void expression_list() {
		if (lookahead.getTokenType() == TokenType.PLUS || lookahead.getTokenType() == TokenType.MINUS || lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.NUMBER || lookahead.getTokenType() == TokenType.LEFTPARA || lookahead.getTokenType() == TokenType.NOT) {
			expression();
			if (lookahead.getTokenType() == TokenType.COMMA) {
				match(TokenType.COMMA);
				expression_list();
			}
		}
	}


	public void expression() {
		if (lookahead.getTokenType() == TokenType.PLUS || lookahead.getTokenType() == TokenType.MINUS || lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.NUMBER || lookahead.getTokenType() == TokenType.LEFTPARA || lookahead.getTokenType() == TokenType.NOT) {
			simple_expression();
			if (isRelop(lookahead.getTokenType())) {
				match(lookahead.getTokenType());
				simple_expression();
			}
		}
	}

	public void simple_expression() {
		if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.NUMBER || lookahead.getTokenType() == TokenType.LEFTPARA || lookahead.getTokenType() == TokenType.NOT) {
			term();
			simple_part();
		} else if (lookahead.getTokenType() == TokenType.PLUS || lookahead.getTokenType() == TokenType.MINUS) {
			sign();
			term();
			simple_part();
		}
	}

	public void simple_part() {
		if (isAddop(lookahead.getTokenType())) {
			match(lookahead.getTokenType());
			term();
			simple_part();
		} else {
			// lambda case
		}
	}

	public void term() {
		factor();
		term_part();
	}

	public void term_part() {
		if (isMulop(lookahead.getTokenType())) {
			match(lookahead.getTokenType());
			factor();
			term_part();
		} else {
			// lambda case
		}
	}

	public void factor() {
		match(TokenType.ID);
		if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
			match(TokenType.LEFTBRACE);
			expression();
			match(TokenType.RIGHTBRACE);

		} else if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			expression_list();
			match(TokenType.RIGHTPARA);
		}else if (lookahead.getTokenType() == TokenType.NUMBER) {
			match(TokenType.NUMBER);
		}

		else if (lookahead.getTokenType() == TokenType.LEFTPARA) {
			match(TokenType.LEFTPARA);
			expression();
			match(TokenType.RIGHTPARA);

		} else if (lookahead.getTokenType() == TokenType.NOT) {
			match(TokenType.NOT);
			factor();
		} else
			error("factor");
	}

	public void sign() {
		if (lookahead.getTokenType() == TokenType.PLUS)
			match(TokenType.PLUS);
		else if (lookahead.getTokenType() == TokenType.MINUS)
			match(TokenType.MINUS);
		else
			error("sign");
	}

	public void Relop(){
		if(lookahead.getTokenType() == TokenType.EQUAL){
			match(TokenType.EQUAL);
		}
		if(lookahead.getTokenType() == TokenType.NOTEQUAL){
			match(TokenType.NOTEQUAL);
		}
		if(lookahead.getTokenType() == TokenType.LESSTHAN){
			match(TokenType.LESSTHAN);
		}
		if(lookahead.getTokenType() == TokenType.LESSTHANEQUAL){
			match(TokenType.LESSTHANEQUAL);
		}
		if(lookahead.getTokenType() == TokenType.GREATERTHANEQUAL){
			match(TokenType.GREATERTHANEQUAL);
		}
		if(lookahead.getTokenType() == TokenType.GREATERTHAN){
			match(TokenType.GREATERTHAN);
		}
	}

	public void Addop(){
		if(lookahead.getTokenType() == TokenType.PLUS){
			match(TokenType.PLUS);
		}
		if(lookahead.getTokenType() == TokenType.MINUS){
			match(TokenType.MINUS);
		}
		if(lookahead.getTokenType() == TokenType.OR){
			match(TokenType.OR);
		}

	}

	public void Mulop(){
		if(lookahead.getTokenType() == TokenType.ASTERISK){
			match(TokenType.ASTERISK);
		}
		if(lookahead.getTokenType() == TokenType.SLASH){
			match(TokenType.SLASH);
		}
		if(lookahead.getTokenType() == TokenType.DIV){
			match(TokenType.DIV);
		}
		if(lookahead.getTokenType() == TokenType.MOD){
			match(TokenType.MOD);
		}
		if(lookahead.getTokenType() == TokenType.AND){
			match(TokenType.AND);
		}
	}

	public boolean isRelop(TokenType t) {
		if (t == TokenType.EQUAL || t == TokenType.NOTEQUAL || t == TokenType.LESSTHAN || t == TokenType.LESSTHANEQUAL || t == TokenType.GREATERTHANEQUAL || t == TokenType.GREATERTHAN)
			return true;
		return false;
	}

	public boolean isAddop(TokenType t) {
		if (t == TokenType.PLUS || t == TokenType.MINUS || t == TokenType.OR)
			return true;
		return false;
	}

	public boolean isMulop(TokenType t) {
		if (t == TokenType.ASTERISK || t == TokenType.SLASH || t == TokenType.DIV || t == TokenType.MOD || t == TokenType.AND)
			return true;
		return false;
	}

	/**
	 * matches the expected token
	 *
	 * @param expected the expected tokenType.
	 */
	public void match(TokenType expected) {
		System.out.println("Match " + expected + " " + lookahead.getLexeme());
		if (this.lookahead.getTokenType() == expected) {
			try {
				this.lookahead = scanner.nextToken();
				if (this.lookahead == null) {
					this.lookahead = new Token("End of File", null, 0); //*****
				}
			} catch (IOException ex) {
				error("Scanner exception");
			}
		} else {
			error("Match of " + expected + " found " + this.lookahead.getTokenType()
			+ " instead.");
		}
	}

	/**
	 * prints an error message then exits the program.
	 *
	 * @param message prints error message
	 */
	public void error(String message) {
		System.out.println("Error " + message);
		System.exit(1);
	}


}
