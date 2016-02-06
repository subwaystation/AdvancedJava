package _rna_3d_viewer.view;

import _rna_3d_viewer.model.structure.*;
import _rna_3d_viewer.model.SelectionModel;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerModel;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerVC;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerVP;
import _rna_3d_viewer.rna_2d_drawer.RnaDrawerView;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
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

    private static Nucleotide3DStructures local3DNucleotides = null;

    private static SecStruct2DRepresentations localSecStructRepresentations = null;

    private static RnaDrawerModel localRnaDrawerModel;

    private static Rna3DViewerModel localRna3DViewerModel;

    private static RnaDrawerView localRnaDrawerView;

    public static void handleDrawBEvent() {
        localRnaDrawerModel = new RnaDrawerModel(localRna3DViewerModel.getSecondaryStructure(), localRna3DViewerModel.getResidues(),
                localRna3DViewerModel.getPseudoKnotsFreeList());
        RnaDrawerVP.setFirstDraw(false);
        RnaDrawerVP.handleDrawBEvent(localRnaDrawerView, localRnaDrawerModel);
    }

    public static void setLocalRnaDrawerView(RnaDrawerView rnaDrawerView) {
        localRnaDrawerView = rnaDrawerView;
    }

    protected static class HandleShowHydrogenBonds implements ChangeListener<Boolean> {
        private Rna3DViewerModel rna3DViewerModel;
        public HandleShowHydrogenBonds(Rna3DViewerModel rna3DViewerModel) {
            this.rna3DViewerModel = rna3DViewerModel;
        }
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                this.rna3DViewerModel.setHydrogenBondsVisibile(newValue);
        }
    }

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

            localRna3DViewerModel = rna3DViewerModel;
            rna3DViewerModel.build3DStructures();
            rna3DViewerView.getRnaMoleculesG().getChildren().clear();

            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getMeshStructures());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getNucleotide3DStructures().getMeshList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getSugarConnectionList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getPhosphorusList());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getPhosphorusConnections());
            rna3DViewerView.getRnaMoleculesG().getChildren().addAll(rna3DViewerModel.getHydrogenBonds());

            local3DNucleotides = rna3DViewerModel.getNucleotide3DStructures();

            is3DStructureReadIn = true;
        }
    }

    protected static void handleSecStruct(Rna3DViewerModel rna3DViewerModel,
                                          Stage secondaryStage) {
        if (is3DStructureReadIn) {
            localRnaDrawerModel = new RnaDrawerModel(rna3DViewerModel.getSecondaryStructure(), rna3DViewerModel.getResidues(),
                    rna3DViewerModel.getPseudoKnotsFreeList());
            RnaDrawerVC rnaDrawerVC = new RnaDrawerVC(localRnaDrawerModel, secondaryStage);
            rnaDrawerVC.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("PDB Information");
            alert.setHeaderText("No PDB file read in yet.");
            alert.setContentText("Please read in PDB file.");
            alert.showAndWait();
        }
    }

    public static void initSelectionModel(RnaDrawerModel rnaDrawerModel) {
        localSecStructRepresentations = rnaDrawerModel.getSecStruct2DRepresentations();
        ANucleotideStructure[] shapes = localSecStructRepresentations.get2DStructureRepresentations();


        ANucleotideStructure[] shapes3D = local3DNucleotides.getNucleotide3DStructuresAsArray();
        int shapesLen = shapes.length;
        int shapes3DLen = shapes3D.length;
        ANucleotideStructure[] allShapes = new ANucleotideStructure[shapes3DLen + shapesLen];
        System.arraycopy(shapes, 0, allShapes, 0, shapesLen);
        System.arraycopy(shapes3D, 0, allShapes, shapesLen, shapes3DLen);
        mySelectionModel = new SelectionModel<Object>(allShapes);

        // setup selection capture in view:
        for (int i = 0; i < allShapes.length; i++) {
            final int index=i;
            ANucleotideStructure shape = allShapes[i];

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
                if (mySelectionModel.isSelected(index)) {
                    mySelectionModel.clearSelection(index);
                } else {
                    mySelectionModel.clearAndSelect(index);
                    // mySelectionModel.select(index);
                }
                colorStructures(index);
            });
        }

    }

    private static void colorStructures(int index) {
        // circle case
        if (index  <= localSecStructRepresentations.secStruct2DCirclesSize()) {
            // crawl circles
            int residueIndex = -1;
            for (SecStruct2DCircle secStruct2DCircle: localSecStructRepresentations.getSecStruct2DCircles()) {
                if (secStruct2DCircle.getIsSelected()) {
                    secStruct2DCircle.getCircle().setFill(Color.ORANGE);
                    residueIndex = secStruct2DCircle.getResidueIndex();
                } else {
                    secStruct2DCircle.resetColor();
                }
            }
            // crawl lines
            resetLineColoring();
            // crawl 3d objects
            reset3DColoring();
            // set specific 3d object
            if (local3DNucleotides.getFast(residueIndex) != null) {
                local3DNucleotides.getFast(residueIndex).getStructure().setMaterial(new PhongMaterial(Color.ORANGE));
            }

        } else {
            // lines case
            int residueIndex1 = -1;
            int residueIndex2 = -1;
            if (index <= localSecStructRepresentations.secStruct2DLinesSize() + localSecStructRepresentations.secStruct2DCirclesSize()) {
                for (SecStruct2DLine secStruct2DLine : localSecStructRepresentations.getSecStruct2DLines()) {
                    if (secStruct2DLine.getIsSelected()) {
                        residueIndex1 = secStruct2DLine.getResidueIndex1();
                        residueIndex2 = secStruct2DLine.getResidueIndex2();
                        secStruct2DLine.getLine().setFill(Color.ORANGE);
                        secStruct2DLine.getLine().setStroke(Color.ORANGE);
                    } else {
                        secStruct2DLine.resetColor();
                    }
                }
                reset3DColoring();
                resetCircleColoring();
                local3DNucleotides.getFast(residueIndex1).getStructure().setMaterial(new PhongMaterial(Color.ORANGE));
                local3DNucleotides.getFast(residueIndex2).getStructure().setMaterial(new PhongMaterial(Color.ORANGE));
                localSecStructRepresentations.getCircle2DStruct(residueIndex1).getCircle().setFill(Color.ORANGE);
                localSecStructRepresentations.getCircle2DStruct(residueIndex2).getCircle().setFill(Color.ORANGE);

            } else {
                int residueIndex = -1;
                // 3d case
                for (ANucleotideStructure aNucleotideStructure : local3DNucleotides.getNucleotide3DStructures()) {
                    Nucleotide3DStructure nucleotide3DStructure = (Nucleotide3DStructure) aNucleotideStructure;
                    if (nucleotide3DStructure.getIsSelected()) {
                        nucleotide3DStructure.getStructure().setMaterial(new PhongMaterial(Color.ORANGE));
                        residueIndex = nucleotide3DStructure.getResidueIndex();
                    } else {
                        nucleotide3DStructure.resetColor();
                    }
                }
                resetCircleColoring();
                resetLineColoring();
                localSecStructRepresentations.getCircle2DStruct(residueIndex).getCircle().setFill(Color.ORANGE);
            }
        }
    }

    private static void resetLineColoring() {
        for (SecStruct2DLine secStruct2DLine : localSecStructRepresentations.getSecStruct2DLines()) {
            if (secStruct2DLine.getIsSelected()) {
                secStruct2DLine.getLine().setFill(Color.ORANGE);
                secStruct2DLine.getLine().setFill(Color.ORANGE);
            } else {
                secStruct2DLine.resetColor();
            }
        }
    }

    private static void reset3DColoring() {
        for (ANucleotideStructure aNucleotideStructure : local3DNucleotides.getNucleotide3DStructures()) {
            if (aNucleotideStructure.isSelectedProperty().getValue()) {
                MeshView meshView = (MeshView) aNucleotideStructure.getStructure();
                meshView.setMaterial(new PhongMaterial(Color.ORANGE));
            } else {
                Nucleotide3DStructure nucleotide3DStructure = (Nucleotide3DStructure) aNucleotideStructure;
                nucleotide3DStructure.resetColor();
            }
        }
    }

    public static void resetCircleColoring() {
        for (SecStruct2DCircle secStruct2DCircle : localSecStructRepresentations.getSecStruct2DCircles()) {
            if (secStruct2DCircle.getIsSelected()) {
                secStruct2DCircle.getCircle().setFill(Color.ORANGE);
            } else {
                secStruct2DCircle.resetColor();
            }
        }
    }

}
