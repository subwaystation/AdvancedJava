package _rna_3d_viewer.seq.nucleotide;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing a nucleotide.
 *
 */
public abstract class ANucleotide {

    // the nucleotide given as a base
    private char base;

    // the residue index
    private  int residueIndex;

    /**
     * A nucleotide consists of one base.
     * @param base the nucleotide given as a char.
     */
    public ANucleotide(char base) {
        if (validateNucleotide(base)) {
            this.base = base;
        } else {
            throwIllegalNucleotideException(base);
        }
    }

    /**
     * Validate the given char.
     * @return boolean if the given char was a valid nucleotide.
     */
    protected abstract boolean validateNucleotide(char base);

    /**
     * Throw Exception if the nucleotide validation did not pass.
     */
    protected abstract void throwIllegalNucleotideException(char base);

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

    public ANucleotide toUpperCase() {
        this.base = Character.toUpperCase(this.base);
        return this;
    }

    public ANucleotide toLowerCase() {
        this.base = Character.toLowerCase(this.base);
        return this;
    }

    public abstract ANucleotide complementary();

    public int getResidueIndex() {
        return this.residueIndex;
    }

    public void setResidueIndex(int residueIndex) {
        this.residueIndex = residueIndex;
    }
}