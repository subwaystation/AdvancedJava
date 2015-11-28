package ui.rna_drawer;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import model.RnaDrawerModel;
import ui.StageManager;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVC {

    // model
    private RnaDrawerModel rnaDrawerModel;

    // view
    private RnaDrawerView rnaDrawerView;

    public RnaDrawerVC(RnaDrawerModel rnaDrawerModel) {
        this.rnaDrawerModel = rnaDrawerModel;
        this.rnaDrawerView = new RnaDrawerView();

        // register event handler
        this.rnaDrawerView.getEnterSeqTA().textProperty().addListener(this::handleEnteredSeqTaChange);
        this.rnaDrawerView.getFoldB().setOnAction(this::handleFoldBEvent);
        this.rnaDrawerView.getSecStructTA().textProperty().addListener(this::handleSecStructTaChange);
        this.rnaDrawerView.getDrawB().setOnAction(this::handleDrawBEvent);
    }

    private void handleDrawBEvent(ActionEvent actionEvent) {
        RnaDrawerVP.handleDrawBEvent(this.rnaDrawerView, this.rnaDrawerModel);
    }

    private void handleFoldBEvent(ActionEvent actionEvent) {
        RnaDrawerVP.handleFoldBEvent(this.rnaDrawerView, this.rnaDrawerModel);
    }

    private void handleEnteredSeqTaChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        RnaDrawerVP.handleEnteredSeqChange(this.rnaDrawerView);
    }

    private void handleSecStructTaChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        RnaDrawerVP.handleSecStructChange(this.rnaDrawerView);
    }

    public void show() {
        this.rnaDrawerView.show(StageManager.getInstance().getPrimaryStage());
    }
}
