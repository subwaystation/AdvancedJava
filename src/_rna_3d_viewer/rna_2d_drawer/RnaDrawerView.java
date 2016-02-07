package _rna_3d_viewer.rna_2d_drawer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerView {

    // the scene
    private Scene scene;

    // the root node
    private VBox rootNode;

    // the button HBox
    private HBox buttonHBox;

    // the primary sequence HBox
    private HBox primaryseqHB;

    private Label sequenceL;

    // the draw button
    private Button drawB;

    // the group containing the _rna_3d_viewer.drawing
    private Pane drawingP;

    // the check box determining if the psudoknots shall be drawn or not
    private CheckBox drawPseudoknotsCB;

    public Pane getDrawingP() {
        return drawingP;
    }

    public RnaDrawerView() {

        this.rootNode = new VBox();
        this.rootNode.setPadding(new Insets(10));
        this.rootNode.setSpacing(5);

        this.sequenceL = new Label();
        this.rootNode.getChildren().addAll(this.sequenceL);

        initButtonHBox();
        this.drawPseudoknotsCB = new CheckBox("Draw Pseudoknots.");
        this.drawPseudoknotsCB.setSelected(false);
        this.buttonHBox.getChildren().add(this.drawPseudoknotsCB);

        this.drawingP = new Pane();
        this.rootNode.getChildren().add(this.drawingP);

        this.scene = new Scene(this.rootNode, 1024, 768);
    }

    private void initButtonHBox() {
        this.primaryseqHB = new HBox();
        this.buttonHBox = new HBox();
        this.drawB = new Button("Draw RNA.");
        this.drawB.setDisable(false);
        this.buttonHBox.getChildren().addAll(this.drawB);
        this.rootNode.getChildren().addAll(this.primaryseqHB, this.buttonHBox);

    }

    public void show(Stage stage) {
        stage.setTitle("Rna Secondary Structure");
        stage.setScene(this.scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public Button getDrawB() {
        return drawB;
    }

    public Label getSequenceL() {
        return sequenceL;
    }

    public CheckBox getDrawPseudoknotsCB() {return this.drawPseudoknotsCB;}

    public HBox getPrimaryseqHB() {return this.primaryseqHB;}
}
