import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DnaManipulatorModel;
import ui.StageManager;
import ui.dna_manipulator.DnaManipulatorPresenter;
import ui.dna_manipulator.DnaManipulatorView;


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

        DnaManipulatorModel dnaManipulatorModel = new DnaManipulatorModel();
        DnaManipulatorView dnaManipulatorView = new DnaManipulatorView(dnaManipulatorModel);
        Scene scene = new Scene(dnaManipulatorView, 768, 640);
        DnaManipulatorPresenter dnaManipulatorPresenter = new DnaManipulatorPresenter(dnaManipulatorModel, dnaManipulatorView);
        StageManager.getInstance().getPrimaryStage().setScene(scene);
        StageManager.getInstance().getPrimaryStage().setTitle("Dna Manipulator");
        StageManager.getInstance().getPrimaryStage().show();
    }
}
