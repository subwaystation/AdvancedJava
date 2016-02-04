package _rna_3d_viewer.view;

import _rna_3d_viewer.model.structure.*;
import _rna_3d_viewer.model.SelectionModel;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerModel;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerVC;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerVP;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
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

    private static int lastResidueIndex = -1;

    private static boolean is3DStructureReadIn = false;

    private static SelectionModel<Object> mySelectionModel;

    private static List<ANucleotideStructure> local3DNucleotides = null;

    private static SecStruct2DRepresentations localSecStructRepresentations = null;

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

            local3DNucleotides = rna3DViewerModel.getNucleotide3DStructures().getNucleotide3DStructures();

            initSelectionModel(rna3DViewerModel);
            is3DStructureReadIn = true;
        }
    }

    protected static void handleSecStruct(Rna3DViewerView rna3DViewerView, Rna3DViewerModel rna3DViewerModel,
                                          Stage primaryStage, Stage secondaryStage) {
        if (is3DStructureReadIn) {
            RnaDrawerModel rnaDrawerModel = new RnaDrawerModel(rna3DViewerModel.getSecondaryStructure(), rna3DViewerModel.getResidues());
            RnaDrawerVC rnaDrawerVC = new RnaDrawerVC(rnaDrawerModel, secondaryStage);
            rnaDrawerVC.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("PDB Information");
            alert.setHeaderText("No PDB file read in yet.");
            alert.setContentText("Please read in PDB file.");
            alert.showAndWait();
        }
    }

    public static void extendSelectionModel(RnaDrawerModel rnaDrawerModel) {
        localSecStructRepresentations = rnaDrawerModel.getSecStruct2DRepresentations();
        ANucleotideStructure[] shapes = rnaDrawerModel.getSecStruct2DRepresentations().get2DStructureRepresentations();

/*      ANucleotideStructure[] shapes3D = (ANucleotideStructure[]) mySelectionModel.getItems();
        int shapesLen = shapes.length;
        int shapes3DLen = shapes3D.length;
        ANucleotideStructure[] allShapes = new ANucleotideStructure[shapes3DLen + shapesLen];
        System.arraycopy(shapes, 0, allShapes, 0, shapesLen);
        System.arraycopy(shapes3D, 0, allShapes, shapesLen, shapes3DLen);*/

        SelectionModel selectionModel = new SelectionModel(shapes);
        // setup selection capture in view:
        for (int i = 0; i < shapes.length; i++) {
            final int index=i;
            ANucleotideStructure shape = shapes[i];

            BooleanBinding binding = new BooleanBinding() {
                {
                    bind(selectionModel.getSelectedItems());
                }

                @Override
                protected boolean computeValue() {
                    return selectionModel.getSelectedIndices().contains(index);
                }
            };

            shape.isSelectedProperty().bind(binding);

            shape.getStructure().setOnMouseClicked((e) -> {
                if (!e.isShiftDown())
                    selectionModel.clearSelection();
                    // TODO
                if (selectionModel.isSelected(index)) {
                    selectionModel.clearSelection(index);
                    // TODO
                } else {
                    selectionModel.select(index);
                    update2DColoringSecondary(index);
                }
            });
        }

    }

    private static void update2DColoringSecondary(int index) {
        // have to look in lines list
        if (index > localSecStructRepresentations.secStruct2dCirclesSize()) {

        } else {
            // have to look in circles list

        }

    }

    private static void initSelectionModel(Rna3DViewerModel rna3DViewerModel) {
        ANucleotideStructure[] shapes = rna3DViewerModel.getNucleotide3DStructures().getNucleotide3DStructuresAsArray();

        ANucleotideStructure[] shapes3D = (ANucleotideStructure[]) mySelectionModel.getItems();
        int shapesLen = shapes.length;
        int shapes3DLen = shapes3D.length;
        ANucleotideStructure[] allShapes = new ANucleotideStructure[shapes3DLen + shapesLen];
        System.arraycopy(shapes, 0, allShapes, 0, shapesLen);
        System.arraycopy(shapes3D, 0, allShapes, shapesLen, shapes3DLen);
        mySelectionModel = new SelectionModel<>(allShapes);

        // setup selection capture in view:
        for (int i = 0; i < shapes.length; i++) {
            final int index=i;
            ANucleotideStructure shape = shapes[i];

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
                    update3DColoringMain(rna3DViewerModel);
                    update2DColoringMain();
                    lastIndices.clear();
                if (mySelectionModel.isSelected(index)) {
                    mySelectionModel.clearSelection(index);
                    lastIndices.remove(new Integer(index));
                    update3DColoringMain(rna3DViewerModel);
                    update2DColoringMain();
                } else {
                    mySelectionModel.select(index);
                    if (!lastIndices.isEmpty()) {
                        lastIndices.remove(new Integer(index));
                    }
                    lastIndices.add(index);
                    Nucleotide3DStructure nucleotide3DStructure = (Nucleotide3DStructure) shape;
                    color3DStructure(nucleotide3DStructure);
                    color2DStructures(nucleotide3DStructure);
                }
            });
        }

    }

    private static void color2DStructures(Nucleotide3DStructure nucleotide3DStructure) {
        if (RnaDrawerVP.wasDrawn) {
            int residueIndex = nucleotide3DStructure.getResidueNumber();
            SecStruct2DCircle secStruct2DCircle = localSecStructRepresentations.getCircle2DStruct(residueIndex);
            secStruct2DCircle.getCircle().setFill(Color.ORANGE);
            lastResidueIndex = residueIndex;
        }

    }

    public static void color3DStructure(Nucleotide3DStructure shape) {
         shape.getStructure().setMaterial(new PhongMaterial(Color.ORANGE));
    }

    private static void update2DColoringMain() {
            if (lastResidueIndex != -1) {
                SecStruct2DCircle secStruct2DCircle = localSecStructRepresentations.getCircle2DStruct(lastResidueIndex);
                secStruct2DCircle.resetColor();
        }
    }

    private static void update3DColoringMain(Rna3DViewerModel rna3DViewerModel) {
        for (int lastIndex : lastIndices) {
            Nucleotide3DStructure nucleotide3DStructure = (Nucleotide3DStructure) rna3DViewerModel.getNucleotide3DStructures().get(lastIndex);
            nucleotide3DStructure.resetColor();
        }
    }
}
