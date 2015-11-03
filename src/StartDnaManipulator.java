import javafx.application.Application;
import javafx.stage.Stage;
import ui.StageManager;
import ui.dna_manipulator.DnaManipulatorVC;


/**
 * Created by heumos on 29.10.15.
 */
public class StartDnaManipulator extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageManager.getInstance().setPrimaryStage(primaryStage);

        DnaManipulatorVC dnaManipulatorVC = new DnaManipulatorVC();
        dnaManipulatorVC.show();
    }
}
