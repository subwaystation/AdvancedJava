package ui.dna_manipulator;

import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.swing.FilePane;

/**
 * Created by heumos on 03.11.15.
 */
public class DnaManipulatorView {

    // the scene
    private Scene scene;

    // the grid pane
    private GridPane gridPane;

    // the text area in which the user can enter a DNA sequence
    private TextArea enterSeqTA;

    // the button which allows the user to flip the text between
    // enterSeqTA and resultSeqTA
    private Button flipB;

    // the text area in which the results of a user interaction
    // is displayed
    private TextArea resultSeqTA;

    // the grid pane in which all button options are placed
    private GridPane optionsGB;

    // filter button
    private Button filterB;

    // upper case button
    private Button upperCaseB;

    // lower case button
    private Button lowerCaseB;

    // to RNA button
    private Button toRnaB;

    // reverse seq button
    private Button reverseB;

    // complementary seq button
    private Button complementaryB;

    // reverse complementary seq button
    private Button reverseComplementaryB;

    // GC content button
    private Button gCContentB;

    // seq length button
    private Button lengthB;

    // clear button
    private Button clearB;

    // the slider
    private Slider lineWidthS;

    public DnaManipulatorView() {
        initGridPane();

        this.enterSeqTA = new TextArea();
        this.enterSeqTA.setFont(Font.font("Monospaced", 13));
        this.gridPane.add(this.enterSeqTA, 0, 0);
        this.gridPane.setHgrow(this.enterSeqTA, Priority.ALWAYS);
        this.flipB = new Button("flip");
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(this.flipB);
        flowPane.setAlignment(Pos.CENTER);
        this.gridPane.add(flowPane, 0, 1);
        this.resultSeqTA = new TextArea();
        this.resultSeqTA.setFont(Font.font("Monospaced", 13));
        this.gridPane.add(resultSeqTA, 0, 2);

        initOptionsGB();

        initLineWidthS();

        this.scene = new Scene(this.gridPane, 768, 640);
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
        this.gridPane.add(label, 0, 4);
        this.gridPane.add(this.lineWidthS, 0, 5);
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

        this.filterB = new Button("filter");
        this.filterB.setMaxWidth(Double.MAX_VALUE);
        this.upperCaseB = new Button("upper case");
        this.upperCaseB.setMaxWidth(Double.MAX_VALUE);
        this.lowerCaseB = new Button("lower case");
        this.lowerCaseB.setMaxWidth(Double.MAX_VALUE);

        this.toRnaB = new Button("to RNA");
        this.toRnaB.setMaxWidth(Double.MAX_VALUE);
        this.reverseB = new Button("reverse");
        this.reverseB.setMaxWidth(Double.MAX_VALUE);
        this.complementaryB = new Button("complementary");
        this.complementaryB.setMaxWidth(Double.MAX_VALUE);

        this.reverseComplementaryB = new Button("reverse-complementary");
        this.reverseComplementaryB.setMaxWidth(Double.MAX_VALUE);
        this.gCContentB = new Button("GC content");
        this.gCContentB.setMaxWidth(Double.MAX_VALUE);
        this.lengthB = new Button("length");
        this.lengthB.setMaxWidth(Double.MAX_VALUE);

        this.clearB = new Button("clear");
        this.clearB.setMaxWidth(Double.MAX_VALUE);
        this.optionsGB.add(filterB, 0, 0);
        this.optionsGB.add(upperCaseB, 1, 0);
        this.optionsGB.add(lowerCaseB, 2, 0);
        this.optionsGB.add(toRnaB, 0, 1);
        this.optionsGB.add(reverseB, 1, 1);
        this.optionsGB.add(complementaryB, 2, 1);
        this.optionsGB.add(reverseComplementaryB, 0, 2);
        this.optionsGB.add(gCContentB, 1, 2);
        this.optionsGB.add(lengthB, 2, 2);
        this.optionsGB.add(clearB, 0, 3);
        
        this.gridPane.add(this.optionsGB, 0, 3);
    }

    private void initGridPane() {
        this.gridPane = new GridPane();
        this.gridPane.setPadding(new Insets(0, 50, 0, 50));
        this.gridPane.setVgap(20);
    }

    public void show(Stage stage) {
        stage.setTitle("Dna Manipulator");
        stage.setScene(this.scene);
        stage.show();
    }

    public TextArea getEnterSeqTA() {
        return enterSeqTA;
    }

    public Button getFlipB() {
        return flipB;
    }

    public TextArea getResultSeqTA() {
        return resultSeqTA;
    }

    public Button getFilterB() {
        return filterB;
    }

    public Button getUpperCaseB() {
        return upperCaseB;
    }

    public Button getLowerCaseB() {
        return lowerCaseB;
    }

    public Button getReverseB() {
        return reverseB;
    }

    public Button getToRnaB() {
        return toRnaB;
    }

    public Button getComplementaryB() {
        return complementaryB;
    }

    public Button getReverseComplementaryB() {
        return reverseComplementaryB;
    }

    public Button getgCContentB() {
        return gCContentB;
    }

    public Button getLengthB() {
        return lengthB;
    }

    public Button getClearB() { return clearB; }

    public Slider getLineWidthS() {
        return lineWidthS;
    }

}
