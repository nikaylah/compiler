/**
 * Nikaylah W
 * Scanner.jflex
 */

/* Declarations */


%%

%class  Scanner   /* Name of java file */
%function nextToken /* Renames the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}
/* Patterns */

other         = .
letter        = [A-Za-z]
word          = {letter}+
whitespace    = [ \n\t]

%%
/* Lexical Rules */

{word}     {
             /** Print out the word that was found. */
             //System.out.println("Found a word: " + yytext());
             return( new Token( yytext()));
            }

{whitespace}  {  /* Ignore Whitespace */

              }

{other}    {
             System.out.println("Illegal char: '" + yytext() + "' found.");
           }
