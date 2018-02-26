/**
 * Nikaylah W
 * Scanner.jflex
 */

package scanner;
import java.util.HashMap;

/* Declarations */

%%

%class Scanner /* Name of java file */
%function nextToken /* Renames the yylex() function */
%unicode
%public
%type TokenType

%eofval{
	return null;
%eofval}

%{
	private HashMap<String, TokenType> Types;
%}

%init{
    types = new HashMap<>();
    types.put("and", Type.AND);
    types.put("array", Type.ARRAY);
    types.put("begin", Type.BEGIN);
    types.put("div", Type.DIV);
    types.put("do", Type.DO);
    types.put("else", Type.ELSE);
    types.put("end", Type.END);
    types.put("function", Type.FUNCTION);
    types.put("if", Type.IF);
    types.put("integer", Type.INTEGER);
    types.put("mod", Type.MOD);
    types.put("not", Type.NOT);
    types.put("of", Type.OF);
    types.put("or", Type.OR);
    types.put("procedure", Type.PROCEDURE);
    types.put("program", Type.PROGRAM);
    types.put("real", Type.REAL);
    types.put("then", Type.THEN);
    types.put("var", Type.VAR);
    types.put("while", Type.WHILE);
    types.put(";", Type.SEMICOLON);
    types.put(",", Type.COMMA);
    types.put(".", Type.PERIOD);
    types.put(":", Type.COLON);
    types.put("[", Type.LEFTBRACE);
    types.put("]", Type.RIGHTBRACE);
    types.put("(", Type.LEFTPARA);
    types.put(")", Type.RIGHTPARA);
    types.put("+", Type.PLUS);
    types.put("-", Type.MINUS);
    types.put("=", Type.EQUAL);
    types.put("<>", Type.DIAMOND);
    types.put("<", Type.LESSTHAN);
    types.put("<=", Type.LESSTHANEQUAL);
    types.put(">", Type.GREATERTHAN);
    types.put(">=", Type.GREATERTHANEQUAL);
    types.put("*", Type.ASTERISK);
    types.put("/", Type.SLASH);
    types.put(":=", Type.ASSIGN);
%init}


//Patterns
other               = .
letter              = [A-Za-z]
digit               = [0-9]
digits              = {digit}{digit}*
optional_fraction   = (\.{digits})?
optional_exponent   = ((E[\+\-]?){digits})?
num                 = {digits}{optional_fraction}{optional_exponent}
id                  = {letter}({letter} | {digit})*
symbol              = [=<>+\-*/;,.\[\]():]
symbols             = {symbol}|:=|<=|>=|<>
commentContent      = [^\{\}]
comment             = \{{commentContent}*\}
whitespace          = [ \n\t\r\f]|{comment}

%%


/* Lexical Rules */
{id}        {
                String lexeme = yytext();
                TokenType type = Types.get(lexeme);

                if(type != null)  // if lexeme is in hashmap, lexeme is a keyword
                    return (new Token(lexeme, type));
                // Otherwise lexeme is an ID
                return(new Token(lexeme, TokenType.ID));
            }

{symbol}   {
                return(new Token(yytext(), Types.get(yytext())));
            }

{num}       {
                return(new Token(yytext(), Type.NUMBER));
            }

{whitespace} {
                // ignore whitespace
            }

{other}     {
                //display an error message and exit when the error is found
                System.out.println("invalid syntax found.");
                System.exit(1);
            }
