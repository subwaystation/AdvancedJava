package _rna_3d_viewer.rna_drawer;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVC {

    // model
    private RnaDrawerModel rnaDrawerModel;

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
}
