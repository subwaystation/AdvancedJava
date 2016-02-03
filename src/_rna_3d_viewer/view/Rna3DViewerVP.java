package _rna_3d_viewer.view;

import _rna_3d_viewer.model.structure.Nucleotide3DStructure;
import _rna_3d_viewer.model.SelectionModel;
import _rna_3d_viewer.rna_drawer.RnaDrawerModel;
import _rna_3d_viewer.rna_drawer.RnaDrawerVC;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import _rna_3d_viewer.model.Rna3DViewerModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerVP {

    private static List<Integer> lastIndices = new ArrayList<>();

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

            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getMeshStructures());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getNucleotide3DStructures().getMeshList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getSugarConnectionList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getPhosphorusList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getPhosphorusConnections());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getHydrogenBonds());

            initSelectionModel(rna3DViewerModel);
        }
    }

    protected static void handleSecStruct(Rna3DViewerView rna3DViewerView, Rna3DViewerModel rna3DViewerModel,
                                          Stage primaryStage, Stage secondaryStage) {
        RnaDrawerModel rnaDrawerModel = new RnaDrawerModel(rna3DViewerModel.getSecondaryStructure());

        RnaDrawerVC rnaDrawerVC = new RnaDrawerVC(rnaDrawerModel, secondaryStage);
        rnaDrawerVC.show();
        /*System.out.println("Hello World!");

        Stage secondStage = new Stage();
        Button button = new Button("Click M2");
        button.setOnAction((e) -> System.out.println("Yeah"));
        secondStage.setScene(new Scene(new HBox(4, button)));
        secondStage.show();*/
    }

    private static void initSelectionModel(Rna3DViewerModel rna3DViewerModel) {

        Nucleotide3DStructure[] shapes = rna3DViewerModel.getNucleotide3DStructures().getNucleotide3DStructuresAsArray();
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = rna3DViewerModel.getNucleotide3DStructures().get(i);
        }
        SelectionModel<Nucleotide3DStructure> mySelectionModel=new SelectionModel<>(shapes);

        // setup selection capture in view:
        for (int i = 0; i < shapes.length; i++) {
            final int index=i;
            Nucleotide3DStructure shape = shapes[i];

            BooleanBinding binding = new BooleanBinding() {
                {
                    bind(mySelectionModel.getSelectedItems());
                }

                @Override
                protected boolean computeValue() {
                    return mySelectionModel.getSelectedIndices().contains(index);
                }
            };

            shape.isSelectedProperty().bind(binding);

            shape.getStructure().setOnMouseClicked((e) -> {
                if (!e.isShiftDown())
                    mySelectionModel.clearSelection();
                    update3DColoring(rna3DViewerModel);
                    lastIndices.clear();
                if (mySelectionModel.isSelected(index)) {
                    mySelectionModel.clearSelection(index);
                    lastIndices.remove(new Integer(index));
                    update3DColoring(rna3DViewerModel);
                } else {
                    mySelectionModel.select(index);
                    if (!lastIndices.isEmpty()) {
                        lastIndices.remove(new Integer(index));
                    }
                    lastIndices.add(index);
                    selectAndColorStructure(shape);
                }
            });
        }

    }

     public static void selectAndColorStructure(Nucleotide3DStructure shape) {
        shape.getStructure().setMaterial(new PhongMaterial(Color.ORANGE));
    }

    private static void update3DColoring(Rna3DViewerModel rna3DViewerModel) {
        for (int lastIndex : lastIndices) {
            rna3DViewerModel.getNucleotide3DStructures().get(lastIndex).resetColor();
        }
    }
}
