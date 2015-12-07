import javafx.application.Application;
import javafx.stage.Stage;
import model.Simple3DViewer2DModel;
import ui.StageManager;
import ui.simple_3d_viewer_2d.Simple3DViewer2DVC;

/**
 * Created by heumos on 07.12.15.
 */
public class StartSimple3DViewer2D extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager.getInstance().setPrimaryStage(primaryStage);

        Simple3DViewer2DModel simple3DViewer2DModel = new Simple3DViewer2DModel();

        Simple3DViewer2DVC simple3DViewerVC = new Simple3DViewer2DVC(simple3DViewer2DModel);
        simple3DViewerVC.show();
    }
}
