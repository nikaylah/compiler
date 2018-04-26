package compiler;

import parser.Parser;


/**
 * @author nikaylahwoody
 * this is the main driver for the compiler
 */
public class CompilerMain {
	
    public static void main(String[] args) {
        String prog = "";
        boolean help = false;
        if (args.length == 0) {
        	prog = "src/parser/test/simple.pas";
        }
        else if (args.length == 1) {
        if (args[0].equals("h") || args[0].equals("help")) {
        	help = true;
        }   else prog = args[0];
        }
        else {
            System.out.println("enter program to parse");
            System.exit(1);
        }
        if (!help) {
            Parser parser = new Parser(prog, true);
            parser.program();
        }
    }

    public static void help() {
        String help = " program will parse a Mini-pascal file";
        System.out.println(help);
    }


	
}
