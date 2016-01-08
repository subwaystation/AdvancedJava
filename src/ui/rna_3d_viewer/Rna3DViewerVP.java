package ui.rna_3d_viewer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import model.rna_3d_viewer.Rna3DViewerModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerVP {

    protected static class HandleMousePressed implements EventHandler<MouseEvent> {
        private Rna3DViewerModel rna3DViewerModel;
        public HandleMousePressed(Rna3DViewerModel rna3DViewerModel) {
            this.rna3DViewerModel = rna3DViewerModel;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            this.rna3DViewerModel.setMouseX(mouseEvent.getX());
            this.rna3DViewerModel.setMouseY(mouseEvent.getY());
        }
    }

    protected static class HandleMouseDragged implements EventHandler<MouseEvent> {
        private Rna3DViewerModel rna3DViewerModel;
        private Rna3DViewerView rna3DViewerView;
        public HandleMouseDragged(Rna3DViewerView rna3DViewerView, Rna3DViewerModel rna3DViewerModel) {
            this.rna3DViewerModel = rna3DViewerModel;
            this.rna3DViewerView = rna3DViewerView;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            double mouseDeltaY = mouseEvent.getY() - this.rna3DViewerModel.getMouseY();
            double mouseDeltaX = mouseEvent.getX() - this.rna3DViewerModel.getMouseX();
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.isShiftDown()) {
                // move camera towards or away from objects
                this.rna3DViewerView.getPerspectiveCamera().
                        setTranslateZ(this.rna3DViewerView.getPerspectiveCamera().
                                getTranslateZ() + mouseDeltaY);
                // rotate group around the y-axis
            } else {
                Rotate rz = new Rotate(mouseDeltaY/20, Rotate.Z_AXIS);
                Rotate ry = new Rotate(mouseDeltaX/20, Rotate.Y_AXIS);
                this.rna3DViewerView.getPerspectiveCamera().getTransforms().addAll(rz,ry);
            }
            rna3DViewerModel.setMouseY(mouseEvent.getY());
            rna3DViewerModel.setMouseX(mouseEvent.getX());
        }
    }

    public static void handleFileOpener(Rna3DViewerView rna3DViewerView, Rna3DViewerModel rna3DViewerModel,
                                      Stage primaryStage) {
        String dir = System.getProperty("user.dir") + File.separator;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .pdb file.");
        FileChooser.ExtensionFilter clsrExtensionFilter = new FileChooser.ExtensionFilter("PDB Files", "*.pdb");
        fileChooser.getExtensionFilters().addAll(clsrExtensionFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            rna3DViewerView.getLabel().setText(file.toString());
            try {
                rna3DViewerModel.parsePDB(file.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            rna3DViewerModel.createMolecules();

            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getMeshViewList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getConnectionList());
        }
    }
}
