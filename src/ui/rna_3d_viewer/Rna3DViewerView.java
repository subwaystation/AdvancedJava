package ui.rna_3d_viewer;

import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerView {

    // the scene
    private Scene scene;

    // the group to which all rna 3d structure will be added
    private Group rnaMoleculesG;

    // the perspective camera
    private PerspectiveCamera perspectiveCamera;

    // the border pane as root
    private BorderPane rootBP;

    // the label at the bottom
    private Label label;

    // the openPDB menu item
    MenuItem openPDB;

    public Rna3DViewerView() {
        this.rootBP = new BorderPane();

        this.rnaMoleculesG = new Group();
        this.scene = new Scene(this.rootBP, 1280, 640, true);

        VBox vBox = new VBox();

        perspectiveCamera = new PerspectiveCamera(false);
        perspectiveCamera.setTranslateX(-scene.getWidth() / 2);
        perspectiveCamera.setTranslateY(-scene.getHeight() / 2);
        perspectiveCamera.setTranslateZ(20);
        perspectiveCamera.setFarClip(10000);
        perspectiveCamera.setNearClip(0.001);
        perspectiveCamera.setFieldOfView(40);
        SubScene subScene = new SubScene(this.rnaMoleculesG, 300, 300, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.WHITE);
        subScene.setCamera(this.perspectiveCamera);
        subScene.heightProperty().bind(vBox.heightProperty());
        subScene.widthProperty().bind(vBox.widthProperty());
        vBox.getChildren().add(subScene);

        MenuBar menuBar = new MenuBar();
        // menu file
        Menu menuFile = new Menu("File");
        openPDB = new MenuItem("Open PDB");
        menuFile.getItems().add(openPDB);
        menuBar.getMenus().add(menuFile);

        this.rootBP.setTop(menuBar);
        this.label = new Label("No PDB file selected.");
        this.rootBP.setCenter(vBox);
        this.rootBP.setBottom(this.label);
    }


    public void show(Stage stage) {
        stage.setTitle("Rna3DViewer");
        stage.setScene(this.scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public Group getRnaMoleculesG() {
        return rnaMoleculesG;
    }

    public PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public Label getLabel() {
        return label;
    }

    public MenuItem getOpenPDB() {
        return openPDB;
    }
}
