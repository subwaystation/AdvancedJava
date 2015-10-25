package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by heumos on 25.10.15.
 *
 * A java class representing the view of MVC pattern.
 * Here the alignment view is created.
 * Its root parent is a border pane on which
 * (1) top: a label
 * (2) mid: alignment
 * (3) right: check boxes
 * (4) bottom: buttons
 * are shown.
 *
 */
public class AlignmentView {

    // the scene
    private Scene scene;

    // the border pane
    private BorderPane borderPane;

    // the label which will be placed on the top of the border pane
    private Label label;

    // the text field in which the alignment will be displayed
    private Text alignmentT;

    // the vBox which will be placed on the right of the border pane
    private VBox checkBoxesVB;
    private CheckBox showIdentifersCB;
    private CheckBox showSequencesCB;
    private CheckBox showNumberingCB;

    // the hBox which will be placed on the bottom of the border pane
    private HBox selectButtonsHB;
    private Button selectAllB;
    private Button clearSelectionB;
    private Button applyB;

    public AlignmentView() {

        // layout is border pane
        this.borderPane = new BorderPane();

        // alignment label
        this.label = new Label("Your alignment:");
        this.label.setPadding(new Insets(20, 20, 20, 20));
        this.borderPane.setTop(label);

        // actual alignment displayed in text field
        this.alignmentT = new Text();
        this.alignmentT.setFont(Font.font("Monospaced", 13));
        this.borderPane.setCenter(this.alignmentT);

        // check boxes
        this.checkBoxesVB = new VBox(20);
        this.checkBoxesVB.setPadding(new Insets(0, 80, 0, 0));
        this.showIdentifersCB = new CheckBox("Show Identifiers");
        this.showSequencesCB = new CheckBox("Show Sequences");
        this.showNumberingCB = new CheckBox("Show Numbering");
        this.checkBoxesVB.getChildren().addAll(this.showIdentifersCB, this.showSequencesCB, this.showNumberingCB);
        this.borderPane.setRight(this.checkBoxesVB);

        // buttons
        this.selectButtonsHB = new HBox(20);
        this.selectButtonsHB.setPadding(new Insets(20, 20, 20, 20));
        this.selectAllB = new Button("Select All");
        this.clearSelectionB = new Button("Clear Selection");
        this.applyB = new Button("Apply");
        this.selectButtonsHB.getChildren().addAll(this.selectAllB, this.clearSelectionB, this.applyB);
        this.borderPane.setBottom(this.selectButtonsHB);

        this.scene = new Scene(this.borderPane, 1366, 768);
    }

    public void show(Stage stage) {
        stage.setTitle("Alignment Viewer");
        stage.setScene(this.scene);
        stage.show();
    }

    public Text getAlignmentT() {
        return alignmentT;
    }

    public CheckBox getShowIdentifersCB() {
        return showIdentifersCB;
    }

    public CheckBox getShowSequencesCB() {
        return showSequencesCB;
    }

    public CheckBox getShowNumberingCB() {
        return showNumberingCB;
    }

    public Button getSelectAllB() {
        return selectAllB;
    }

    public Button getClearSelectionB() {
        return clearSelectionB;
    }

    public Button getApplyB() {
        return applyB;
    }
}
