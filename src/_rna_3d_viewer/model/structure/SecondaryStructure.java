package _rna_3d_viewer.model.structure;

import javafx.util.Pair;

import java.util.List;

/**
 * A secondary structure.
 * Consists of sequence, secondary structure as pairs and the corresponding
 * dot-bracket notation.
 *
 * Created by heumos on 03.02.16.
 */
public class SecondaryStructure {

    // sequence
    private String sequence;

    // the pairs of the secondary structure
    private List<Pair<Integer,Integer>> secondaryStructure;

    // dot bracket notation
    private String dotBracketsNotation;

    /**
     * create secondary structure
     * @param sequence the primary sequence
     * @param secondaryStructure the secondary structure in pair representation
     * @param dotBracketsNotation dot-bracket notation of secondary structure
     */
    public SecondaryStructure(String sequence, List<Pair<Integer, Integer>> secondaryStructure,
                              String dotBracketsNotation) {
        this.secondaryStructure = secondaryStructure;
        this.sequence = sequence;
        this.dotBracketsNotation = dotBracketsNotation;
    }

    public String getSequence() {
        return sequence;
    }

    public List<Pair<Integer, Integer>> getSecondaryStructure() {
        return secondaryStructure;
    }

    public String getDotBracketsNotation() {
        return  this.dotBracketsNotation;
    }
}

