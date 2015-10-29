package seq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 29.10.15.
 *
 * Java class representing an DNA nucleotide sequence.
 * Each sequence consists of an identifier and the sequence data itself.
 *
 */
public class DnaSequence extends ASequence {

    /**
     * A sequences consists of a sequence identifier
     * and the sequence data.
     *
     * @param seqId        the sequence identifier.
     * @param sequenceData the sequence data.
     */
    public DnaSequence(String seqId, List<ANucleotide> sequenceData) {
        super(seqId, sequenceData);
    }

    public List<RnaNucleotide> toRnaSequence() {
        List<ANucleotide> rnaSeqData = new ArrayList<ANucleotide>(this.getSequenceData());
        char t = 't';
        char T = 'T';
        char u = 'u';
        char U = 'U';
        for (int i = 0; i < rnaSeqData.size(); i++) {
            ANucleotide aNucleotide = rnaSeqData.get(i);
            if (aNucleotide.getBase() == t) {
                aNucleotide.setBase(u);
            } else {
                if (aNucleotide.getBase() == T) {
                    aNucleotide.setBase(U);
                }
            }
        }
        return (List<RnaNucleotide>) (List<?>) rnaSeqData;
    }
}
