/*Jonathan Abdo 6-9-16
 * translate from english to pig latin
 */
import java.util.Scanner;

public class PigLatin {

	public static void main(String[] args) {
		
		//Scanner and char arrays needed for loops
		Scanner sc = new Scanner(System.in);
		char[] vowels = "AEIOU".toCharArray();
		char[] consonants = "BCDFGHJKLMNPQRSTVWXYZ".toCharArray();
		char[] punctuation = " ,.!?_".toCharArray();
		char[] excluded = "0123456789~@#$%^&*()[]{}/\\|-+=:;><".toCharArray();
		
		//title
		System.out.println("English to Pig Latin Translator");
		
		//user can "continue" to use functionality based on this loop
		//uses a boolean that is set to false when the user doesn't enter 'y' or "yes", ignoring case
		boolean cont = true;
		while (cont) {
			String english;
			//check to make sure the user entered a line
			do {
				System.out.println("\nPlease enter a line: ");
				english = sc.nextLine();
				if(english.length() <= 0){
					System.out.println("Oops! You forgot to enter something.");
				}
			} while (english.length()<=0);
			
			System.out.println("becomes: ");
			
			//copy user line into stringbuilder to allow for inserting/deleting
			StringBuilder translated = new StringBuilder(english);
			
			//adds spaces to make identifying the start of the first word and end of the last word easier
			translated.insert(0, " ");
			translated.append(" ");
			
			//calls method that searches for words that begin with vowels and applies the corresponding pig latin rule
			startsWithVowels(vowels, punctuation, excluded, translated);
			
			//Calls method that searches for words that begin with consonants and applies the corresponding pig latin rule
			startsWithConsonants(consonants, vowels, punctuation, excluded, translated);
						
			//cleans up the spaces that were added for 'identifying' purposes
			translated.deleteCharAt(0);
			translated.deleteCharAt(translated.length()-1);
			System.out.println(translated);
			
			if(!InputCheck.cont(sc, "\nTranslate another line? (y/n)"))
				cont = false;
			sc.nextLine();
		}
		sc.close();
	}
	
	//if it starts with a vowel add "way" to the end of the word
	public static void startsWithVowels(char[] vowels, char[] punctuation, char[] excluded, StringBuilder translated){
		
		boolean foundExcluded = false;
		
		//checks each vowel against the start of each word in the sentence
		for(int i=0; i<vowels.length; i++){
			
			//iterates through the use line
			for(int j=0; j<translated.length()-1; j++){
				
				//iterates through punctuation
				for (int k=0; k < punctuation.length; k++) {
					
					//search for any combination of space/punctuation + vowel, indicating start of word
					if(translated.substring(j, j+2).equalsIgnoreCase(Character.toString(punctuation[k])+Character.toString(vowels[i]))){
						
						//a word starting with a vowel is found, now find the end of the word
						//sets the 'endOfWord' to the first space following the word
						int endOfWord = translated.indexOf(" ", j+1);
						
						//checks for any 'separating' punctuation that might be before the 'space' 
						for (int l=0; l < punctuation.length; l++) {
							int punctuationLoc = translated.indexOf(Character.toString(punctuation[l]), j + 1);
							
							//if the punctuation found is before the space, set 'endOfWord' to the punctuation
							if (endOfWord > punctuationLoc && punctuationLoc >= 0)
								endOfWord = punctuationLoc;
						}
						
						//check for any of the excluded numbers or symbols in the word
						for (int m = 0; m < excluded.length; m++) {
							
							//if the word contains numbers or symbols, change foundExcluded to true and break from loop
							if (translated.substring(j + 1, endOfWord).indexOf(excluded[m]) >= 0) {
								foundExcluded = true;
								break;
							} 
						}
						//if no numbers/symbols were found then add "way" to the end
						if(!foundExcluded){
							translated.insert(endOfWord, "way");
						}
						
					}
				}
			}
		}
	}

	/*if it starts with a consonant, move all consonants, from start of the word to 
	 * the first vowel, to the end of the word and add "ay".
	 */
	public static void startsWithConsonants(char[] consonants, char[] vowels, char[] punctuation, char[] excluded, StringBuilder translated){
		
		//iterates through each consonant
		for(int i=0; i<consonants.length; i++){
			
			//iterates through user line
			for(int j=0; j<translated.length()-1; j++){
				
				//iterates through punctuation
				for (int j2 = 0; j2 < punctuation.length; j2++) {
					
					//check if the word begins with a consonant
					if (translated.substring(j, j + 2).equalsIgnoreCase(Character.toString(punctuation[j2]) + Character.toString(consonants[i]))) {

						//string that will hold the consonants being moved
						String moveConsonants = new String(" ");

						//set firstVowel to end of line for comparison
						int firstVowel = translated.length();

						//iterates through all possible vowels
						for (int k = 0; k < vowels.length; k++) {

							//sets currVowelLoc to earliest index of current vowel being searched
							int currVowelLoc = translated.indexOf(Character.toString(vowels[k]), j);

							//if the capital case of the current vowel is not found, or found outside of the word
							if (currVowelLoc < 0 || currVowelLoc > translated.indexOf(" ", j + 1))
								//set the current vowel location to the first location of the lower case for the same vowel
								currVowelLoc = translated.indexOf(Character.toString(vowels[k]).toLowerCase(), j);
							//if the current vowel in the word is before 'firstVowel' set 'firstVowel' to the current vowel
							if (currVowelLoc > 0 && firstVowel > currVowelLoc
									&& currVowelLoc < translated.indexOf(" ", j + 1))
								firstVowel = currVowelLoc;
						}

						if (firstVowel < translated.length()) {

							moveConsonants = translated.substring(j + 1, firstVowel);
							//check for end of word via space first then punctuation
							int endOfWord = translated.indexOf(" ", j + 1);

							for (int k = 0; k < punctuation.length; k++) {
								int punctuationLoc = translated.indexOf(Character.toString(punctuation[k]), j + 1);

								if (endOfWord > punctuationLoc && punctuationLoc >= 0) {
									endOfWord = punctuationLoc;
								}
							}

							boolean foundExcluded = false;
							//check for any of the excluded numbers or symbols in the word
							for (int m = 0; m < excluded.length; m++) {

								//if the word contains numbers or symbols, change foundExcluded to true and break from loop
								if (translated.substring(j + 1, endOfWord).indexOf(excluded[m]) >= 0) {
									foundExcluded = true;
									break;
								}
							}
							//if no numbers/symbols were found then add consonants+"ay" to the end
							if (!foundExcluded) {
								translated.insert(endOfWord, moveConsonants + "ay");
								translated.delete(j + 1, firstVowel);
							}
						}
					} 
				}
			}
		}
	}
}
