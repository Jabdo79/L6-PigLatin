/*Jonathan Abdo 6-9-16
 * translate from english to pig latin
 */
import java.util.Scanner;

public class PigLatin {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("English to Pig Latin Translator\n");
		
		char[] vowels = "AEIOU".toCharArray();
		char[] consonants = "BCDFGHJKLMNPQRSTVWXYZ".toCharArray();
		boolean cont = true;
		while (cont) {
			System.out.println("Please enter a line: ");
			String english = sc.nextLine();
			System.out.println("becomes: ");
			
			StringBuilder translated = new StringBuilder(english);
			//adds spaces to make identifying the first and last word easier
			translated.insert(0, " ");
			translated.append(" ");
			
			
			//iterates through each vowel
			for(int i=0; i<vowels.length; i++){
				//iterates through user line
				for(int j=0; j<translated.length()-1; j++){
					//if it starts with a vowel, add 'way' to the end of the word
					//if the current position being checked of the user line is equal to, ignoring case, a space + the current vowel being checked, perform the following
					if(translated.substring(j, j+2).equalsIgnoreCase(" "+Character.toString(vowels[i]))){
						int endOfWord = translated.indexOf(" ", j+1);
						translated.insert(endOfWord, "way");
					}
				}
			}
			
			//iterates through each consonant
			for(int i=0; i<consonants.length; i++){
				//iterates through user line
				for(int j=0; j<translated.length()-1; j++){
					//if it starts with a consonant, move all consonants from start of the word to first vowel to the end and add 'ay' to the end of the word
					//if the current position being checked of the user line is equal to, ignoring case, a space + the current consonant being checked, perform the following
					if(translated.substring(j, j+2).equalsIgnoreCase(" "+Character.toString(consonants[i]))){
						int firstVowel = translated.length();
						String moveConsonants = new String(" ");
						for(int k=0; k<vowels.length; k++){
				
							int currVowelLoc = translated.indexOf(Character.toString(vowels[k]), j);
							if(currVowelLoc<0||currVowelLoc>translated.indexOf(" ", j+1))
								currVowelLoc = translated.indexOf(Character.toString(vowels[k]).toLowerCase(), j);
							if(currVowelLoc>0&&firstVowel>currVowelLoc&&currVowelLoc<translated.indexOf(" ", j+1))
								firstVowel = currVowelLoc;
						}
						if(firstVowel<translated.length()){
							moveConsonants = translated.substring(j+1, firstVowel);							
							translated.insert(translated.indexOf(" ", j+1), moveConsonants+"ay");
							translated.delete(j+1, firstVowel);
						}
					}
				}
			}
			
			
			//cleans up the spaces that were added for 'identifying' purposes
			translated.deleteCharAt(0);
			translated.deleteCharAt(translated.length()-1);
			System.out.println(translated);
			
			if(!InputCheck.cont(sc, "Translate another line? (y/n)"))
				cont = false;
			sc.nextLine();
		}
		sc.close();
	}

}
