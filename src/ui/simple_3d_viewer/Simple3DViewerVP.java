package ui.simple_3d_viewer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import model.Simple3DViewerModel;

/**
 * Created by heumos on 03.12.15.
 */
public class Simple3DViewerVP {

    protected static class HandleMousePressed implements EventHandler<MouseEvent> {
        private Simple3DViewerModel simple3DViewerModel;
        public HandleMousePressed(Simple3DViewerModel simple3DViewerModel) {
            this.simple3DViewerModel = simple3DViewerModel;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            this.simple3DViewerModel.setMouseX(mouseEvent.getX());
            this.simple3DViewerModel.setMouseY(mouseEvent.getY());
        }
    }

    protected static class HandleMouseDragged implements EventHandler<MouseEvent> {
        private Simple3DViewerModel simple3DViewerModel;
        private Simple3DViewerView simple3DViewerView;
        public HandleMouseDragged(Simple3DViewerView simple3DViewerView, Simple3DViewerModel simple3DViewerModel) {
            this.simple3DViewerModel = simple3DViewerModel;
            this.simple3DViewerView = simple3DViewerView;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            double mouseDeltaY = mouseEvent.getY() - this.simple3DViewerModel.getMouseY();
            double mouseDeltaX = mouseEvent.getX() - this.simple3DViewerModel.getMouseX();
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.isShiftDown()) {
                // move camera towards or away from objects
                this.simple3DViewerView.getPerspectiveCamera().
                        setTranslateZ(this.simple3DViewerView.getPerspectiveCamera().
                                getTranslateZ() + mouseDeltaY);
                // rotate group around the y-axis
            } else {
                Rotate rz = new Rotate(mouseDeltaY/20, Rotate.Z_AXIS);
                Rotate ry = new Rotate(mouseDeltaX/20, Rotate.Y_AXIS);
                this.simple3DViewerView.getRootNodeG().getTransforms().addAll(rz,ry);
            }

            simple3DViewerModel.setMouseY(mouseEvent.getY());
            simple3DViewerModel.setMouseX(mouseEvent.getX());
        }
    }

}
