package seq;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing a nucleotide.
 * Nucleotides (allowed) are: https://en.wikipedia.org/wiki/Nucleotide
 *
 */
public class Nucleotide {

    // the nucleotide given as a base
    private char base;

    /**
     * A nucleotide consists of one base.
     * @param base the nucleotide given as a char.
     */
    public Nucleotide(char base) {
        this.base = base;
    }

    /**
     * Get the nucleotide.
     * @return the nucleotide as char.
     */
    public char getBase() {
        return base;
    }

    /**
     * Set the nucleotide.
     * @param base the nucleotide given as a char.
     */
    public void setBase(char base) {
        this.base = base;
    }

    @Override public String toString() {
        return String.valueOf(this.base);
    }
}
