package ui.cluster_viewer;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.StageManager;

/**
 * Created by heumos on 09.11.15.
 */
public class ClusterViewerView {

    // the scene
    private Scene scene;

    // the root pane is a border pane
    private BorderPane borderPane;

    // the menu bar
    private MenuBar menuBar;

    // the open menu item
    private MenuItem menuItem;

    public ClusterViewerView() {
        this.borderPane = new BorderPane();

        this.menuBar = new MenuBar();
        this.menuBar.prefWidthProperty().bind(StageManager.getInstance().getPrimaryStage().widthProperty());
        this.borderPane.setTop(this.menuBar);

        Menu fileMenu = new Menu("File");
        this.menuItem = new MenuItem("Open");
        fileMenu.getItems().addAll(this.menuItem);
        this.menuBar.getMenus().add(fileMenu);

        this.scene = new Scene(this.borderPane, 1024, 640);
        this.scene.getStylesheets().addAll("stylesheet.css");
    }

    public void show(Stage stage) {
        stage.setTitle("Cluster Viewer");
        stage.setScene(this.scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }
}
