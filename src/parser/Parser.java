package parser;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

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
 * tokens is a valid Mini-Pascal program as defined in the grammer
 */
public class Parser {

	// Instance Variables
	private Token lookahead;
	private Scanner scanner;

	// Constructors

	public Parser(String filename) {
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
	}

	// Methods

	/**
	 * Executes the rule for the program symbol in the expression grammer We
	 * want to see the program symbol
	 */
	public void program() {

		if (lookahead.getTokenType() == TokenType.PROGRAM)
			match(TokenType.PROGRAM);
		else
			error("program #1");
		if (lookahead.getTokenType() == TokenType.ID)
			match(TokenType.ID);
		else
			error("program #2");
		if (lookahead.getTokenType() == TokenType.SEMICOLON)
			match(TokenType.SEMICOLON);
		else
			error("program #3");
		// errors should occur if it is not the program token type
		declarations();
		subprogram_declarations();
		compound_statement();

		if (lookahead.getTokenType() == TokenType.PERIOD)
			match(TokenType.PERIOD);
		else
			error("program #4");

	}

	public void identifier_list() {
		if (lookahead.getTokenType() == TokenType.ID) {
			match(TokenType.ID);
			if (lookahead.getTokenType() == TokenType.COMMA) {
				match(TokenType.COMMA);
				identifier_list();
			}

		} else
			error("identifier_list #2");
	}

	public void declarations() {
		if (lookahead.getTokenType() == TokenType.VAR) {
			match(TokenType.VAR);
			identifier_list();
			if (lookahead.getTokenType() == TokenType.COLON) {
				match(TokenType.COLON);
				type();// reevaluate this one
				if (lookahead.getTokenType() == TokenType.SEMICOLON) {
					match(TokenType.SEMICOLON);
					declarations();
				} else
					error("declarations #3");
			} else
				error("declarations #2");
		} else
			error("declarations #1");
	}

	public void type() {
		if (lookahead.getTokenType() == TokenType.ARRAY) {
			match(TokenType.ARRAY);
			if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
				match(TokenType.LEFTBRACE);
				if (lookahead.getTokenType() == TokenType.NUMBER) {
					match(TokenType.NUMBER);
					if (lookahead.getTokenType() == TokenType.COLON) {
						match(TokenType.COLON);
						if (lookahead.getTokenType() == TokenType.NUMBER) {
							match(TokenType.NUMBER);
							if (lookahead.getTokenType() == TokenType.RIGHTBRACE) {
								match(TokenType.RIGHTBRACE);
								if (lookahead.getTokenType() == TokenType.OF) {
									match(TokenType.OF);
									standard_type();
								} else
									error("type 6");
							} else
								error("type 5");
						} else
							error("type 4");
					} else
						error("type 3");
				} else
					error("type 2");
			} else
				error("type 1");
		} else if (lookahead.getTokenType() == TokenType.INTEGER || lookahead.getTokenType() == TokenType.REAL)
			standard_type();
		else
			error("type 7");
	}

	public void standard_type() {
		if (lookahead.getTokenType() == TokenType.INTEGER)
			match(TokenType.INTEGER);
		else if (lookahead.getTokenType() == TokenType.REAL)
			match(TokenType.REAL);
		else
			error("standard_type");
	}

	public void subprogram_declarations() {
		if (lookahead.getTokenType() == TokenType.FUNCTION || lookahead.getTokenType() == TokenType.PROCEDURE) {
			subprogram_declaration();
			if (lookahead.getTokenType() == TokenType.SEMICOLON) {
				match(TokenType.SEMICOLON);
				subprogram_declarations();
			} else
				error("subprogram_declarations #2");
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
				if (lookahead.getTokenType() == TokenType.ID) {
					match(TokenType.ID);
					arguments();
					if (lookahead.getTokenType() == TokenType.COLON) {
						match(TokenType.COLON);
						standard_type();
						if (lookahead.getTokenType() == TokenType.SEMICOLON)
							match(TokenType.SEMICOLON);
						else
							error("subprogram_head #4");
					} else
						error("subprogram_head #3");
				} else
					error("subprogram_head #2");
			} else if (lookahead.getTokenType() == TokenType.PROCEDURE) {
				match(TokenType.PROCEDURE);
				if (lookahead.getTokenType() == TokenType.ID) {
					match(TokenType.ID);
					arguments();
					if (lookahead.getTokenType() == TokenType.SEMICOLON)
						match(TokenType.SEMICOLON);
					else
						error("subprogram_head #6");
				} else
					error("subprogram_head #5");
			} else
				error("subprogram_head #1");
		}

		public void arguments() {
			if (lookahead.getTokenType() == TokenType.LEFTPARA) {
				match(TokenType.LEFTPARA);
				parameter_list();
				if (lookahead.getTokenType() == TokenType.RIGHTPARA)
					match(TokenType.RIGHTPARA);
				else
					error("arguments");
			} else {
				// lambda case
			}
		}


		public void parameter_list() {
			if (lookahead.getTokenType() == TokenType.ID) {
				identifier_list();
				if (lookahead.getTokenType() == TokenType.COLON) {
					match(TokenType.COLON);
					type();
					if (lookahead.getTokenType() == TokenType.SEMICOLON) {
						match(TokenType.SEMICOLON);
						parameter_list();
					}
				} else
					error("parameter_list #1");
			} else
				error("parameter_list #3");
		}

		public void compound_statement() {
			if (lookahead.getTokenType() == TokenType.BEGIN) {
				match(TokenType.BEGIN);
				optional_statements();
				if (lookahead.getTokenType() == TokenType.END)
					match(TokenType.END);
				else
					error("compound_statement #2");
			} else
				error("compound_statement #1");
		}

		public void optional_statements() {
			if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.BEGIN || lookahead.getTokenType() == TokenType.IF || lookahead.getTokenType() == TokenType.WHILE)
				statement_list();
			else {
				// Lambda case
			}
		}

		public void statement_list() {
			if (lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.IF || lookahead.getTokenType() == TokenType.WHILE || lookahead.getTokenType() == TokenType.BEGIN)
				statement();
			if (lookahead.getTokenType() == TokenType.SEMICOLON) {
				match(TokenType.SEMICOLON);
				statement_list();
			}
		}

		public void statement() {
			if (lookahead.getTokenType() == TokenType.ID) {
				variable();
				if (lookahead.getTokenType() == TokenType.ASSIGN)
					match(TokenType.ASSIGN);
				else
					error("statement #1");
				expression();
			} else if (lookahead.getTokenType() == TokenType.BEGIN)
				compound_statement();
			else if (lookahead.getTokenType() == TokenType.IF) {
				match(TokenType.IF);
				expression();
				if (lookahead.getTokenType() == TokenType.THEN) {
					match(TokenType.THEN);
					statement();
					if (lookahead.getTokenType() == TokenType.ELSE) {
						match(TokenType.ELSE);
						statement();
					} else
						error("statement #3");
				} else
					error("statement #2");
			} else
				error("statement #4");
		}

		public void variable() {
			if (lookahead.getTokenType() == TokenType.ID) {
				match(TokenType.ID);
				if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
					match(TokenType.LEFTBRACE);
					expression();
					if (lookahead.getTokenType() == TokenType.RIGHTBRACE)
						match(TokenType.RIGHTBRACE);
					else
						error("variable #2");
				}

			} else
				error("variable #1");
		}




		public void procedure_statement() {
			if (lookahead.getTokenType() == TokenType.ID) {
				match(TokenType.ID);
				if (lookahead.getTokenType() == TokenType.LEFTPARA) {
					match(TokenType.LEFTPARA);
					expression_list();
					if (lookahead.getTokenType() == TokenType.RIGHTPARA)
						match(TokenType.RIGHTPARA);
					else
						error("procedure_statement #2");
				}

			} else
				error("procedure_statement #1");
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

		// How do we match relop??
		public void expression() {
			if (lookahead.getTokenType() == TokenType.PLUS || lookahead.getTokenType() == TokenType.MINUS || lookahead.getTokenType() == TokenType.ID || lookahead.getTokenType() == TokenType.NUMBER || lookahead.getTokenType() == TokenType.LEFTPARA || lookahead.getTokenType() == TokenType.NOT) {
				simple_expression();
				if (isRelOp(lookahead.getTokenType())) {
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

		// How do we match addop??
		public void simple_part() {
			if (isAddOp(lookahead.getTokenType())) {
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

		// How do we match mulop??
		public void term_part() {
			if (isMulOp(lookahead.getTokenType())) {
				match(lookahead.getTokenType());
				factor();
				term_part();
			} else {
				// lambda case
			}
		}

		public void factor() {
			if (lookahead.getTokenType() == TokenType.ID) {
				match(TokenType.ID);
				if (lookahead.getTokenType() == TokenType.LEFTBRACE) {
					match(TokenType.LEFTBRACE);
					expression();
					if (lookahead.getTokenType() == TokenType.RIGHTBRACE) {
						match(TokenType.RIGHTBRACE);
					} else
						error("factor #1");
				} else if (lookahead.getTokenType() == TokenType.LEFTPARA) {
					match(TokenType.LEFTPARA);
					expression_list();
					if (lookahead.getTokenType() == TokenType.RIGHTPARA)
						match(TokenType.RIGHTPARA);
					else
						error("factor #2");
				}
			} else if (lookahead.getTokenType() == TokenType.NUMBER)
				match(TokenType.NUMBER);
			else if (lookahead.getTokenType() == TokenType.LEFTPARA) {
				match(TokenType.LEFTPARA);
				expression();
				if (lookahead.getTokenType() == TokenType.RIGHTPARA)
					match(TokenType.RIGHTPARA);
				else
					error("factor #3");
			} else if (lookahead.getTokenType() == TokenType.NOT) {
				match(TokenType.NOT);
				factor();
			} else
				error("factor #4");
		}

		public void sign() {
			if (lookahead.getTokenType() == TokenType.PLUS)
				match(TokenType.PLUS);
			else if (lookahead.getTokenType() == TokenType.MINUS)
				match(TokenType.MINUS);
			else
				error("sign");
		}


		public boolean isRelOp(TokenType t) {
			if (t == TokenType.EQUAL || t == TokenType.NOTEQUAL || t == TokenType.LESSTHAN || t == TokenType.LESSTHANEQUAL || t == TokenType.GREATERTHANEQUAL || t == TokenType.GREATERTHAN)
				return true;
			return false;
		}

		public boolean isAddOp(TokenType t) {
			if (t == TokenType.PLUS || t == TokenType.MINUS || t == TokenType.OR)
				return true;
			return false;
		}

		public boolean isMulOp(TokenType t) {
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
		 * Prints an error message and then exits the program.
		 *
		 * @param message prints error message
		 */
		public void error(String message) {
			System.out.println("Error " + message);
			System.exit(1);
		}





	}
