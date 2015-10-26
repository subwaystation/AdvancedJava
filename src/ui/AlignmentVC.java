package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.AlignmentModel;

/**
 * Created by heumos on 25.10.15.
 *
 * A java class representing the controller of MVC pattern.
 * Here the controller reacts to changes in the model and view,
 * respectively.
 */
public class AlignmentVC {

    // model
    private AlignmentModel alignmentModel;

    // view
    private AlignmentView alignmentView;

    public AlignmentVC(AlignmentModel alignmentModel) {
        this.alignmentModel = alignmentModel;
        this.alignmentView = new AlignmentView();

        // register event_handling handler
        alignmentView.getSelectAllB().setOnAction((actionEvent) ->
                AlignmentVP.setAlignmentViewCheckBoxes(alignmentView, true));
        alignmentView.getClearSelectionB().setOnAction((actionEvent) ->
                AlignmentVP.setAlignmentViewCheckBoxes(alignmentView, false));
        alignmentView.getApplyB().setOnAction((actionEvent) ->
                AlignmentVP.updateAlignmentView(alignmentModel, alignmentView));
    }

    public void show() {
        alignmentView.show(StageManager.getInstance().getPrimaryStage());
    }

}
