package seq.sequence;

import seq.nucleotide.ANucleotide;
import seq.nucleotide.DnaNucleotide;
import seq.nucleotide.RnaNucleotide;

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
        List<RnaNucleotide> rnaSeqData = new ArrayList<RnaNucleotide>();
        char t = 't';
        char T = 'T';
        char u = 'u';
        char U = 'U';
        for (int i = 0; i < this.seqLength(); i++) {
            ANucleotide aNucleotide = rnaSeqData.get(i);
            if (aNucleotide.getBase() == t) {
                rnaSeqData.add(new RnaNucleotide(u));
            } else {
                if (aNucleotide.getBase() == T) {
                    rnaSeqData.add(new RnaNucleotide(U));
                } else {
                    rnaSeqData.add(new RnaNucleotide(this.getSequenceData().get(i).getBase()));
                }
            }
        }
        return rnaSeqData;
    }

    @Override
    public List<ANucleotide> complementarySeq() {
        List<ANucleotide> dnaNucleotidesComplementary = new ArrayList<ANucleotide>();
        for (int i = 0; i < this.seqLength(); i++) {
            ANucleotide aNucleotide = this.getSequenceData().get(i);
            dnaNucleotidesComplementary.add(new DnaNucleotide(aNucleotide.complementary().getBase()));
        }
        return dnaNucleotidesComplementary;
    }

    public static DnaSequence parseStringToSequence(String s) {
        List<ANucleotide> dnaNucleotides = new ArrayList<ANucleotide>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '\n') {
                dnaNucleotides.add(new DnaNucleotide(c));
            }
        }
        return new DnaSequence(null, dnaNucleotides);
    }
}
