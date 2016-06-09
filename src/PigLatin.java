/*Jonathan Abdo 6-9-16
 * translate from english to pig latin
 */
import java.util.Scanner;

public class PigLatin {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("English to Pig Latin Translator\n");
		
		boolean cont = true;
		while (cont) {
			System.out.print("Please enter a line: ");
			String english = sc.nextLine();
			System.out.println(english+"\nbecomes: ");
			
			StringBuilder translated = new StringBuilder(english);
			
			
			
			if(!InputCheck.cont(sc, "Translate another line? (y/n)"))
				cont = false;
		}
		sc.close();
	}

}
