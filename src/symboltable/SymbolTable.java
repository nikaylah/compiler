
package symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import scanner.TokenType;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

/**
 * @author nikaylahwoody
 *
 */
public class SymbolTable {

//Instance variables-----------------------------------
	protected HashMap<String, Symbol> symbTable;

	public SymbolTable() {
		symbTable = new HashMap<>();
	}

//Constructor----------------------------------------
	
	public String toString(){
		return "SymbolTable{" + "symbTable" + symbTable + '}';
	}
	
//	public String toString(){
//		return "SymbolTable{" + "symbTable" + symbTable + '}';
//	}
	
	
//----------------------------------------------------------------
//Methods
	public boolean addProgram(String name) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol(name, Kind.PROGRAM));
			return true;
		}
		return false;
	}

	public boolean addVariable(String name,TokenType type) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol(name, Kind.VARIABLE, type));
			return true;
		}
		return false;
	}

	public boolean addArray(String name, TokenType type, int begin, int end) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol(name, Kind.ARRAY));
			return true;
		}
		return false;
	}

	
	public boolean addFunction(String name, TokenType type) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol(name, Kind.FUNCTION));
			return true;
		}
		return false;
	}
	
	public boolean addProcedure(String name){
		if (!symbTable.containsKey(name)){
			symbTable.put(name, new Symbol(name, Kind.PROCEDURE));
			return true;
		}
		return false;
	}

	public boolean isProgramName(String name) {
		if (symbTable.containsKey(name)) return symbTable.get(name).getKind() == Kind.PROGRAM;
		return false;
	}

	public boolean isVariableName(String name) {
		if (symbTable.containsKey(name)) return symbTable.get(name).getKind() == Kind.VARIABLE;
		return false;
	}

	public boolean isArrayName(String name) {
		if (symbTable.containsKey(name)) return symbTable.get(name).getKind() == Kind.ARRAY;
		return false;
	}

	public boolean isFunctionName(String name) {
		if (symbTable.containsKey(name)) return symbTable.get(name).getKind() == Kind.FUNCTION;
		return false;
	}
	
	public boolean isProcedureName(String name) {
		if (symbTable.containsKey(name)) return symbTable.get(name).getKind() == Kind.PROCEDURE;
		return false;
	}

	public enum Kind {
		PROGRAM, VARIABLE, ARRAY, FUNCTION, PROCEDURE
	}	
	
	/**
	 * @author nikaylahwoody
	 *
	 */
	public class Symbol {
		String lexeme;
		Kind kind;
		TokenType type;
		int beginidx, endidx;
		ArrayList<Symbol> args;

		// for program
		public Symbol(String lex, Kind k) {
			lexeme = lex;
			kind = k;
		}	
		// for variable
		public Symbol(String lex, Kind k, TokenType t) {
			lexeme = lex;
			kind = k;
			type = t;
		}

		// for array
		public Symbol(String lex, Kind k, TokenType t, int begin, int end) {
			lexeme = lex;
			kind = k;
			type = t;
			beginidx = begin;
			endidx = end;
		}	
		// for function
		public Symbol(String lex, Kind k, TokenType t, ArrayList funcArgs) {
			lexeme = lex;
			kind = k;
			type = t;
			args = funcArgs;
		}

		public Kind getKind() {
			return kind;
		}
		
		public String getLexeme(){
			return lexeme;
		}
		
		public TokenType getType(){
			return type;
		}
		
		public int getBeginidx(){
			return beginidx;
		}
		
		public int getEndidx(){
			return endidx;
		}
		
		public ArrayList getArgs(){
			return args;
		}
		

		@Override
		public String toString() {
			return "Symbol [lexeme=" + lexeme + ", kind=" + kind + ", type=" + type + ", beginidx=" + beginidx
					+ ", endidx=" + endidx + ", args=" + args + "]";
		}
	
		public boolean equals(Object o){
			if (this == o)
				return true;
			if(o == null || getClass() != o.getClass())
				return false;
			Symbol symbol = (Symbol) o;
			return getBeginidx() == symbol.getBeginidx() && getEndidx() == symbol.getEndidx() && Objects.equals(getLexeme(), symbol.getLexeme()) && Objects.equals(getType(), symbol.getType()) && getKind() == symbol.getKind() && Objects.equals(getArgs(), symbol.getArgs()); 
		}
		
	}


	public boolean doesExist(String lexeme) {
		
		return false;
	}

	

}
