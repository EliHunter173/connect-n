import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;
import java.util.Random;
import java.io.PrintStream;

/**
 * This class contains various utility functions that are used
 * throughout the Connect-N game but aren't related to specific classes.
 * @author Eli W. Hunter
 */
public class Utils {

    /**
     * Determines if a string is a parsable int.
     * @param str The string to be checked.
     * @return True if the string (str) does contain a parsable int.
     */
    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * The default error message to be displayed if the user doesn't input
     * an integer.
     */
    public static String INT_ERROR_MESSAGE = "That is not an integer.";

    /**
     * Requests a int from the given input by printing the given output.
     * If the input is not a String, it displays an error message and recurses.
     * @param prompt The String to be displayed.
     * @param input The Scanner object from which input will be taken.
     * @param output The PrintSream object where all the messages and
     *     error messages will be displayed.
     * @return The integer that was successfuly received.
     */
    public static int getIntReprompting(String prompt, Scanner input, PrintStream output) {
        int answer;
        try {
            output.print(prompt);
            answer = input.nextInt();
        } catch (InputMismatchException e) {
            input.next(); // discard the failed input
            output.println(INT_ERROR_MESSAGE);
            return getIntReprompting(prompt, input, output); // try again
        }
        return answer;
    }

    /**
     * Requests a int from the given input by printing the given output.
     * If the input is not a String, it prints the given error message and
     * recurses.
     * @param prompt The String to be displayed as a prompt.
     * @param error The String to be displayed as an error message.
     * @param input The Scanner object from which input will be taken.
     * @param output The PrintSream object where all the messages and
     *     error messages will be displayed.
     * @return The integer that was successfuly received.
     */
    public static int getIntReprompting(String prompt, String error, Scanner input, PrintStream output) {
        int answer;
        try {
            output.print(prompt);
            answer = input.nextInt();
        } catch (InputMismatchException e) {
            input.next(); // discard the failed input
            output.println(error);
            return getIntReprompting(prompt, error, input, output); // try again
        }
        return answer;
    }

    /**
     * Repeats the given String a given number of times (count). This is done
     * because not all Java versions support stringObject.repeat(count).
     * @param str The String to be repeated.
     * @param count The number of times for the string (str) to be repeated.
     * @return The inputted string repeated count number of times.
     */
    public static String repeatString(String str, int count) {
        String repeatedString = "";
        for (int i = 0; i < count; i++) {
            repeatedString += str;
        }
        return repeatedString;
    }

    /**
     * Determines whether a String Array contains the given String.
     * @param array The String Array to be checked for the given String.
     * @param compareString The string to be searched for.
     * @return True if the String Array contains the given compare string.
     */
    public static boolean containsString(String[] array, String compareString) {
        for (String string : array) {
            if (string.equals(compareString))
                return true;
        }
        return false;
    }

    /**
     * Finds the largest integer in an integer array.
     * @param array The integer array that the largest value is being found in.
     * @return The largest integer in that array.
     */
    public static int max(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int n : array) {
            max = Math.max(max, n);
        }
        return max;
    }

    /**
     * Finds the indexes of all the matches of search number within the given
     * array.
     * @param array[] The integer array that is being searched thrrough.
     * @param searchNumber The integer being searched for in the array.
     * @return An array of all of the indexes of that contain a match with the
     *     search number.
     */
    public static int[] indexesOf(int[] array, int searchNumber) {
        // The max size is the entire array being a match
        int[] indexes = new int[array.length];
        int indexPointer = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == searchNumber)
                indexes[indexPointer++] = i;
        }
        // Reduce the size of the array to just fit the last added value
        indexes = Arrays.copyOf(indexes, indexPointer);
        return indexes;
    }

    /**
     * Picks a random integer from the given array.
     * @param array[] The integer array from which a random value is being
     *     chosen.
     * @return A randomly chosen integer from the given integer array.
     */
    public static int randomPick(int[] array) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(array.length);
        return array[randomIndex];
    }

    /**
     * The default radix used by getNumberOfDigits when no radix
     * is provided.
     */
    public static final int DEFAULT_RADIX = 10;

    /**
     * Determines the number of digits an integer has using the default radix
     * ({@value #DEFAULT_RADIX}).
     * @return The number of digits the given number has with the default
     *     radix.
     */
    public static int numberOfDigits(int number) {
        int radix = DEFAULT_RADIX;
        int numberOfDigits = 1;
        int maxNumberAtCurrentDigits = radix;
        while (maxNumberAtCurrentDigits < number) {
            numberOfDigits++;
            maxNumberAtCurrentDigits *= radix;
        }
        return numberOfDigits;
    }

    /**
     * Determines the number of digits an integer has given a specific radix.
     * @param radix The radix used to calculate the number of digits the
     *     integer would have
     * @return The number of digits the given number has with the given
     *     radix.
     */
    public static int numberOfDigits(int number, int radix) {
        int numberOfDigits = 1;
        int maxNumberAtCurrentDigits = radix;
        while (maxNumberAtCurrentDigits < number) {
            numberOfDigits++;
            maxNumberAtCurrentDigits *= radix;
        }
        return numberOfDigits;
    }

    /**
     * Creates an integer array from zero, inclusive, up to the given number,
     * exclusive.
     * @param max The highest number that will be excluded.
     * @return The range of numbers from zero up to the given max.
     */
    public static int[] range(int max) {
        int[] numbers = new int[max];
        for (int i = 0; i < max; i++) {
            numbers[i] = i;
        }
        return numbers;
    }

    /**
     * Returns the given object array that only contains the objects where the
     * corresponding value in the boolean array is true.
     * @param numbers The object array that will have some of its items
     *     returned where the corresponding boolean values are true.
     * @param booleans The boolean array that has the values which determine
     *     whether an object will be included in the returned array.
     * @throws IllegalArgumentException When the object array and boolean array
     *     are not of the same length.
     */
    public static int[] filter(int[] numbers, boolean[] booleans) {
        if (numbers.length != booleans.length) {
            throw new IllegalArgumentException(
                    "Two arrays must be of the same length to filter each other");
        }

        int pointer = 0;
        int[] newNumbers = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (booleans[i]) {
                newNumbers[pointer++] = numbers[i];
            }
        }
        // Reduce the length to the number added
        newNumbers = Arrays.copyOf(newNumbers, pointer);

        return newNumbers;
    }

}
