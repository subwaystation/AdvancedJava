package ui.dna_manipulator;

import javafx.beans.value.ObservableValue;
import model.DnaManipulatorModel;

/**
 * Created by heumos on 03.11.15.
 */
public class DnaManipulatorPresenter {

    // model
    private final DnaManipulatorModel dnaManipulatorModel;

    // view
    private final DnaManipulatorView dnaManipulatorView;

    public DnaManipulatorPresenter(DnaManipulatorModel dnaManipulatorModel, DnaManipulatorView dnaManipulatorView) {
        this.dnaManipulatorModel = dnaManipulatorModel;
        this.dnaManipulatorView = dnaManipulatorView;
        attachEvents();
    }

    private void attachEvents() {
        this.dnaManipulatorView.flipB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performFlip());
        this.dnaManipulatorView.filterB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performFilter());
        this.dnaManipulatorView.upperCaseB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performUpperCase());
        this.dnaManipulatorView.lowerCaseB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performLowerCase());
        this.dnaManipulatorView.toRnaB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performToRna());
        this.dnaManipulatorView.reverseB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performReverse());
        this.dnaManipulatorView.complementaryB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performComplementary());
        this.dnaManipulatorView.reverseComplementaryB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performReverseComplementary());
        this.dnaManipulatorView.gCContentB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performGCContent());
        this.dnaManipulatorView.lengthB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performSeqLength());
        this.dnaManipulatorView.clearB.setOnAction((actionEvent) ->
                this.dnaManipulatorView.performClearing());
        this.dnaManipulatorView.lineWidthS.valueProperty().addListener(this::handleSliderChange);
    }

    private void handleSliderChange(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        this.dnaManipulatorView.updateResultSeqTaWidth(observable, oldValue, newValue);
    }
}
