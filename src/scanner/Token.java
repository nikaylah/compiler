package scanner;


/**
 * @author nikaylahwoody
 *
 */
public class Token {
	
    public String lexeme;
    public TokenType type;
    
    public Token( String l, TokenType t) {
        this.lexeme = l;
        this.type = t;
    }
    
    public String getLexeme(){
    	return this.lexeme;
    }
    
    public TokenType getTokenType(){
    	return this.type;
    }

    

	@Override
	public String toString() {
		return "TokenType [lexeme=" + lexeme + ", type=" + type + "]";
	}

	//Fix
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Token.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Token other = (Token) obj;
        if (this.lexeme.equals(other.getLexeme()) && this.type.equals(other.getTokenType()))
            return true;
        return false;
    }
	
	
//	@Override
//	public boolean equals(Object o){
//		
//		if(!( o instanceof Token)) 
//		return false;
//		
//		Token other = (Token)o;
//		boolean answer = true;
//		if(!this.lexeme.equals(other.lexeme))
//		answer = false;
//		if(this.type != other.type)
//		answer = false;
//	}
	
}


