package ui.simple_3d_viewer_2d;

import model.Simple3DViewer2DModel;
import model.Simple3DViewerModel;
import ui.StageManager;
import ui.simple_3d_viewer.Simple3DViewerView;

/**
 * Created by heumos on 07.12.15.
 */
public class Simple3DViewer2DVC {

    // model
    private Simple3DViewer2DModel simple3DViewer2DModel;

    // view
    private Simple3DViewer2DView simple3DViewer2DView;

    public Simple3DViewer2DVC(Simple3DViewer2DModel simple3DViewerModel) {
        this.simple3DViewer2DModel = simple3DViewerModel;

        this.simple3DViewer2DView = new Simple3DViewer2DView();

        // register event handler
        this.simple3DViewer2DView.getScene().setOnMousePressed(new Simple3DViewer2DVP.
                HandleMousePressed(this.simple3DViewer2DModel));
        this.simple3DViewer2DView.getScene().setOnMouseDragged(new Simple3DViewer2DVP.
                HandleMouseDragged(this.simple3DViewer2DView, this.simple3DViewer2DModel));
    }

    public void show() {
        this.simple3DViewer2DView.show(StageManager.getInstance().getPrimaryStage());
    }

}
