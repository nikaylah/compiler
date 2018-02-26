package compiler;

import parser.Parser;


/**
 * @author nikaylahwoody
 * this is the main driver for the compiler
 */
public class CompilerMain {
	
	public static void main(String[] args){
		Parser parser = new Parser("src/parser/test/simple.pas", true);
		parser.program();
		System.out.println("yes");
		System.out.println(parser.getSymbolTable().toString());
	}


	
}
