package ui.rna_drawer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

    // the input sequence text area
    private TextArea enterSeqTA;
    // the input/output secondary structure
    private TextArea secStructTA;

    // the fold button
    private Button foldB;
    // the draw button
    private Button drawB;

    // the group containing the drawing
    private Pane drawingP;

    // the animation check box
    private CheckBox animationCB;

    public Pane getDrawingP() {
        return drawingP;
    }

    public RnaDrawerView() {

        this.rootNode = new VBox();
        this.rootNode.setPadding(new Insets(10));
        this.rootNode.setSpacing(5);

        this.enterSeqTA = new TextArea();
        this.enterSeqTA.setFont(Font.font("Courier"));
        this.enterSeqTA.setPrefHeight(5);
        this.enterSeqTA.setPrefRowCount(1);
        this.secStructTA = new TextArea();
        this.secStructTA.setFont(Font.font("Courier"));
        this.secStructTA.setPrefHeight(5);
        this.rootNode.getChildren().addAll(this.enterSeqTA, this.secStructTA);

        initButtonHBox();

        this.drawingP = new Pane();
        this.rootNode.getChildren().add(this.drawingP);

        this.scene = new Scene(this.rootNode, 1024, 768);
    }

    public CheckBox getAnimationCB() {
        return animationCB;
    }

    private void initButtonHBox() {
        this.buttonHBox = new HBox();
        this.foldB = new Button("Fold RNA.");
        this.foldB.setDisable(true);
        this.drawB = new Button("Draw RNA.");
        this.drawB.setDisable(true);
        this.animationCB = new CheckBox("animation");
        this.buttonHBox.getChildren().addAll(this.foldB, this.drawB, this.animationCB);
        this.rootNode.getChildren().add(this.buttonHBox);

    }

    public void show(Stage stage) {
        stage.setTitle("Rna Drawer");
        stage.setScene(this.scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public VBox getRootNode() {
        return rootNode;
    }

    public TextArea getEnterSeqTA() {
        return enterSeqTA;
    }

    public TextArea getSecStructTA() {
        return secStructTA;
    }

    public Button getFoldB() {
        return foldB;
    }

    public Button getDrawB() {
        return drawB;
    }
}
