package seq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by heumos on 29.10.15.
 *
 * Java class representing a nucleotide sequence.
 * Each sequence consists of an identifier and the sequence data itself.
 *
 */
public abstract class ASequence {

    // the identifier of the sequence
    private String seqId;

    // the sequence data of the sequence
    private List<ANucleotide> sequenceData;

    /**
     * A sequences consists of a sequence identifier
     * and the sequence data.
     * @param seqId the sequence identifier.
     * @param sequenceData the sequence data.
     */
    public ASequence(String seqId, List<ANucleotide> sequenceData) {
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
    public List<ANucleotide> getSequenceData() {
        return sequenceData;
    }

    /**
     * Set the sequence data.
     * @param sequenceData the sequence data.
     */
    public void setSequenceData(List<ANucleotide> sequenceData) {
        this.sequenceData = sequenceData;
    }

    public double getGcContent() {
        String gC = "GCcg";
        double gCCount = this.sequenceData
                .stream()
                .filter(aNucleotide -> gC.contains(String.valueOf(aNucleotide.getBase())))
                .count();
        return gCCount / (double) this.sequenceData.size();
    }

    public int getSeqLength() {
        return this.sequenceData.size();
    }

    public List<ANucleotide> getReverseSeq() {
        List<ANucleotide> sequenceDataReverse = new ArrayList<ANucleotide>(this.sequenceData);
        Collections.reverse(sequenceDataReverse);
        return sequenceDataReverse;
    }

    protected abstract void getComplementarySeq();

    protected abstract void getReverseComplementarySeq();

    public List<ANucleotide> toUpperCase() {
        return this.sequenceData
                .stream()
                .map(ANucleotide::toUpperCase)
                .collect(Collectors.toList());
    }

    public List<ANucleotide> toLowerCase() {
        return this.sequenceData
                .stream()
                .map(ANucleotide::toLowerCase)
                .collect(Collectors.toList());
    }

}
