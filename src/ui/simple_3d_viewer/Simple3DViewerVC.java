package ui.simple_3d_viewer;

import model.Simple3DViewerModel;
import ui.StageManager;

/**
 * Created by heumos on 03.12.15.
 */
public class Simple3DViewerVC {

    // model
    private Simple3DViewerModel simple3DViewerModel;

    // view
    private Simple3DViewerView simple3DViewerView;

    public Simple3DViewerVC(Simple3DViewerModel simple3DViewerModel) {
        this.simple3DViewerModel = simple3DViewerModel;

        this.simple3DViewerView = new Simple3DViewerView();

        // register event handler
        this.simple3DViewerView.getScene().setOnMousePressed(new Simple3DViewerVP.
                HandleMousePressed(this.simple3DViewerModel));
        this.simple3DViewerView.getScene().setOnMouseDragged(new Simple3DViewerVP.
                HandleMouseDragged(this.simple3DViewerView, this.simple3DViewerModel));
    }

    public void show() {
        this.simple3DViewerView.show(StageManager.getInstance().getPrimaryStage());
    }

}
