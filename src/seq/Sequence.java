package seq;

import java.util.ArrayList;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing a nucleotide sequence.
 * Each sequence consists of an identifier and the sequence data itself.
 *
 */
public class Sequence {

    // the identifier of the sequence
    private String seqId;

    // the sequence data of the sequence
    private ArrayList<RnaNucleotide> sequenceData;

    /**
     * A sequences consists of a sequence identifier
     * and the sequence data.
     * @param seqId the sequence identifier.
     * @param sequenceData the sequence data.
     */
    public Sequence(String seqId, ArrayList<RnaNucleotide> sequenceData) {
        this.seqId = seqId;
        this.sequenceData = sequenceData;
    }

    /**
     * Get the sequence identifier.
     * @return
     */
    public String getSeqId() {
        return seqId;
    }

    /**
     * Set the sequence identifer.
     * @param seqId the sequence identifer.
     */
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    /**
     * Get the sequence data.
     * @return the sequence data as ArrayList.
     */
    public ArrayList<RnaNucleotide> getSequenceData() {
        return sequenceData;
    }

    /**
     * Set the sequence data.
     * @param sequenceData the sequence data.
     */
    public void setSequenceData(ArrayList<RnaNucleotide> sequenceData) {
        this.sequenceData = sequenceData;
    }
}
