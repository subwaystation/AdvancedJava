package ui.dna_manipulator;

import lib.StringFormatter;
import seq.nucleotide.ANucleotide;
import seq.sequence.ASequence;
import seq.sequence.DnaSequence;

import java.util.List;

/**
 * Created by heumos on 03.11.15.
 */
public class DnaManipulatorVP {

    protected static void performFlip(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = new String(dnaManipulatorView.getEnterSeqTA().getText());
        String resultedSeq = new String(dnaManipulatorView.getResultSeqTA().getText());
        dnaManipulatorView.getEnterSeqTA().setText(resultedSeq);
        dnaManipulatorView.getResultSeqTA().setText(enteredSeq);
    }

    protected static void performFilter(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(dnaSequence.seqToString(lineWidth));
    }

    protected static void performUpperCase(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        List<ANucleotide> aNucleotideList = dnaSequence.toUpperCase();
        System.out.println(ASequence.seqToString(aNucleotideList, lineWidth));
        dnaManipulatorView.getResultSeqTA().setText(ASequence.seqToString(aNucleotideList, lineWidth));
    }

    protected static void performLowerCase(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        List<ANucleotide> aNucleotideList = dnaSequence.toLowerCase();
        dnaManipulatorView.getResultSeqTA().setText(ASequence.seqToString(aNucleotideList, lineWidth));
    }

    protected static void performToRna(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(ASequence.
                seqToString((List<ANucleotide>) (List<?>) dnaSequence.toRnaSequence(), lineWidth));
    }

    protected static void performReverse(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(ASequence.
                seqToString(dnaSequence.reverseSeq(), lineWidth));
    }

    protected static void performComplementary(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(ASequence.
                seqToString(dnaSequence.complementarySeq(), lineWidth));
    }

    protected static void performReverseComplementary(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(ASequence.
                seqToString(dnaSequence.reverseComplementarySeq(), lineWidth));
    }

    protected static void performGCContent(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(StringFormatter.
                formatStringSequence(String.valueOf(dnaSequence.calcGcContent() * 100) + "%", lineWidth));
    }

    protected static void performSeqLength(DnaManipulatorView dnaManipulatorView) {
        String enteredSeq = dnaManipulatorView.getEnterSeqTA().getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) dnaManipulatorView.getLineWidthS().getValue();
        dnaManipulatorView.getResultSeqTA().setText(StringFormatter.
                formatStringSequence(String.valueOf(dnaSequence.seqLength()), lineWidth));
    }

    protected static void performClearing(DnaManipulatorView dnaManipulatorView) {
        dnaManipulatorView.getResultSeqTA().setText("");
        dnaManipulatorView.getEnterSeqTA().setText("");
    }

}
