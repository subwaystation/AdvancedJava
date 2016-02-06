package _rna_3d_viewer.view;

import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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

    // the stack pane as root
    private StackPane rootBP;

    // the label at the bottom
    private Label label;

    private Rotate rx;

    private Rotate ry;

    private Translate cameraTranslate;

    // the border pane
    private BorderPane borderPane;

    // the openPDB menu item
    private MenuItem openPDB;

    // the open sec struct menu item
    private MenuItem openSecStruct;

    // the show hydrogen bond check menu item
    private CheckMenuItem showHydrogenBonds;

    public Rna3DViewerView() {
        this.rootBP = new StackPane();

        this.borderPane = new BorderPane();

        this.rnaMoleculesG = new Group();
        this.scene = new Scene(this.rootBP, 1280, 640, true);

        VBox vBox = new VBox();

        perspectiveCamera = new PerspectiveCamera(false);
        perspectiveCamera.setTranslateX(-scene.getWidth() / 2);
        perspectiveCamera.setTranslateY(-scene.getHeight() / 2);
        perspectiveCamera.setTranslateZ(50);
        perspectiveCamera.setFarClip(10000);
        perspectiveCamera.setNearClip(0.001);
        perspectiveCamera.setFieldOfView(45);
        SubScene subScene = new SubScene(this.rnaMoleculesG, 1280, 640, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.WHITE);
        subScene.setCamera(this.perspectiveCamera);
        subScene.heightProperty().bind(this.scene.heightProperty());
        subScene.widthProperty().bind(this.scene.widthProperty());

        MenuBar menuBar = new MenuBar();
        // menu file
        Menu menuFile = new Menu("File");
        openPDB = new MenuItem("Open PDB");
        menuFile.getItems().add(openPDB);
        menuBar.getMenus().add(menuFile);
        Menu menuSecStruct = new Menu("SecStruct");
        this.openSecStruct = new MenuItem("Open SecStruct");
        menuSecStruct.getItems().add(openSecStruct);
        menuBar.getMenus().add(menuSecStruct);
        showHydrogenBonds = new CheckMenuItem("Show Hydrogen Bonds");
        showHydrogenBonds.setSelected(false);
        Menu menuView = new Menu("View");
        menuView.getItems().add(showHydrogenBonds);
        menuBar.getMenus().add(menuView);


        borderPane.setTop(menuBar);
        this.label = new Label("No PDB file selected.");
        borderPane.setBottom(this.label);
        borderPane.setPickOnBounds(false);

        this.rootBP.getChildren().addAll(subScene, borderPane);

        this.ry = new Rotate(0, new Point3D(1,0,0));
        this.rx = new Rotate(0, new Point3D(0,1,0));
        Rotate rz = new Rotate(0, Rotate.Z_AXIS);

        this.cameraTranslate = new Translate(0, 0, 0);

        this.rnaMoleculesG.getTransforms().addAll(ry, rx, rz, this.cameraTranslate);
        ry.setAngle(10);
        rx.setAngle(10);
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

    public StackPane getRootBP() {
        return rootBP;
    }

    public Rotate getRx() {
        return rx;
    }

    public Rotate getRy() {
        return ry;
    }

    public Translate getCameraTranslate() {
        return cameraTranslate;
    }

    public BorderPane getBorderPane() {
        return this.borderPane;
    }

    public MenuItem getOpenSecStruct() {
        return this.openSecStruct;
    }

    public CheckMenuItem getShowHydrogenBonds() {
        return this.showHydrogenBonds;
    }
}
