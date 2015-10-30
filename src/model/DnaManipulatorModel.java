package model;

import seq.nucleotide.ANucleotide;
import seq.sequence.DnaSequence;

import java.util.ArrayList;

/**
 * Created by heumos on 30.10.15.
 */
public class DnaManipulatorModel {

    private DnaSequence dnaSequence;

    public DnaManipulatorModel() {
        this.dnaSequence = new DnaSequence(null, new ArrayList<ANucleotide>());
    }

    public DnaSequence getDnaSequence() {
        return dnaSequence;
    }

    public void setDnaSequence(DnaSequence dnaSequence) {
        this.dnaSequence = dnaSequence;
    }
}
