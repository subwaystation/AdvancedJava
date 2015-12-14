package ui.rna_3d_viewer;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerView {

    // the scene
    private Scene scene;

    // the group to which all rna 3d structure will be added
    private Group rnaMoleculesG;

    private PerspectiveCamera perspectiveCamera;

    public Rna3DViewerView() {

        this.rnaMoleculesG = new Group();
        this.scene = new Scene(this.rnaMoleculesG, 1280, 640, true);
        perspectiveCamera = new PerspectiveCamera(false);
        perspectiveCamera.setTranslateX(-scene.getWidth()/2);
        perspectiveCamera.setTranslateY(-scene.getHeight()/2);
        perspectiveCamera.setTranslateZ(50);
        perspectiveCamera.setFarClip(10000);
        perspectiveCamera.setNearClip(0.001);
        perspectiveCamera.setFieldOfView(45);

        scene.setCamera(perspectiveCamera);
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
}
