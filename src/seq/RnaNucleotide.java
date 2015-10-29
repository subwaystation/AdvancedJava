package seq;

import lib.RnaNucleotideException;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing an RNA nucleotide.
 *
 */
public class RnaNucleotide extends ANucleotide {

    // allowed RNA nucleotides
    private static final String ALLOWED_RNA_NUCLEOTIDES = "acug-ACUG";

    /**
     * A nucleotide consists of one base.
     * @param base the nucleotide given as a char.
     */
    public RnaNucleotide(char base) {
        super(base);
    }

    @Override
    public boolean validateNucleotide(char base) {
        return ALLOWED_RNA_NUCLEOTIDES.contains(String.valueOf(base));
    }

    @Override
    public void throwIllegalNucleotideException(char base) {
        try {
            throw  new RnaNucleotideException("ERROR: Illegal character for RnaNucleotide: "
                    + String.valueOf(base));
        } catch (RnaNucleotideException e) {
            e.printStackTrace();
        }
    }

}
