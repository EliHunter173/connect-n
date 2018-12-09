/**
 * This class simply contains the 16-bit ANSI color escape
 * sequences that are supported on most modern terminals.
 * @author Eli W. Hunter
 */
public class Color {

    //public static final String BLACK =   "\u001B[30m";
    /** The ANSI escape sequence for black. */
    public static final String BLACK =   "\033[30;0m";
    /** The ANSI escape sequence for red. */
    public static final String RED =     "\033[31;0m";
    /** The ANSI escape sequence for green. */
    public static final String GREEN =   "\033[32;0m";
    /** The ANSI escape sequence for yellow. */
    public static final String YELLOW =  "\033[33;0m";
    /** The ANSI escape sequence for blue. */
    public static final String BLUE =    "\033[34;0m";
    /** The ANSI escape sequence for magenta. */
    public static final String MAGENTA = "\033[35;0m";
    /** The ANSI escape sequence for cyan. */
    public static final String CYAN =    "\033[36;0m";
    /** The ANSI escape sequence for white. */
    public static final String WHITE =   "\033[37;0m";

    /** The ANSI escape sequence for bold black. */
    public static final String BOLD_BLACK =   "\033[30;1m";
    /** The ANSI escape sequence for bold red. */
    public static final String BOLD_RED =     "\033[31;1m";
    /** The ANSI escape sequence for bold green. */
    public static final String BOLD_GREEN =   "\033[32;1m";
    /** The ANSI escape sequence for bold yellow. */
    public static final String BOLD_YELLOW =  "\033[33;1m";
    /** The ANSI escape sequence for bold blue. */
    public static final String BOLD_BLUE =    "\033[34;1m";
    /** The ANSI escape sequence for bold magenta. */
    public static final String BOLD_MAGENTA = "\033[35;1m";
    /** The ANSI escape sequence for bold cyan. */
    public static final String BOLD_CYAN =    "\033[36;1m";
    /** The ANSI escape sequence for bold white. */
    public static final String BOLD_WHITE =   "\033[37;1m";

}
