package scanner;

import java.util.HashMap;

public class LookupTable extends HashMap<String, TokenType>{

	  public LookupTable() {

	    this.put("and", TokenType.AND);
	    this.put("array", TokenType.ARRAY);
	    this.put("begin", TokenType.BEGIN);
	    this.put("div", TokenType.DIV);
	    this.put("do", TokenType.DO);
	    this.put("else", TokenType.ELSE);
	    this.put("end", TokenType.END);
	    this.put("function", TokenType.FUNCTION);
	    this.put("if", TokenType.IF);
	    this.put("integer", TokenType.INTEGER);
	    this.put("mod", TokenType.MOD);
	    this.put("not", TokenType.NOT);
	    this.put("of", TokenType.OF);
	    this.put("or", TokenType.OR);
	    this.put("procedure", TokenType.PROCEDURE);
	    this.put("program", TokenType.PROGRAM);
	    this.put("real", TokenType.REAL);
	    this.put("then", TokenType.THEN);
	    this.put("var", TokenType.VAR);
	    this.put("while", TokenType.WHILE);
	    this.put(";", TokenType.SEMICOLON);
	    this.put(",", TokenType.COMMA);
	    this.put(".", TokenType.PERIOD);
	    this.put(":", TokenType.COLON);
	    this.put("[", TokenType.LEFTBRACE);
	    this.put("]", TokenType.RIGHTBRACE);
	    this.put("(", TokenType.LEFTPARA);
	    this.put(")", TokenType.RIGHTPARA);
	    this.put("+", TokenType.PLUS);
	    this.put("-", TokenType.MINUS);
	    this.put("=", TokenType.EQUAL);
	    this.put("<>", TokenType.NOTEQUAL);
	    this.put("<", TokenType.LESSTHAN);
	    this.put("<=", TokenType.LESSTHANEQUAL);
	    this.put(">", TokenType.GREATERTHAN);
	    this.put(">=", TokenType.GREATERTHANEQUAL);
	    this.put("*", TokenType.ASTERISK);
	    this.put("/", TokenType.SLASH);
	    this.put(":=", TokenType.ASSIGN);
	    
	  }
}
