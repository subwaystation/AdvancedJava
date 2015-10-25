import javafx.application.Application;
import javafx.stage.Stage;
import model.AlignmentModel;
import ui.AlignmentVC;
import ui.StageManager;

import java.util.List;

/**
 * Created by heumos on 25.10.15.
 */
public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageManager.getInstance().setPrimaryStage(primaryStage);
        List<String> parameters = getParameters().getRaw();

        // read in the FASTA file
        AlignmentModel alignmentModel = new AlignmentModel(parameters.toArray(new String[parameters.size()]));

        AlignmentVC alignmentVC = new AlignmentVC(alignmentModel);
        alignmentVC.show();
    }
}
