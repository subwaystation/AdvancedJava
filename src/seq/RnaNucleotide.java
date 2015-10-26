package seq;

import lib.RnaNucleotideException;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing a nucleotide.
 * Nucleotides (allowed) are: https://en.wikipedia.org/wiki/Nucleotide
 *
 */
public class RnaNucleotide {

    // allowed RNA nucleotides
    private static final String ALLOWED_RNA_NUCLEOTIDES = "acug-ACUG";

    // the nucleotide given as a base
    private char base;

    /**
     * A nucleotide consists of one base.
     * @param base the nucleotide given as a char.
     */
    public RnaNucleotide(char base) throws RnaNucleotideException {
        if (ALLOWED_RNA_NUCLEOTIDES.contains(String.valueOf(base))) {
            this.base = base;
        } else {
            throw  new RnaNucleotideException("ERROR: Unallowed character for RnaNucleotide: "
                    + String.valueOf(base));
        }
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
