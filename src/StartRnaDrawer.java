import javafx.application.Application;
import javafx.stage.Stage;
import model.RnaDrawerModel;
import ui.StageManager;
import ui.rna_drawer.RnaDrawerVC;

/**
 * Created by heumos on 23.11.15.
 */
public class StartRnaDrawer extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager.getInstance().setPrimaryStage(primaryStage);

        // read in the FASTA file
        RnaDrawerModel rnaDrawerModel = new RnaDrawerModel();

        RnaDrawerVC rnaDrawerVC = new RnaDrawerVC(rnaDrawerModel);
        rnaDrawerVC.show();
    }

    /**
     * Created by heumos on 14.12.15.
     */
    public static class StartRna3DViewer {
    }
}
