package ui.simple_3d_viewer_2d;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by heumos on 07.12.15.
 */
public class Simple3DViewer2DView {

    // the stack pane
    private StackPane rootNodeP;

    // the scene
    private Scene scene;

    // the subscene
    private SubScene subScene;

    // the root node
    private Group threeDG;

    // the perspective camera
    private PerspectiveCamera perspectiveCamera;

    // the zylinder
    private Cylinder cylinder;

    // the first box
    private Box box1;

    // the second box
    private Box box2;

    // 2D Pane
    private Pane topPane;

    public Simple3DViewer2DView() {
        this.perspectiveCamera = new PerspectiveCamera(true);
        this.perspectiveCamera.setNearClip(0.1);
        this.perspectiveCamera.setFarClip(10000.0);
        this.perspectiveCamera.setTranslateX(0.0);
        this.perspectiveCamera.setTranslateY(0.0);
        this.perspectiveCamera.setTranslateZ(-500.0);

        this.threeDG = new Group();

        // build cylinder
        this.cylinder = new Cylinder(12, 100);
        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);
        this.cylinder.setMaterial(greyMaterial);

        // box material
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        // build box 1
        this.box1 = new Box(40,50,40);
        this.box1.setMaterial(redMaterial);
        this.box1.setTranslateY(55);
        // build box 2
        this.box2 = new Box(40, 50, 40);
        this.box2.setMaterial(redMaterial);
        this.box2.setTranslateY(-55);
        // add boxes to group
        this.threeDG.getChildren().addAll(this.cylinder, this.box1, this.box2);
        // build box tooltips
        buildTooltips();

        // add group to subscene
        this.subScene = new SubScene(this.threeDG, 1280, 640, true, SceneAntialiasing.DISABLED);
        this.subScene.setFill(Color.MIDNIGHTBLUE);
        this.subScene.setCamera(this.perspectiveCamera);

        this.rootNodeP = new StackPane();
        this.topPane = new Pane();
        this.rootNodeP.getChildren().addAll(subScene, topPane);
        this.scene = new Scene(this.rootNodeP, 1280, 640, true);
        this.topPane.setPickOnBounds(false);
    }

    public void initialize2DBoxes() {
        this.rootNodeP.setAlignment(topPane, Pos.CENTER);
        this.rootNodeP.setAlignment(subScene, Pos.CENTER);
        this.topPane.getChildren().add(getBoundingBox2D(this.box1));
        this.topPane.getChildren().add(getBoundingBox2D(this.box2));
        this.topPane.getChildren().add(getBoundingBox2D(this.cylinder));
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
        stage.setTitle("Simple3DViewer2D");
        stage.setScene(this.scene);
        stage.show();
        initialize2DBoxes();
    }

    public Scene getScene() {
        return scene;
    }

    public Group getThreeDG() {
        return threeDG;
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

    public static javafx.scene.shape.Rectangle getBoundingBox2D(Node shape) {
        final Window window = shape.getScene().getWindow();
        System.out.println(window);
        final Bounds bounds = shape.getBoundsInLocal();
        final Bounds screenBounds = shape.localToScreen(bounds);
        javafx.scene.shape.Rectangle rectangle;
        rectangle = new javafx.scene.shape.Rectangle
                ((int) Math.round(screenBounds.getMinX() - window.getX()),
                        (int) Math.round(screenBounds.getMinY() - window.getY()),
                        (int) Math.round(screenBounds.getWidth()),
                        (int) Math.round(screenBounds.getHeight()));
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.LIGHTBLUE);
        rectangle.setMouseTransparent(true);
        return rectangle;
    }
}
