/**
 * Nikaylah W
 * Scanner.jflex
 */

package scanner;
import java.util.HashMap;

/* Declarations */

%%



/*JFelx Directives */


%class Scanner /* Name of java file */
%function nextToken /* Renames the yylex() function */
%unicode
%public
%type TokenType
%line  /* turn on line counting for parser */
%column /* returns one end of the file*/

%eofval{
	return null;
%eofval}

%{
/* returns current line number*/
 public int getLine() { return yyline; }
 
 
 /* returns current column number */
 public int getColumn() { return yycolumn; }

 LookupTable LT = new LookupTable();
%}

%{
	private HashMap<String, TokenType> Types;
%}

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
