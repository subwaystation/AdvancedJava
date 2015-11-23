package ui.rna_drawer;

import lib.sec_struct.Nussinov;
import model.RnaDrawerModel;
import seq.sequence.RnaSequence;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVP {

    public static void handleEnteredSeqChange(RnaDrawerView rnaDrawerView) {

        String rnaSeq = rnaDrawerView.getEnterSeqTA().getText().replace("\n", "");

        // check if valid RNA sequence
        if (RnaSequence.validateRnaString(rnaSeq)) {
            rnaDrawerView.getFoldB().setDisable(false);
        } else {
            rnaDrawerView.getFoldB().setDisable(true);
            rnaDrawerView.getDrawB().setDisable(true);
            // TODO print information
        }
    }

    public static void handleSecStructChange(RnaDrawerView rnaDrawerView) {
        String secStruct = rnaDrawerView.getSecStructTA().getText().replace("\n", "");

        // check if string contains only dots and brackets
        boolean validateDotBrackets = RnaDrawerModel.validateDotBrackets(secStruct);
        boolean validateLength = (secStruct.length() == rnaDrawerView.getEnterSeqTA().getText().length());
        if (validateDotBrackets && validateLength) {
            rnaDrawerView.getDrawB().setDisable(false);
        } else {
            rnaDrawerView.getDrawB().setDisable(true);
        }
    }

    public static void handleFoldBEvent(RnaDrawerView rnaDrawerView) {
        String rnaSeq = rnaDrawerView.getEnterSeqTA().getText();
        Nussinov nussinov = new Nussinov(rnaSeq);
        String secStruct = nussinov.getBracketNotation();
        rnaDrawerView.getSecStructTA().setText(secStruct);
    }


}
