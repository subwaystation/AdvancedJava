import javafx.application.Application;
import javafx.stage.Stage;
import model.rna_3d_viewer.Rna3DViewerModel;
import ui.rna_3d_viewer.Rna3DViewerVC;

import java.util.List;

/**
 * Created by heumos on 14.12.15.
 */
public class StartRna3DViewer extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rna3DViewerModel rna3DViewerModel = new Rna3DViewerModel();

        Rna3DViewerVC rna3DViewerVC = new Rna3DViewerVC(rna3DViewerModel, primaryStage);
        rna3DViewerVC.show();
    }

}
