
package symboltable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author nikaylahwoody
 *
 */
public class SymbolTable {


	private HashMap<String, Symbol> symbTable;

	public SymbolTable() {
		symbTable = new HashMap<>();
	}

	public boolean addProgram(String name, Kind k) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol("program", k));
			return true;
		}
		return false;
	}

	public boolean addVariable(String name, Kind k, String type) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol("var", k, type));
			return true;
		}
		return false;
	}

	public boolean addArray(String name, Kind k, String type, int begin, int end) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol("var", k, type, begin, end));
			return true;
		}
		return false;
	}

	public boolean addFunction(String name, Kind k, String type, ArrayList<Symbol> args) {
		if (!symbTable.containsKey(name)) {
			symbTable.put(name, new Symbol("var", k, type, args));
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

	private enum Kind {
		PROGRAM, VARIABLE, ARRAY, FUNCTION
	}	
	private class Symbol {
		String lexeme;
		Kind kind;
		String type;
		int beginidx, endidx;
		ArrayList<Symbol> args;

		// for program
		public Symbol(String lex, Kind k) {
			lexeme = lex;
			kind = k;
		}	
		// for variable
		public Symbol(String lex, Kind k, String t) {
			lexeme = lex;
			kind = k;
			type = t;
		}

		// for array
		public Symbol(String lex, Kind k, String t, int begin, int end) {
			lexeme = lex;
			kind = k;
			type = t;
			beginidx = begin;
			endidx = end;
		}	
		// for function
		public Symbol(String lex, Kind k, String t, ArrayList funcArgs) {
			lexeme = lex;
			kind = k;
			type = t;
			args = funcArgs;
		}

		public Kind getKind() {
			return kind;
		}

	}


}
