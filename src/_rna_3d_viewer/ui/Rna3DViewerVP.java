package _rna_3d_viewer.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import _rna_3d_viewer.model.Rna3DViewerModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerVP {

    protected static class HandleSceneWidth implements ChangeListener<Number> {
        private Rna3DViewerView rna3DViewerView;
        public HandleSceneWidth(Rna3DViewerView rna3DViewerView) {
            this.rna3DViewerView = rna3DViewerView;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            this.rna3DViewerView.getPerspectiveCamera().
                    setTranslateX(-newValue.doubleValue() / 2);
        }
    }

    protected static class HandleSceneHeight implements ChangeListener<Number> {
        private Rna3DViewerView rna3DViewerView;
        public HandleSceneHeight(Rna3DViewerView rna3DViewerView) {
            this.rna3DViewerView = rna3DViewerView;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            this.rna3DViewerView.getPerspectiveCamera().
                    setTranslateY(-newValue.doubleValue() / 2);
        }
    }

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
                this.rna3DViewerView.getRnaMoleculesG().
                        setTranslateZ(this.rna3DViewerView.getRnaMoleculesG().
                                getTranslateZ() + mouseDeltaY);
                // rotate group around the y-axis
            } else {
                Rotate rx = new Rotate(mouseDeltaY/20, Rotate.X_AXIS);
                Rotate ry = new Rotate(mouseDeltaX/20, Rotate.Y_AXIS);
                this.rna3DViewerView.getRnaMoleculesG().getTransforms().addAll(rx,ry);
            }
            rna3DViewerModel.setMouseY(mouseEvent.getY());
            rna3DViewerModel.setMouseX(mouseEvent.getX());
        }
    }

    public static void handleFileOpener(Rna3DViewerView rna3DViewerView, Rna3DViewerModel rna3DViewerModel,
                                      Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .pdb file.");
        FileChooser.ExtensionFilter pdbExtensionFilter = new FileChooser.ExtensionFilter("PDB Files", "*.pdb");
        fileChooser.getExtensionFilters().addAll(pdbExtensionFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            rna3DViewerView.getLabel().setText(file.toString());
            try {
                rna3DViewerModel.parsePDB(file.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            rna3DViewerModel.build3DStructures();
            rna3DViewerView.getRnaMoleculesG().getChildren().clear();

            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getMeshViewList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getSugarConnectionList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getPhosphorusList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getPhosphorusConnections());
        }
    }
}
