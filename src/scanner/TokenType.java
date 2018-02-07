package scanner;

/**
 * @author nikaylahwoody
 *
 */
public enum TokenType {
	
	AND, ARRAY, BEGIN,  DIV, DO,ELSE, END, FUNCTION, IF, INTEGER, MOD, NOT, OF, OR, PROCEDURE, PROGRAM, REAL, THEN, VAR, WHILE, //KEYWORDS
	
	
	ID, NUMBER, //TYPES
	
	//SYMBOLS
	SEMICOLON, COMMA, PERIOD, COLON, LEFTBRACE, RIGHTBRACE, LEFTPARA, RIGHTPARA, PLUS, MINUS, EQUAL, ASSIGN, SLASH, NOTEQUAL, LESSTHAN, LESSTHANEQUAL, GREATERTHAN, GREATERTHANEQUAL, ASTERISK
}

