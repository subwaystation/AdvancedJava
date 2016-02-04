package _rna_3d_viewer.rna_2d_drawer;

import _rna_3d_viewer.model.Rna3DViewerModel;
import _rna_3d_viewer.model.structure.SecStruct2DRepresentations;
import _rna_3d_viewer.view.Rna3DViewerVC;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVC {

    // model
    public static RnaDrawerModel rnaDrawerModel;

    // view
    private RnaDrawerView rnaDrawerView;

    // stage
    private Stage primaryStage;

    public RnaDrawerVC(RnaDrawerModel rnaDrawerModel, Stage primaryStage) {
        this.rnaDrawerModel = rnaDrawerModel;
        this.rnaDrawerView = new RnaDrawerView();
        this.primaryStage = primaryStage;

        // register event handler
        this.rnaDrawerView.getDrawB().setOnAction(this::handleDrawBEvent);
    }

    private void handleDrawBEvent(ActionEvent actionEvent) {
        RnaDrawerVP.handleDrawBEvent(this.rnaDrawerView, this.rnaDrawerModel);
    }

    public void show() {
        this.rnaDrawerView.show(primaryStage);
    }

    public RnaDrawerView getRnaDrawerView() {
        return this.rnaDrawerView;
    }

    public void setRnaDrawerModel(RnaDrawerModel rnaDrawerModel) {
        this.rnaDrawerModel = rnaDrawerModel;
    }
}
