import javafx.application.Application;
import javafx.stage.Stage;
import ui.StageManager;
import ui.cluster_viewer.ClusterViewerVC;

/**
 * Created by heumos on 09.11.15.
 */
public class StartClusterViewer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageManager.getInstance().setPrimaryStage(primaryStage);

        ClusterViewerVC clusterViewerVC = new ClusterViewerVC();
        clusterViewerVC.show();
    }
}
