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
        if (args.length != 1){
            System.err.println("ERROR: Wrong number of arguments. Please enter a valid RNA PDB file.");
            System.exit(1);
        }
        if (!args[0].endsWith(".pdb")) {
            System.err.println("ERROR: The file you entered is not a valid RNA PDB file. The file must end with" +
                    "'.pdb'!");
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> args = getParameters().getRaw();
        Rna3DViewerModel rna3DViewerModel = new Rna3DViewerModel(args.get(0));

        // TODO parse pdb into mashs

        Rna3DViewerVC rna3DViewerVC = new Rna3DViewerVC(rna3DViewerModel, primaryStage);
        rna3DViewerVC.show();
    }

}
