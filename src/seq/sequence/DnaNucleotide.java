package seq.sequence;

import lib.DnaNucleotideException;
import seq.nucleotide.ANucleotide;

/**
 * Created by heumos on 29.10.15.
 *
 * Java class representing an RNA nucleotide.
 *
 */
public class DnaNucleotide extends ANucleotide {

    // allowed DNA nucleotides
    private static final String ALLOWED_DNA_NUCLEOTIDES = "actg-ACTG";

    /**
     * A nucleotide consists of one base.
     * @param base the nucleotide given as a char.
     */
    public DnaNucleotide(char base) {
        super(base);
    }

    @Override
    public boolean validateNucleotide(char base) {
        return ALLOWED_DNA_NUCLEOTIDES.contains(String.valueOf(base));
    }

    @Override
    public void throwIllegalNucleotideException(char base) {
        try {
            throw  new DnaNucleotideException("ERROR: Illegal character for DnaNucleotide: "
                    + String.valueOf(base));
        } catch (DnaNucleotideException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DnaNucleotide complementary() {
        switch (this.getBase()) {
            case 'a':
                this.setBase('t');
                break;
            case 'A':
                this.setBase('T');
                break;
            case 'c':
                this.setBase('g');
                break;
            case 'C':
                this.setBase('G');
                break;
            case 't':
                this.setBase('a');
                break;
            case 'T':
                this.setBase('A');
                break;
            case 'g':
                this.setBase('c');
                break;
            case 'G':
                this.setBase('G');
                break;
            default:
                throwIllegalNucleotideException(this.getBase());
                break;
        }
        return  this;
    }

}
