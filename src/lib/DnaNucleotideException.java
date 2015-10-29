package lib;

/**
 * Created by heumos on 29.10.15.
 *
 * A java class representing a error exception
 * that is raised when a char is not a conform
 * DNA nucleotide.
 *
 */
public class DnaNucleotideException extends Exception {

    public DnaNucleotideException(String message) {
        super(message);
    }

}
