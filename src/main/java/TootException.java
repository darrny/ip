/**
 * Represents an input error that Toot can explain to the user.
 */
public class TootException extends Exception {
    /**
     * Creates an exception with a user-friendly explanation of the input error.
     *
     * @param message Explanation of the error and, where useful, how to correct it.
     */
    public TootException(String message) {
        super(message);
    }
}
