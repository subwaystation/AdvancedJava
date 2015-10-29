import seq.ANucleotide;
import seq.RnaNucleotide;
import seq.RnaSequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 29.10.15.
 */
public class StartDnaManipulator {

    List<ANucleotide> rnaNucleotides = new ArrayList<ANucleotide>();
    RnaNucleotide rnaNucleotide = new RnaNucleotide('A');
    List<RnaNucleotide> blubb;

    RnaSequence rnaSequence = new RnaSequence("abc", rnaNucleotides);

    public StartDnaManipulator() {
        this.rnaNucleotides.add(rnaNucleotide);
        this.blubb = (List<RnaNucleotide>) (List<?>) this.rnaSequence.getSequenceData();
    }
}
