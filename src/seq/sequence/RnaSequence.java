package seq.sequence;

import seq.nucleotide.ANucleotide;
import seq.nucleotide.DnaNucleotide;
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

    public List<DnaNucleotide> toDnaSeq() {
        List<DnaNucleotide> dnaSeqData = new ArrayList<>();
        char t = 't';
        char T = 'T';
        char u = 'u';
        char U = 'U';
        for (int i = 0; i < this.seqLength(); i++) {
            ANucleotide aNucleotide = getSequenceData().get(i);
            if (aNucleotide.getBase() == u) {
                dnaSeqData.add(new DnaNucleotide(t));
            } else {
                if (aNucleotide.getBase() == U) {
                    dnaSeqData.add(new DnaNucleotide(T));
                } else {
                    dnaSeqData.add(new DnaNucleotide(this.getSequenceData().get(i).getBase()));
                }
            }
        }
        return dnaSeqData;
    }

    public static RnaSequence parseStringToSequence(String s) {
        List<ANucleotide> rnaNucleotides = new ArrayList<ANucleotide>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '\n' && RnaNucleotide.ALLOWED_RNA_NUCLEOTIDES.contains(String.valueOf(c))) {
                rnaNucleotides.add(new RnaNucleotide(c));
            }
        }
        return new RnaSequence(null, rnaNucleotides);
    }

}
