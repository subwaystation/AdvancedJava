package archive;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


/**
 * Created by heumos on 03.11.15.
 */
public class Playground extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cluster Viewer");

        BorderPane borderPane = new BorderPane();
        //Creating tree items
         TreeItem<String> childNode1 = new TreeItem<>("Child Node 1");
         TreeItem<String> childNode2 = new TreeItem<>("Child Node 2");
         TreeItem<String> childNode3 = new TreeItem<>("Child Node 3");

         TreeItem<String> childNode4 = new TreeItem<>("Child Node 4");
         TreeItem<String> childNode5 = new TreeItem<>("Child Node 5");
         TreeItem<String> childNode6 = new TreeItem<>("Child Node 6");

        //Creating the root element
        TreeItem<String> fakeRoot = new TreeItem<>("Root node");
        fakeRoot.setExpanded(false);

        TreeItem<String> root1 = new TreeItem<>("Root 1 node");
        root1.getChildren().addAll(childNode4, childNode5, childNode6);
        //Adding tree items to the root
        TreeItem<String> root2 = new TreeItem<>("Root 2 node");
        root2.getChildren().addAll(childNode4, childNode5, childNode6);
        //Adding tree items to the root
        fakeRoot.getChildren().addAll(root1, root2);

        //Creating a column
        TreeTableColumn<String,String> column = new TreeTableColumn<>("Column");
        column.setPrefWidth(150);
        TreeTableColumn<String,String> column1 = new TreeTableColumn<>("Column1");

        //Defining cell content
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue()));
        column1.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue()));

        //Creating a tree table view
        TreeTableView<String> treeTableView = new TreeTableView<>(fakeRoot);
        treeTableView.setShowRoot(false);
        treeTableView.getColumns().add(column);
        treeTableView.getColumns().add(column1);
        treeTableView.setPrefWidth(Integer.MAX_VALUE);

        Scene scene = new Scene(borderPane, 768, 640);
        borderPane.setCenter(treeTableView);
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        borderPane.setTop(menuBar);

        Menu fileMenu = new Menu("File");
        MenuItem openMenuItem = new MenuItem("Open");
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String dir = System.getProperty("user.dir") + File.separator;
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open CLSR file.");
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    System.out.println(file);
                }
            }
        });
        fileMenu.getItems().add(openMenuItem);
        menuBar.getMenus().addAll(fileMenu);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
