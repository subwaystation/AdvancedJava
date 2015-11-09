package cd_hit;

/**
 * Created by heumos on 09.11.15.
 *
 * Java class representing an entry of a cluster in an .clsr file
 * originating from CD_HIT.
 *
 */
public class ClsrEntry {

    // the length in nucleotides of the sequence
    private int seqLength;

    // the sequence identifier
    private String seqId;

    // the similarity of the sequence in the cluster
    private double seqSimilarity;

    // the strain corresponding to the sequence identifier
    private String strain;

    public ClsrEntry(int seqLength, String seqId, double seqSimilarity) {
        this.seqLength = seqLength;
        this.seqId = seqId;
        this.seqSimilarity = seqSimilarity;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public int getSeqLength() {
        return seqLength;
    }

    public void setSeqLength(int seqLength) {
        this.seqLength = seqLength;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public double getSeqSimilarity() {
        return seqSimilarity;
    }

    public void setSeqSimilarity(double seqSimilarity) {
        this.seqSimilarity = seqSimilarity;
    }

    @Override
    public String toString() {
        String tab = "\t";
        StringBuilder sB = new StringBuilder();
        sB.append(this.seqId).append(tab);
        sB.append(this.seqLength).append(tab);
        sB.append(this.seqSimilarity);
        return sB.toString();
    }

    @Override
    public  ClsrEntry clone() {
        int seqLength = this.getSeqLength();
        String seqId = new String(this.getSeqId());
        double seqSimilarity = this.getSeqSimilarity();
        return new ClsrEntry(seqLength, seqId, seqSimilarity);
    }

}
