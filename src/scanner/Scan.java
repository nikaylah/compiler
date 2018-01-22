package scanner;


/**
 * @author nikaylahwoody
 *
 */
public class Scan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner ss = new Scanner("this");
        //java.util.Scanner ss = new java.util.Scanner("this");
        Token t = ss.nextToken();
        while( t != null){
            System.out.println("Token is " + t.type + " : " + t.lexeme);
            t = ss.nextToken();
        }
//        for( Token t = ss.nextToken(); t != null; t = ss.nextToken())
//             System.out.println("Token is " + t.type + " : " + t.lexeme);
//        System.out.println("All done");
    }
    
}
