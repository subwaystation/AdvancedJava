package ui.simple_3d_viewer;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.stage.Stage;

/**
 * Created by heumos on 03.12.15.
 */
public class Simple3DViewerView {

    // the scene
    private Scene scene;

    // the root node
    private Group rootNodeG;

    // the perspective camera
    private PerspectiveCamera perspectiveCamera;

    // the zylinder
    private Cylinder cylinder;

    // the first box
    private Box box1;

    // the second box
    private Box box2;

    public Simple3DViewerView() {
        this.perspectiveCamera = new PerspectiveCamera(true);
        this.perspectiveCamera.setNearClip(0.1);
        this.perspectiveCamera.setFarClip(10000.0);
        this.perspectiveCamera.setTranslateX(0.0);
        this.perspectiveCamera.setTranslateY(0.0);
        this.perspectiveCamera.setTranslateZ(-500.0);

        this.rootNodeG = new Group();

        this.cylinder = new Cylinder(12, 100);
        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);
        this.cylinder.setMaterial(greyMaterial);

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        this.box1 = new Box(40,50,40);
        this.box1.setMaterial(redMaterial);
        this.box1.setTranslateY(55);
        this.box2 = new Box(40, 50, 40);
        this.box2.setMaterial(redMaterial);
        this.box2.setTranslateY(-55);
        this.rootNodeG.getChildren().addAll(this.cylinder, this.box1, this.box2);
        buildTooltips();

        this.scene = new Scene(this.rootNodeG, 1280, 640, true);
        this.scene.setFill(Color.MIDNIGHTBLUE);
        this.scene.setCamera(this.perspectiveCamera);
    }

    private void buildTooltips() {
        String tooltipS = "Box 1";
        Tooltip tooltip = new Tooltip(tooltipS);
        Tooltip.install(this.box1, tooltip);
        tooltipS = "Box 2";
        tooltip = new Tooltip(tooltipS);
        Tooltip.install(this.box2, tooltip);
        tooltipS = "Cylinder";
        tooltip = new Tooltip(tooltipS);
        Tooltip.install(this.cylinder, tooltip);
    }

    public void show(Stage stage) {
        stage.setTitle("Simple3DViewer");
        stage.setScene(this.scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public Group getRootNodeG() {
        return rootNodeG;
    }

    public PerspectiveCamera getPerspectiveCamera() {
        return perspectiveCamera;
    }

    public Cylinder getCylinder() {
        return cylinder;
    }

    public Box getBox1() {
        return box1;
    }

    public Box getBox2() {
        return box2;
    }
}
