/**
 * Menu-driven program to encrypt and decrypt text files using a simple
 * substitution cipher.
 * @author Brendon Baughn
 * @version 20181107
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SubstitutionCipher {

	/**
	 * Private constants used to shift characters for the substitution cipher.
	 */
	private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * Constructs a new String where each letter in the String input is shifted
	 * by the amount shift to the right, preserving whether the original
	 * character was uppercase or lowercase. For example, the String "ABC" with
	 * shift 3 would cause this method to return "DEF". A negative value should
	 * shift to the left. For example, the String "ABC" with shift -3 would
	 * cause this method to return "XYZ". Punctuation, numbers, whitespace and
	 * other non-letter characters should be left unchanged. NOTE: For full
	 * credit you are REQUIRED to use a StringBuilder to build the String in
	 * this method rather than using String concatenation.
	 *
	 * @param input
	 *            String to be encrypted
	 * @param shift
	 *            Amount to shift each character of input to the right
	 * @return the encrypted String as outlined above
	 */

	public static String shift(String input, int shift) {
		String lowercase = LOWERCASE;
		String uppercase = UPPERCASE;
		String returnedStr = "";
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < input.length(); ++i) {
			char fchar = input.charAt(i);
			char lchar = lowercase.charAt(0); // Start at 0
			char uchar = uppercase.charAt(0); // Start at 0
			if (Character.isWhitespace(fchar)) {
				// if there is a space
				sb.append(" ");
			} else if (!Character.isLetterOrDigit(fchar) && !(Character.isWhitespace(fchar))) {
				// if not let, dig, or space
				sb.append(fchar);
			} else if (Character.isDigit(fchar)) {
				// if it is a digit
				sb.append(fchar);
			} else if (Character.isLowerCase(fchar)) {
				while (fchar != lchar) { // update char if it does not match input
					++lchar;
				}
				int newi = lowercase.indexOf(lchar) + shift; // Get the index of the shift
				if (newi % 26 >= 0) { // If the mod of new index is >= 0 ("=" for first place of input)
					int newpos = newi % 26;
					char nchar = lowercase.charAt(newpos);
					sb.append(nchar);
				} else if (newi % 26 < 0) {
					// add 26 to the negative num to get positive position
					int wrapper = (newi % 26) + 26;
					char nchar = lowercase.charAt(wrapper);
					sb.append(nchar);
				}

			} else if (Character.isUpperCase(fchar)) { // Repeat everything above
				while (fchar != uchar) {
					++uchar;
				}
				int newi = uppercase.indexOf(uchar) + shift;
				if (newi % 26 >= 0) {
					int newpos = newi % 26;
					char nchar = uppercase.charAt(newpos);
					sb.append(nchar);
				} else if (newi % 26 < 0) {
					int wrapper = (newi % 26) + 26;
					char nchar = uppercase.charAt(wrapper);
					sb.append(nchar);

				}
			}
		} // End of for loop
		returnedStr = sb.toString();
		return returnedStr;
	}

	/**
	 * Displays the message "promptMsg" to the user and reads the next full line
	 * that the user enters. If the user enters an empty string, reports the
	 * error message "ERROR! Input value cannot be empty!" and then loops,
	 * repeatedly prompting them with "promptMsg" to enter a new string until
	 * the user enters a non-empty String
	 *
	 * @param in
	 *            Scanner to read user input from
	 * @param promptMsg
	 *            Message to display to user to prompt them for input
	 * @return the String entered by the user
	 */
	public static String promptForString(Scanner in, String promptMsg) {
		System.out.print(promptMsg);
		String userInput = in.nextLine();
		while (userInput.isEmpty()) {
			System.out.println("ERROR! Input value cannot be empty!");
			System.out.print(promptMsg);
			userInput = in.nextLine();
		}
		return userInput;
	}

	/**
	 * Opens the file inFile for reading and the file outFile for writing,
	 * reading one line at a time from inFile, shifting it the number of
	 * characters given by "shift" and writing that line to outFile. If an
	 * exception occurs, must report the error message: "ERROR! File inFile not
	 * found or cannot write to outFile" where "inFile" and "outFile" are the
	 * filenames given as parameters. Note that you MUST catch FileNotFound
	 * exceptions in this method - you cannot modify the header to throw them to
	 * the calling method.
	 *
	 * @param inFile
	 *            the file to be transformed
	 * @param outFile
	 *            the file to write the transformed output to
	 * @param shift
	 *            the amount to shift the characters from inFile by
	 */
	public static void transformFile(String inFile, String outFile, int shift) {
		try {
			File textFile = new File(inFile);
			Scanner textScan = new Scanner(textFile);
			PrintWriter copyFile = new PrintWriter(outFile);
			while (textScan.hasNext()) {
				String input = textScan.nextLine();

				copyFile.println(shift(input, shift));
				// ^^^ outputting "b" to main, outputting half of code to outFile for some reason
			}
			copyFile.close();
			textScan.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR! File inFile not found or cannot write to outFile");
		}
	}

	/**
	 * Displays the current settings for currentInput, currentOutput and shift
	 * to the screen and a menu of options formatted as outlined in the
	 * assignment writeup. If either of currentInput or currentOutput is empty,
	 * the menu must not show the Encode or Decode options.
	 *
	 * @param currentInput
	 *            the name of the current input file
	 * @param currentOutput
	 *            the name of the current output file
	 * @param shift
	 *            the amount to shift characters during encoding/decoding
	 */
	public static void displayMenu(String currentInput, String currentOutput,
			int shift) {
		System.out.println("Input file is: " + currentInput);
		System.out.println("Output file is: " + currentOutput);
		System.out.println("Shift amount is: " + shift);
		System.out.println("Select from the following options: ");
		System.out.println("Set [O]utput file");
		System.out.println("Set [I]nput file");
		System.out.println("Set [S]hift amount");
		if (!(currentInput == "" || currentOutput == "" || shift == 0)) {
			System.out.println("[E]ncode " + currentInput + " into " + currentOutput);
			System.out.println("[D]ecode " + currentInput + " into " + currentOutput);
		}
		System.out.println("[Q]uit");
	}

	/**
	 * Prompts the user to enter a single character choice. The values O, I, S
	 * and Q are always allowed. The values E and D are only allowed if the flag
	 * "allOptions" is true. All other values are invalid, including all values
	 * longer than one character in length, however the user is allowed to enter
	 * values in either lower or upper case. If the user enters an invald value,
	 * the method displays the error message "ERROR! Enter a valid value!" and
	 * then prompts the user repeatedly until a valid value is entered. Returns
	 * a single uppercase character representing the user's choice.
	 *
	 * @param in
	 *            Scanner to read user choices from
	 * @param allOptions
	 *            true if E and D are allowable inputs, false otherwise
	 * @return the user's choice as an uppercase character
	 */
	public static char getChoice(Scanner in, boolean allOptions) {
		System.out.print("Enter your choice: ");
		String userChoice = in.nextLine().toUpperCase();
		char userEnter = userChoice.charAt(0);
		if (allOptions == false) {
			while (!(userEnter == 'S' || userEnter == 'O' || userEnter == 'I' || userEnter == 'Q')
					|| userChoice.length() > 1) {
				System.out.println("ERROR! Enter a valid value!");
				System.out.println();
				System.out.print("Enter your choice: ");
				userChoice = in.nextLine().toUpperCase();
				userEnter = userChoice.charAt(0);
			}
		} else {
			while (!(userEnter == 'E' || userEnter == 'D' || userEnter == 'Q')) {
				System.out.println("ERROR! Enter a valid value!");
				System.out.println();
				System.out.print("Enter your choice: ");
				userChoice = in.nextLine().toUpperCase();
				userEnter = userChoice.charAt(0);
			}
		}
		return userEnter;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		boolean options = false;
		String input = "";
		String output = "";
		int shiftAMT = 0;
		displayMenu(input, output, shiftAMT);
		char userInput = getChoice(in, options);
		while (!(userInput == 'Q') && (input == "" || output == "" || shiftAMT == 0)) {
			if (userInput == 'S') {
				System.out.print("Enter an amount to shift: ");
				shiftAMT = Integer.parseInt(in.nextLine());
			} else if (userInput == 'O') {
				String msg = "Enter an output filename: ";
				output = promptForString(in, msg);
			} else if (userInput == 'I') {
				String msg = "Enter an input filename: ";
				input = promptForString(in, msg);
			}
			System.out.println();
			displayMenu(input, output, shiftAMT);
			if (!(input == "" || output == "" || shiftAMT == 0)) {
				options = true;
			}
			userInput = getChoice(in, options);
			if (!(input == "" || output == "" || shiftAMT == 0) && !(userInput == 'Q')) {
				if (userInput == 'E') {
					transformFile(input, output, shiftAMT);
					System.out.println("Finished writing to file: " + output);
					System.out.println();
				}
				else if (userInput == 'D') {
					shiftAMT = shiftAMT * -1;
					transformFile(input, output, shiftAMT);
					System.out.println("Finished writing to file: " + output);
					System.out.println();
				}
				displayMenu(input, output, shiftAMT);
				userInput = getChoice(in, options);
			}
		}
		System.out.println();
		System.out.println("Goodbye!");

		in.close();
	}
}