package scanner;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import org.junit.Before;
import org.junit.After;

public class ScannerTest {

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	private final String filename = "src/scanner/simplest.pas";
	private Scanner scanner;
	
	@Test
	void yytext() throws IOException {
		System.out.println("-----test yytext-----");

		String expRes = "";
		System.out.println("Expected result: " + expRes);
		String result = scanner.yytext();
		System.out.println("Actual result: " + result);
		assertEquals(expRes, result);
		System.out.println("test case 1 passed.\n");

		scanner.nextToken();

		expRes = "program";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 2 passed.\n");

		scanner.nextToken();

		expRes = "foo";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 3 passed.\n");

		scanner.nextToken();

		expRes = ";";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 4 passed.\n");

		scanner.nextToken();

		expRes = "begin";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 5 passed.\n");

 		scanner.nextToken();

		expRes = "end";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 6 passed.\n");

		scanner.nextToken();

		expRes = ".";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 7 passed.\n");

		scanner.nextToken();

		expRes = "";
		System.out.println("Expected result: " + expRes);
		result = scanner.yytext();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 8 passed.\n");
	}

	/**
	 * Tests whether the next token returned is the expected lexeme and Type.
	 *
	 * @throws IOException
	 *             if any I/O-Error occurs
	 */
	@Test
	void nextToken() throws IOException {
		System.out.println("-----test nextToken-----");
		Token expRes = new Token("program", TokenType.PROGRAM, 0);
		System.out.println("Expected result: " + expRes);
		Token result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 1 pass.\n");

		expRes = new Token("foo", TokenType.ID, 0);
		System.out.println("Expected result: " + expRes);
		result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 2 pass.\n");

		expRes = new Token(";", TokenType.SEMICOLON, 0);
		System.out.println("Expected result: " + expRes);
		result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 3 pass.\n");

		expRes = new Token("begin", TokenType.BEGIN, 0);
		System.out.println("Expected result: " + expRes);
		result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 4 pass.\n");

		expRes = new Token("end", TokenType.END, 0);
		System.out.println("Expected result: " + expRes);
		result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 5 pass.\n");

		expRes = new Token(".", TokenType.PERIOD, 0);
		System.out.println("Expected result: " + expRes);
		result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 6 pass.\n");

		// EOF value should be null
		expRes = null;
		System.out.println("Expected result: " + expRes);
		result = scanner.nextToken();
		System.out.println("Actual result:   " + result);
		assertEquals(expRes, result);
		System.out.println("test case 7 pass.\n");

		System.out.println("All nextToken tests PASSED.\n");
	}

}
