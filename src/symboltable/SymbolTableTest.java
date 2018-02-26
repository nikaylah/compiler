package symboltable;

import scanner.Token;

import org.junit.After;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import scanner.TokenType;
import symboltable.SymbolTable.Kind;
import symboltable.SymbolTable.Symbol;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;

import org.junit.Test;

public class SymbolTableTest {

	private SymbolTable symbolTable;
		
  /**
  * adding functions, variables, arrays and programs to created symbol table before each test
  */
@Before
 void setUp() {
     symbolTable = new SymbolTable();

     //adding several function id's
     ArrayList<Symbol> args = new ArrayList();
     args.add(new Symbol("input", Kind.VARIABLE, TokenType.INTEGER));
     symbolTable.addFunction("function1", TokenType.REAL);
     symbolTable.addFunction("function2", TokenType.INTEGER);
     symbolTable.addFunction("function3", TokenType.REAL);

     //adding variables
     symbolTable.addVariable("variable1", TokenType.INTEGER);
     symbolTable.addVariable("variable2", TokenType.REAL);
     symbolTable.addVariable("variable3", TokenType.INTEGER);

     //adding ids to the array
     symbolTable.addArray("array1", TokenType.INTEGER, 0, 5);
     symbolTable.addArray("array2", TokenType.REAL, -5, 5);
     symbolTable.addArray("array3", TokenType.INTEGER, 10, 500);

     //adding ids to program
     symbolTable.addProgram("program1");
     symbolTable.addProgram("program2");
     symbolTable.addProgram("program3");

     //adding ids to procedure
     symbolTable.addProgram("procedure1");
     symbolTable.addProgram("procedure2");
     symbolTable.addProgram("procedure3");
 }

 

 /**
  * Testing adding two programs to the symbol table
  */
 @Test
 void addProgram() {
     System.out.println("addProgram");
     System.out.println("-------------------------------------------");
     // Add a program not already in the symbol table
     String name = "program0";
     boolean result = symbolTable.addProgram(name);
     assertEquals(true, result);
     System.out.println("program0 successfully added to symbol table");

     // Add a program already in the symbol table
     name = "program1";
     result = symbolTable.addProgram(name);
     assertEquals(false, result);
     System.out.println("program1 not added to symbol table, already exists\n");
 }

 @After
 void tearDown(){
	 symbolTable.symbTable.clear();
	 symbolTable.symbTable = null;
	 symbolTable = null;
 }
 /**
  * Testing adding two variables to the symbol table 
  */
 @Test
 void addVariable() {
     System.out.println("addVariable");
     System.out.println("-------------------------------------------");
     // Add a variable not already in the symbol table
     String name = "var0";
     TokenType t = TokenType.INTEGER;
     boolean result = symbolTable.addVariable(name, t);
     assertEquals(true, result);
     System.out.println("var0 successfully added to symbol table");

     // Add a variable already in the symbol table
     name = "variable1";
     t = TokenType.REAL;
     result = symbolTable.addVariable(name, t);
     assertEquals(false, result);
     System.out.println("variable1 not added to symbol table, already exists\n");
 }

 /**
  * Testing adding two arrays to the symbol table
  */
 @Test
 void addArray() {
     System.out.println("addArray");
     System.out.println("-------------------------------------------");
     // Add a array not already in the symbol table
     String name = "array0";
     TokenType t = TokenType.INTEGER;
     int begin = -5, end = 0;
     boolean result = symbolTable.addArray(name, t, begin, end);
     assertEquals(true, result);
     System.out.println("array0 successfully added to symbol table");

     // Add a array already in the symbol table
     name = "array1";
     t = TokenType.REAL;
     begin = -5;
     end = 0;
     result = symbolTable.addArray(name, t, begin, end);
     assertEquals(false, result);
     System.out.println("array1 not added to symbol table, already exists\n");
 }

 /**
  * Testing adding two functions to the symbol table
  */
 @Test
 void addFunction() {
     System.out.println("addFunction");
     System.out.println("-------------------------------------------");
     // Add a function not already in the symbol table
     String name = "function0";
     TokenType t = TokenType.INTEGER;
     //ArrayList<Symbol> args = new ArrayList();
     //args.add(new Symbol("input", Kind.VARIABLE, Type.INTEGER));
     boolean result = symbolTable.addFunction(name, t);
     assertEquals(true, result);
     System.out.println("function0 successfully added to symbol table");

     // Add a function already in the symbol table
     name = "function1";
     t = TokenType.REAL;
     result = symbolTable.addFunction(name, t);
     assertEquals(false, result);
     System.out.println("function1 not added to symbol table, already exists\n");
 }

 /**
  * Testing adding two procedures to the symbol table
  */
 @Test
 void addProcedure() {
     System.out.println("addProcedure");
     System.out.println("-------------------------------------------");
     // Add a procedure not already in the symbol table
     String name = "procedure0";
     boolean result = symbolTable.addProgram(name);
     assertEquals(true, result);
     System.out.println("procedure0 successfully added to symbol table");

     // Add a procedure already in the symbol table
     name = "procedure1";
     result = symbolTable.addProgram(name);
     assertEquals(false, result);
     System.out.println("procedure1 not added to symbol table, already exists\n");
 }

 /**
  * Testing method with a program symbol in the table
  */
 @Test
 void isProgramName() {
     System.out.println("isProgramName");
     System.out.println("-------------------------------------------");
     String name = "program1";
     boolean result = symbolTable.isProgramName(name);
     assertEquals(true, result);
     System.out.println("program1 is a name in the symbol table with kind PROGRAM");

     name = "variable1";
     result = symbolTable.isProgramName(name);
     assertEquals(false, result);
     System.out.println("variable1 is a name in the symbol table without have kind PROGRAM");

     name = "foo";
     result = symbolTable.isProgramName(name);
     assertEquals(false, result);
     System.out.println("foo is a not a name in the symbol table\n");
 }

 /**
  * Tests method with a variable symbol in the table
  */
 @Test
 void isVariableName() {
     System.out.println("isVariableName");
     System.out.println("-------------------------------------------");
     String name = "variable1";
     boolean result = symbolTable.isVariableName(name);
     assertEquals(true, result);
     System.out.println("variable1 is a name in the symbol table with kind VARIABLE");

     name = "program1";
     result = symbolTable.isVariableName(name);
     assertEquals(false, result);
     System.out.println("program1 is a name in the symbol table witout kind VARIABLE");

     name = "foo";
     result = symbolTable.isVariableName(name);
     assertEquals(false, result);
     System.out.println("foo is a not a name in the symbol table\n");
 }

 /**
  * Tests method with an array symbol in the table
  */
 @Test
 void isArrayName() {
     System.out.println("isArrayName");
     System.out.println("-------------------------------------------");
     String name = "array1";
     boolean result = symbolTable.isArrayName(name);
     assertEquals(true, result);
     System.out.println("array1 is a name in the symbol table with kind ARRAY");

     name = "variabl1";
     result = symbolTable.isArrayName(name);
     assertEquals(false, result);
     System.out.println("variable1 is a name in the symbol table without kind ARRAY");

     name = "foo";
     result = symbolTable.isArrayName(name);
     assertEquals(false, result);
     System.out.println("foo is a not a name in the symbol table\n");
 }

 /**
  * Tests method with a function symbol in the table
  */
 @Test
 void isFunctionName() {
     System.out.println("isFunctionName");
     System.out.println("-------------------------------------------");
     String name = "function1";
     boolean result = symbolTable.isFunctionName(name);
     assertEquals(true, result);
     System.out.println("function1 is a name in the symbol table and has kind FUNCTION");

     name = "variable1";
     result = symbolTable.isFunctionName(name);
     assertEquals(false, result);
     System.out.println("variable1 is a name in the symbol table but does not have kind FUNCTION");

     name = "foo";
     result = symbolTable.isFunctionName(name);
     assertEquals(false, result);
     System.out.println("foo is a not a name in the symbol table\n");
 }

 /**
  * Tests method with a procedure symbol in the table
  */
 @Test
 void isProcedureName() {
     System.out.println("isProcedureName");
     System.out.println("-------------------------------------------");
     String name = "procedure1";
     boolean result = symbolTable.isProgramName(name);
     assertEquals(true, result);
     System.out.println("procedure1 is a name in the symbol tablwithe and has kind PROGRAM");

     name = "variable1";
     result = symbolTable.isProgramName(name);
     assertEquals(false, result);
     System.out.println("variable1 is a name in the symbol table without kind PROCEDURE");

     name = "foo";
     result = symbolTable.isProgramName(name);
     assertEquals(false, result);
     System.out.println("foo is a not a name in the symbol table\n");
}

}
