package seq.sequence;

import seq.nucleotide.ANucleotide;
import seq.nucleotide.RnaNucleotide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 14.10.15.
 *
 * Java class representing an RNA nucleotide sequence.
 * Each sequence consists of an identifier and the sequence data itself.
 *
 */
public class RnaSequence extends ASequence {

    /**
     * A sequences consists of a sequence identifier
     * and the sequence data.
     *
     * @param seqId the sequence identifier.
     * @param sequenceData the sequence data.
     */
    public RnaSequence(String seqId, List<ANucleotide> sequenceData) {
        super(seqId, sequenceData);
    }

    @Override
    public List<ANucleotide> complementarySeq() {
        List<ANucleotide> rnaNucleotidesComplementary = new ArrayList<ANucleotide>();
        for (int i = 0; i < this.seqLength(); i++) {
            ANucleotide aNucleotide = this.getSequenceData().get(i);
            rnaNucleotidesComplementary.add(new RnaNucleotide(aNucleotide.complementary().getBase()));
        }
        return rnaNucleotidesComplementary;
    }

}
