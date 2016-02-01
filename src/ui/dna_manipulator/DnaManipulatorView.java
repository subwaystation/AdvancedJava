package ui.dna_manipulator;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lib.StringFormatter;
import model.DnaManipulatorModel;
import _rna_3d_viewer.seq.nucleotide.ANucleotide;
import _rna_3d_viewer.seq.sequence.ASequence;
import _rna_3d_viewer.seq.sequence.DnaSequence;
import _rna_3d_viewer.seq.sequence.RnaSequence;
import _rna_3d_viewer.seq.sequence.SeqUtils;

import java.util.List;

/**
 * Created by heumos on 03.11.15.
 */
public class DnaManipulatorView extends GridPane{

    private final DnaManipulatorModel dnaManipulatorModel;

    // the text area in which the user can enter a DNA sequence
    public TextArea enterSeqTA;

    // the button which allows the user to flip the text between
    // enterSeqTA and resultSeqTA
    public Button flipB;

    // the text area in which the results of a user interaction
    // is displayed
    public TextArea resultSeqTA;

    // the grid pane in which all button options are placed
    public GridPane optionsGB;

    // filter to rna button
    public Button filterToRnaB;

    // upper case button
    public Button upperCaseB;

    // lower case button
    public Button lowerCaseB;

    // to RNA button
    public Button toRnaB;

    // reverse seq button
    public Button reverseB;

    // complementary seq button
    public Button complementaryB;

    // reverse complementary seq button
    public Button reverseComplementaryB;

    // GC content button
    public Button gCContentB;

    // seq length button
    public Button lengthB;

    // clear button
    public Button clearB;

    // the slider
    public Slider lineWidthS;

    // the label on which the current seq status is printed
    public Label seqStatusL;

    // to dna button
    public Button toDnaB;

    // filter to dna button
    public Button filterToDnaB;

    // close button
    public Button closeB;

    public DnaManipulatorView(DnaManipulatorModel dnaManipulatorModel) {
        this.dnaManipulatorModel = dnaManipulatorModel;
        initThis();

        this.enterSeqTA = new TextArea();
        this.enterSeqTA.setFont(Font.font("Monospaced", 13));
        this.add(this.enterSeqTA, 0, 0);
        this.setHgrow(this.enterSeqTA, Priority.ALWAYS);
        this.flipB = new Button("flip");
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(this.flipB);
        flowPane.setAlignment(Pos.CENTER);
        this.add(flowPane, 0, 1);
        this.resultSeqTA = new TextArea();
        this.resultSeqTA.setFont(Font.font("Monospaced", 13));
        this.add(resultSeqTA, 0, 2);

        initOptionsGB();

        initLineWidthS();
    }

    private void initLineWidthS() {
        this.lineWidthS = new Slider();
        this.lineWidthS.setMin(10);
        this.lineWidthS.setMax(50);
        this.lineWidthS.setShowTickLabels(true);
        this.lineWidthS.setShowTickMarks(true);
        this.lineWidthS.setBlockIncrement(10);
        this.lineWidthS.setMinorTickCount(5);
        this.lineWidthS.setValue(40);
        this.lineWidthS.setMajorTickUnit(10);
        this.lineWidthS.snapToTicksProperty().set(true);
        this.lineWidthS.setPadding(new Insets(0, 60, 0, 60));
        Label label = new Label("choose line width:");
        label.setPadding(new Insets(0, 60, 0, 60));
        this.add(label, 0, 4);
        this.add(this.lineWidthS, 0, 5);
        this.seqStatusL = new Label("No sequence entered.");
        this.seqStatusL.setPadding(new Insets(0, 200, 0, 200));
        this.seqStatusL.setTextFill(Color.RED);
        this.seqStatusL.setTextAlignment(TextAlignment.CENTER);
        this.seqStatusL.setAlignment(Pos.CENTER);
        this.setHalignment(this.seqStatusL, HPos.CENTER);
        this.add(this.seqStatusL, 0, 6);
        this.closeB = new Button("close application");
        this.setHalignment(this.closeB, HPos.CENTER);
        this.add(this.closeB, 0, 7);
        this.closeB.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        this.closeB.setId("close-button");
    }

    private void initOptionsGB() {
        this.optionsGB = new GridPane();
        this.optionsGB.setHgap(4);
        double colConstraintWidth = 100.0 / 3.0;
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(colConstraintWidth);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(colConstraintWidth);
        ColumnConstraints col3Constraints = new ColumnConstraints();
        col3Constraints.setPercentWidth(colConstraintWidth);
        this.optionsGB.getColumnConstraints().addAll(col1Constraints, col2Constraints, col3Constraints);

        this.filterToRnaB = new Button("filter to RNA");
        this.filterToRnaB.setMaxWidth(Double.MAX_VALUE);
        this.filterToRnaB.setDisable(true);

        this.upperCaseB = new Button("upper case");
        this.upperCaseB.setMaxWidth(Double.MAX_VALUE);
        this.upperCaseB.setDisable(true);

        this.lowerCaseB = new Button("lower case");
        this.lowerCaseB.setMaxWidth(Double.MAX_VALUE);
        this.lowerCaseB.setDisable(true);

        this.toRnaB = new Button("to RNA");
        this.toRnaB.setMaxWidth(Double.MAX_VALUE);
        this.toRnaB.setDisable(true);

        this.filterToDnaB = new Button("filter to DNA");
        this.filterToDnaB.setMaxWidth(Double.MAX_VALUE);
        this.filterToDnaB.setDisable(true);

        this.toDnaB = new Button("to DNA");
        this.toDnaB.setMaxWidth(Double.MAX_VALUE);
        this.toDnaB.setDisable(true);

        this.reverseB = new Button("reverse");
        this.reverseB.setMaxWidth(Double.MAX_VALUE);
        this.reverseB.setDisable(true);

        this.complementaryB = new Button("complementary");
        this.complementaryB.setMaxWidth(Double.MAX_VALUE);
        this.complementaryB.setDisable(true);

        this.reverseComplementaryB = new Button("reverse-complementary");
        this.reverseComplementaryB.setMaxWidth(Double.MAX_VALUE);
        this.reverseComplementaryB.setDisable(true);

        this.gCContentB = new Button("GC content");
        this.gCContentB.setMaxWidth(Double.MAX_VALUE);
        this.gCContentB.setDisable(true);

        this.lengthB = new Button("length");
        this.lengthB.setMaxWidth(Double.MAX_VALUE);
        this.lengthB.setDisable(true);

        this.clearB = new Button("clear");
        this.clearB.setMaxWidth(Double.MAX_VALUE);
        this.clearB.setDisable(true);

        this.optionsGB.add(filterToRnaB, 0, 0);
        this.optionsGB.add(upperCaseB, 1, 0);
        this.optionsGB.add(lowerCaseB, 2, 0);
        this.optionsGB.add(toRnaB, 0, 1);
        this.optionsGB.add(reverseB, 1, 1);
        this.optionsGB.add(complementaryB, 2, 1);
        this.optionsGB.add(reverseComplementaryB, 0, 2);
        this.optionsGB.add(gCContentB, 1, 2);
        this.optionsGB.add(lengthB, 2, 2);
        this.optionsGB.add(clearB, 0, 3);
        this.optionsGB.add(filterToDnaB, 1, 3);
        this.optionsGB.add(toDnaB, 2, 3);

        this.add(this.optionsGB, 0, 3);
    }

    private void initThis() {
        this.setPadding(new Insets(0, 50, 0, 50));
        this.setVgap(20);
    }

    public void performFlip() {
        String enteredSeq = new String(this.enterSeqTA.getText());
        String resultedSeq = new String(this.resultSeqTA.getText());
        this.enterSeqTA.setText(resultedSeq);
        this.resultSeqTA.setText(enteredSeq);
    }

    public void performFilterToRna() {
        String enteredSeq = new String(this.enterSeqTA.getText());
        RnaSequence rnaSequence = RnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(rnaSequence.seqToString(lineWidth));
    }

    public void performUpperCase() {
        String enteredSeq = new String(this.enterSeqTA.getText());
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        List<ANucleotide> aNucleotideList = dnaSequence.toUpperCase();
        System.out.println(ASequence.seqToString(aNucleotideList, lineWidth));
        this.resultSeqTA.setText(ASequence.seqToString(aNucleotideList, lineWidth));
    }

    public void performLowerCase() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        List<ANucleotide> aNucleotideList = dnaSequence.toLowerCase();
        this.resultSeqTA.setText(ASequence.seqToString(aNucleotideList, lineWidth));
    }

    public void performToRna() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(ASequence.
                seqToString((List<ANucleotide>) (List<?>) dnaSequence.toRnaSequence(), lineWidth));
    }

    public void performReverse() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(ASequence.
                seqToString(dnaSequence.reverseSeq(), lineWidth));
    }

    public void performComplementary() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(ASequence.
                seqToString(dnaSequence.complementarySeq(), lineWidth));
    }

    public void performReverseComplementary() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(ASequence.
                seqToString(dnaSequence.reverseComplementarySeq(), lineWidth));
    }

    public void performGCContent() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(StringFormatter.
                formatStringSequence(String.valueOf(dnaSequence.calcGcContent() * 100) + "%", lineWidth));
    }

    public void performSeqLength() {
        String enteredSeq = this.enterSeqTA.getText();
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(StringFormatter.
                formatStringSequence(String.valueOf(dnaSequence.seqLength()), lineWidth));
    }

    public void performClearing() {
        this.resultSeqTA.setText("");
        this.enterSeqTA.setText("");
    }

    public void updateResultSeqTaWidth(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        String resultSeq = new String(this.resultSeqTA.getText());
        String[] resultSeqSplit = resultSeq.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String base : resultSeqSplit) {
            stringBuilder.append(base);
        }
        this.resultSeqTA.setText(StringFormatter.formatStringSequence(stringBuilder.toString(), (int) Math.rint((double) newValue)));
    }

    public void validateEnteredSeqChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String enteredSeq = new String(this.enterSeqTA.getText());
        if (!enteredSeq.equals("")) {
            SeqUtils.SeqType seqType = SeqUtils.checkSeqType(enteredSeq);
            if (seqType == SeqUtils.SeqType.UNKNOWN) {
                this.seqStatusL.setText("UNKNOWN");
                // allow filterToRna and filterToDna
                setButtonsUnknown();
            } else if (seqType == SeqUtils.SeqType.DNA) {
                this.seqStatusL.setText("DNA");
                // allow toRna and all other buttons
                setButtonsDna();
            } else if (seqType == SeqUtils.SeqType.RNA) {
                this.seqStatusL.setText("RNA");
                // allow toDna and all other buttons
                setButtonsRna();
            } else {
                this.seqStatusL.setText("BOTH");
                // allow other buttons
                setButtonsBoth();
            }
        } else {
            this.seqStatusL.setText("No sequence entered.");
            setAllButtons(false);
        }
    }

    private void setAllButtons(boolean bool) {
        // not allow: filterToDna, filterToRna, toRna, toDna
        for (Node node : this.optionsGB.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(!bool);
            }
        }
    }

    private void setButtonsBoth() {
        // not allow: filterToDna, filterToRna, toRna, toDna
        for (Node node : this.optionsGB.getChildren()) {
            if (node instanceof Button) {
                if (node.equals(this.filterToDnaB) || node.equals(this.filterToRnaB)
                        || node.equals(this.toRnaB) || node.equals(this.toDnaB)) {
                    node.setDisable(true);
                } else {
                    node.setDisable(false);
                }
            }
        }
    }

    private void setButtonsRna() {
        // not allow: filterToDna, filterToRna, toRna
        for (Node node : this.optionsGB.getChildren()) {
            if (node instanceof Button) {
                if (node.equals(this.filterToDnaB) || node.equals(this.filterToRnaB) || node.equals(this.toRnaB)) {
                    node.setDisable(true);
                } else {
                    node.setDisable(false);
                }
            }
        }
    }

    private void setButtonsDna() {
        // not allow: filterToDna, filterToRna, toDna
        for (Node node : this.optionsGB.getChildren()) {
            if (node instanceof Button) {
                if (node.equals(this.filterToDnaB) || node.equals(this.filterToRnaB) || node.equals(this.toDnaB)) {
                    node.setDisable(true);
                } else {
                    node.setDisable(false);
                }
            }
        }
    }

    private void setButtonsUnknown() {
        // only allow filterToRnaB and filterToDnaB
        for (Node node : this.optionsGB.getChildren()) {
            if (node instanceof Button) {
                if (node.equals(this.filterToDnaB) || node.equals(this.filterToRnaB) ||
                        node.equals(this.flipB) || node.equals(this.closeB) ||
                        node.equals(this.clearB)) {
                    node.setDisable(false);
                } else {
                    node.setDisable(true);
                }
            }
        }
    }

    public void performToDna() {
        String enteredSeq = this.enterSeqTA.getText();
        RnaSequence rnaSequence = RnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(ASequence.
                seqToString((List<ANucleotide>) (List<?>) rnaSequence.toDnaSeq(), lineWidth));
    }

    public void performFilterToDna() {
        String enteredSeq = new String(this.enterSeqTA.getText());
        DnaSequence dnaSequence = DnaSequence.parseStringToSequence(enteredSeq);
        int lineWidth = (int) this.lineWidthS.getValue();
        this.resultSeqTA.setText(dnaSequence.seqToString(lineWidth));
    }

    public void performClosing() {
        Platform.exit();
    }
}
