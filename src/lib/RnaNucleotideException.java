package lib;

/**
 * Created by heumos on 26.10.15.
 *
 * A java class representing a error exception
 * that is raised when a char is not a conform
 * RNA nucleotide.
 *
 */
public class RnaNucleotideException extends Exception {

    public RnaNucleotideException(String message) {
        super(message);
    }

}
