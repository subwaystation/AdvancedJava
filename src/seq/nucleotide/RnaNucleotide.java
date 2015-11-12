package seq.nucleotide;

import lib.RnaNucleotideException;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing an RNA nucleotide.
 *
 */
public class RnaNucleotide extends ANucleotide {

    // allowed RNA nucleotides
    public static final String ALLOWED_RNA_NUCLEOTIDES = "acug-ACUG";

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

    @Override
    public RnaNucleotide complementary() {
        switch (this.getBase()) {
            case 'a':
                this.setBase('u');
                break;
            case 'A':
                this.setBase('U');
                break;
            case 'c':
                this.setBase('g');
                break;
            case 'C':
                this.setBase('G');
                break;
            case 'u':
                this.setBase('a');
                break;
            case 'U':
                this.setBase('A');
                break;
            case 'g':
                this.setBase('c');
                break;
            case 'G':
                this.setBase('G');
                break;
            default:
                break;
        }
        return  this;
    }

}
