//package scanner;
//
//
///**
// * @author nikaylahwoody
// *
// */
//public class Scanner {
//    // CONSTANTS
//    private static final int START = 0;
//    private static final int IN_ID = 1;
//    private static final int IN_NUM = 2;
//    
//    // completion states
//    private static final int ERROR = 10;
//    private static final int ID_COMPLETE = 11;
//    private static final int NUM_COMPLETE = 12;
//    private static final int SYMBOL_COMPLETE = 13;
//
//    // instance variables
//    private String source = "";
//    private int currentIndex = 0;
//    private HashMap<String,TokenType> types = new HashMap<String,TokenType>();
//
//    // constructor
//    public Scanner( String scan) {
//        this.source = scan;
//        
//        // the lookup table
//        types = new HashMap<>();
//        types.put("and", Type.AND);
//        types.put("array", Type.ARRAY);
//        types.put("begin", Type.BEGIN);
//        types.put("div", Type.DIV);
//        types.put("do", Type.DO);
//        types.put("else", Type.ELSE);
//        types.put("end", Type.END);
//        types.put("function", Type.FUNCTION);
//        types.put("if", Type.IF);
//        types.put("integer", Type.INTEGER);
//        types.put("mod", Type.MOD);
//        types.put("not", Type.NOT);
//        types.put("of", Type.OF);
//        types.put("or", Type.OR);
//        types.put("procedure", Type.PROCEDURE);
//        types.put("program", Type.PROGRAM);
//        types.put("real", Type.REAL);
//        types.put("then", Type.THEN);
//        types.put("var", Type.VAR);
//        types.put("while", Type.WHILE);
//        types.put(";", Type.SEMICOLON);
//        types.put(",", Type.COMMA);
//        types.put(".", Type.PERIOD);
//        types.put(":", Type.COLON);
//        types.put("[", Type.LEFTBRACE);
//        types.put("]", Type.RIGHTBRACE);
//        types.put("(", Type.LEFTPARA);
//        types.put(")", Type.RIGHTPARA);
//        types.put("+", Type.PLUS);
//        types.put("-", Type.MINUS);
//        types.put("=", Type.EQUAL);
//        types.put("<>", Type.DIAMOND);
//        types.put("<", Type.LESSTHAN);
//        types.put("<=", Type.LESSTHANEQUAL);
//        types.put(">", Type.GREATERTHAN);
//        types.put(">=", Type.GREATERTHANEQUAL);
//        types.put("*", Type.ASTERISK);
//        types.put("/", Type.SLASH);
//        types.put(":=", Type.ASSIGN);
//    }
//    
//    // instance functions
//    public Token nextToken() {
//        Token answer = null;
//        // if there is no more string to consume, return null
//        if( currentIndex >= source.length()) return null;
//        
//        String lexeme = "";
//        
//        // Start in start state
//        int currentState = Scanner.START;
//        while( currentState < Scanner.ERROR) {
//            //System.out.println("While index is " + currentIndex + " and state is " + currentState);
//            // By default the char is whitespace
//            char currentChar = ' ';
//            if( currentIndex >= source.length() && currentState != 0) {
//                currentChar = ' ';
//            }
//            else if( currentIndex < source.length()) {
//                currentChar = source.charAt( currentIndex);
//            }
//            else break;
//            
//            switch( currentState) {
//                case Scanner.START:
//                    if( Character.isLetter(currentChar)) {
//                        lexeme = lexeme + currentChar;
//                        currentState = Scanner.IN_ID;
//                        currentIndex++;
//                    }
//                    else if (Character.isDigit(currentChar)) {
//                        lexeme = lexeme + currentChar;
//                        currentState = Scanner.IN_NUM;
//                        currentIndex++;
//                    }
//                    else if (Character.isWhitespace(currentChar)) {
//                        currentIndex++;
//                    }
//                    else if( currentChar == '+' ||
//                            currentChar == '-' ||
//                            currentChar == ';' ||
//                            currentChar == '=') {
//                        currentIndex++;
//                        currentState = Scanner.SYMBOL_COMPLETE;
//                        lexeme = lexeme + currentChar;                        
//                    }
//                    else {
//                        currentIndex++;
//                        currentState = Scanner.ERROR;
//                        lexeme = lexeme + currentChar;                        
//                    }
//                    break;
//                    
//                    
//                case Scanner.IN_ID:
//                    if( Character.isLetterOrDigit(currentChar)) {
//                        lexeme = lexeme + currentChar;
//                        currentIndex++;
//                    }
//                    else {
//                        currentState = Scanner.ID_COMPLETE;
//                    }
//                    break;
//                    
//                    
//                case Scanner.IN_NUM:
//                    if( Character.isDigit(currentChar)) {
//                        lexeme = lexeme + currentChar;
//                        currentIndex++;
//                    }
//                    else {
//                        currentState = Scanner.NUM_COMPLETE;
//                    }
//                    break;
//                    
//                default:
//                    System.out.println("Never get here");
//                    break;
//            } /// end switch
//        } // end while
//        
//        // Now we are in a completion state:
//        switch( currentState) {
//            case ERROR: 
//                answer = new Token( lexeme, null);
//                break;
//            case ID_COMPLETE:
//                TokenType idToken = types.get( lexeme);
//                if( idToken == null) idToken = TokenType.ID;
//                answer = new Token( lexeme, idToken);
//                break;
//            case NUM_COMPLETE:
//                answer = new Token( lexeme, TokenType.NUMBER);
//                break;
//            case SYMBOL_COMPLETE:
//                TokenType whichToken = types.get( lexeme);
//                answer = new Token( lexeme, whichToken);
//                break;
//        }
//        return answer;
//    }
//
//}