package ui;

import javafx.stage.Stage;

/**
 * Created by heumos on 23.10.15.
 *
 * A java class making use of the Singleton Pattern in order to ensure
 * that only one instance of the primary Stage is available in the Code.
 *
 */
public class StageManager {

    // the instance of this class
    private  static StageManager instance;

    // the primary stage
    private static Stage primaryStage;

    /**
     * This method returns the current instance of
     * the StageManager. If no instance is existing yet,
     * a new one is instanciated.
     * @return the instance of the StageManager.
     */
    public synchronized static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        StageManager.primaryStage = primaryStage;
    }
}
