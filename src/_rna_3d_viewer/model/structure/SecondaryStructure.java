package _rna_3d_viewer.model.structure;

import javafx.util.Pair;

import java.util.List;

/**
 * Created by heumos on 03.02.16.
 */
public class SecondaryStructure {

    // sequence
    private String sequence;

    // the pairs of the secondary structure
    private List<Pair<Integer,Integer>> secondaryStructure;

    // dot bracket notation
    private String dotBracketsNotation;

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

