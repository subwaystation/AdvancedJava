package ui.rna_3d_viewer;

import javafx.stage.Stage;
import model.rna_3d_viewer.Rna3DViewerModel;

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

        this.rna3DViewerView.getRnaMoleculesG().getChildren().addAll(this.rna3DViewerModel.getMeshViewList());

        // register event handler
        this.rna3DViewerView.getScene().setOnMousePressed(new Rna3DViewerVP.HandleMousePressed(rna3DViewerModel));
        this.rna3DViewerView.getScene().setOnMouseDragged(new Rna3DViewerVP.HandleMouseDragged(this.rna3DViewerView, this.rna3DViewerModel));
    }

    public void show() {
        this.rna3DViewerView.show(this.primaryStage);
    }
}
