package ui.dna_manipulator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lib.StringFormatter;
import model.DnaManipulatorModel;
import ui.StageManager;

import java.util.Arrays;

/**
 * Created by heumos on 03.11.15.
 */
public class DnaManipulatorVC {

    // view
    private DnaManipulatorView dnaManipulatorView;

    public DnaManipulatorVC() {
        this.dnaManipulatorView = new DnaManipulatorView();

        // register event_handling handler
        this.dnaManipulatorView.getFlipB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performFlip(this.dnaManipulatorView));
        this.dnaManipulatorView.getFilterB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performFilter(this.dnaManipulatorView));
        this.dnaManipulatorView.getUpperCaseB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performUpperCase(this.dnaManipulatorView));
        this.dnaManipulatorView.getLowerCaseB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performLowerCase(this.dnaManipulatorView));
        this.dnaManipulatorView.getToRnaB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performToRna(this.dnaManipulatorView));
        this.dnaManipulatorView.getReverseB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performReverse(this.dnaManipulatorView));
        this.dnaManipulatorView.getComplementaryB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performComplementary(this.dnaManipulatorView));
        this.dnaManipulatorView.getReverseComplementaryB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performReverseComplementary(this.dnaManipulatorView));
        this.dnaManipulatorView.getgCContentB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performGCContent(this.dnaManipulatorView));
        this.dnaManipulatorView.getLengthB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performSeqLength(this.dnaManipulatorView));
        this.dnaManipulatorView.getClearB().setOnAction((actionEvent) ->
                DnaManipulatorVP.performClearing(this.dnaManipulatorView));
        this.dnaManipulatorView.getLineWidthS().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String resultSeq = new String(dnaManipulatorView.getResultSeqTA().getText());
                String[] resultSeqSplit = resultSeq.split("\n");
                StringBuilder stringBuilder = new StringBuilder();
                for (String base : resultSeqSplit) {
                    stringBuilder.append(base);
                }
                dnaManipulatorView.getResultSeqTA().setText(StringFormatter.formatStringSequence(stringBuilder.toString(), (int) Math.rint((double) newValue)));
            }
        });
    }

    public void show() {
        this.dnaManipulatorView.show(StageManager.getInstance().getPrimaryStage());
    }
}
