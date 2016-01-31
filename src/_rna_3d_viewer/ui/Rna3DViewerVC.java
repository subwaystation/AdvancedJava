package _rna_3d_viewer.ui;

import javafx.stage.Stage;
import _rna_3d_viewer.model.Rna3DViewerModel;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerVC {

    // model
    private Rna3DViewerModel rna3DViewerModel;

    // view
    private Rna3DViewerView rna3DViewerView;

    // stage
    private Stage primaryStage;

    public Rna3DViewerVC(Rna3DViewerModel rna3DViewerModel, Stage primaryStage) {
        this.rna3DViewerModel = rna3DViewerModel;
        this.rna3DViewerView = new Rna3DViewerView();
        this.primaryStage = primaryStage;

        // register event handler
        this.rna3DViewerView.getScene().setOnMousePressed(new Rna3DViewerVP.HandleMousePressed(rna3DViewerModel));
        this.rna3DViewerView.getScene().setOnMouseDragged(new Rna3DViewerVP.HandleMouseDragged(this.rna3DViewerView, this.rna3DViewerModel));
        this.rna3DViewerView.getOpenPDB().setOnAction((actionEvent) -> Rna3DViewerVP.handleFileOpener(rna3DViewerView, rna3DViewerModel, primaryStage));
        this.rna3DViewerView.getScene().widthProperty().addListener(new Rna3DViewerVP.HandleSceneWidth(this.rna3DViewerView));
        this.rna3DViewerView.getScene().heightProperty().addListener(new Rna3DViewerVP.HandleSceneHeight(this.rna3DViewerView));
    }

    public void show() {
        this.rna3DViewerView.show(this.primaryStage);
    }
}
