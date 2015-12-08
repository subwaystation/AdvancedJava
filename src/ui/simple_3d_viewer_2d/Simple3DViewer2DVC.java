package ui.simple_3d_viewer_2d;

import javafx.stage.Stage;
import model.Simple3DViewer2DModel;

/**
 * Created by heumos on 07.12.15.
 */
public class Simple3DViewer2DVC {

    // model
    private Simple3DViewer2DModel simple3DViewer2DModel;

    // view
    private Simple3DViewer2DView simple3DViewer2DView;

    // stage
    private Stage primaryStage;

    public Simple3DViewer2DVC(Simple3DViewer2DModel simple3DViewerModel, Stage primaryStage) {
        this.simple3DViewer2DModel = simple3DViewerModel;
        this.primaryStage = primaryStage;
        this.simple3DViewer2DView = new Simple3DViewer2DView();

        // register event handler
        this.simple3DViewer2DView.getScene().setOnMousePressed(new Simple3DViewer2DVP.
                HandleMousePressed(this.simple3DViewer2DModel));
        this.simple3DViewer2DView.getScene().setOnMouseDragged(new Simple3DViewer2DVP.
                HandleMouseDragged(this.simple3DViewer2DView, this.simple3DViewer2DModel));
    }

    public void show() {
        this.simple3DViewer2DView.show(this.primaryStage);
    }

}
