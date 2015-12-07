import javafx.application.Application;
import javafx.stage.Stage;
import model.Simple3DViewerModel;
import ui.StageManager;
import ui.simple_3d_viewer.Simple3DViewerVC;

/**
 * Created by heumos on 03.12.15.
 */
public class StartSimple3DViewer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager.getInstance().setPrimaryStage(primaryStage);

        Simple3DViewerModel simple3DViewerModel = new Simple3DViewerModel();

        Simple3DViewerVC simple3DViewerVC = new Simple3DViewerVC(simple3DViewerModel);
        simple3DViewerVC.show();
    }
}
