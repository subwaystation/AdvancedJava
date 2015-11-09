package ui.cluster_viewer;

import ui.StageManager;

/**
 * Created by heumos on 09.11.15.
 */
public class ClusterViewerVC {

    // view
    private ClusterViewerView clusterViewerView;

    public ClusterViewerVC() {
        this.clusterViewerView = new ClusterViewerView();

        // register event handler
        this.clusterViewerView.getMenuItem().setOnAction((actionEvent) ->
                ClusterViewerVP.buildTableTree(this.clusterViewerView));
    }

    public void show() {
        this.clusterViewerView.show(StageManager.getInstance().getPrimaryStage());
    }

}
