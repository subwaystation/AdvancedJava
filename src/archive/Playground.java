package archive;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Created by heumos on 03.11.15.
 */
public class Playground extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dna Manipulator");
        GridPane root = new GridPane();
        root.setPadding(new Insets(0, 50, 0, 50));
        root.setVgap(20);
        TextArea textArea1 = new TextArea("Unmanipulated");
        TextArea textArea2 = new TextArea("Manipulated");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        FlowPane buttonFP = new FlowPane();
        buttonFP.getChildren().add(btn);
        buttonFP.setAlignment(Pos.CENTER);
        root.add(textArea1, 0, 0);
        root.setHgrow(textArea1, Priority.ALWAYS);
        root.add(buttonFP, 0, 1);
        root.add(textArea2, 0, 2);

        GridPane buttonsGP = new GridPane();
        buttonsGP.setHgap(4);
        Button filterB = new Button("filter");
        filterB.setMaxWidth(Double.MAX_VALUE);
        Button upperCaseB = new Button("upper case");
        upperCaseB.setMaxWidth(Double.MAX_VALUE);
        Button lowerCaseB = new Button("lower case");
        lowerCaseB.setMaxWidth(Double.MAX_VALUE);
        buttonsGP.getColumnConstraints().addAll(new ColumnConstraints());
        buttonsGP.getColumnConstraints().get(0).setPercentWidth(100.0 / 3.0);
        buttonsGP.getColumnConstraints().addAll(new ColumnConstraints());
        buttonsGP.getColumnConstraints().get(1).setPercentWidth(100.0 / 3.0);
        buttonsGP.getColumnConstraints().addAll(new ColumnConstraints());
        buttonsGP.getColumnConstraints().get(2).setPercentWidth(100.0 / 3.0);
        buttonsGP.add(filterB, 0, 0);
        buttonsGP.add(upperCaseB, 1, 0);
        buttonsGP.add(lowerCaseB, 2, 0);
        Button toRnaB = new Button("to RNA");
        toRnaB.setMaxWidth(Double.MAX_VALUE);
        Button reverseB = new Button("reverse");
        reverseB.setMaxWidth(Double.MAX_VALUE);
        Button complementaryB = new Button("complementary");
        complementaryB.setMaxWidth(Double.MAX_VALUE);

        Button reverseComplementaryB = new Button("reverse-complementary");
        reverseComplementaryB.setMaxWidth(Double.MAX_VALUE);
        Button gCContentB = new Button("GC content");
        gCContentB.setMaxWidth(Double.MAX_VALUE);
        Button lengthB = new Button("length");
        lengthB.setMaxWidth(Double.MAX_VALUE);

        Button clearB = new Button("clear");
        clearB.setMaxWidth(Double.MAX_VALUE);
        buttonsGP.add(toRnaB, 0, 1);
        buttonsGP.add(reverseB, 1, 1);
        buttonsGP.add(complementaryB, 2, 1);
        buttonsGP.add(reverseComplementaryB, 0, 2);
        buttonsGP.add(gCContentB, 1, 2);
        buttonsGP.add(lengthB, 2, 2);
        buttonsGP.add(clearB, 0, 3);
        root.add(buttonsGP, 0, 3);
        Slider lineWidthS = new Slider();
        lineWidthS.setMin(10);
        lineWidthS.setMax(50);
        lineWidthS.setShowTickLabels(true);
        lineWidthS.setShowTickMarks(true);
        lineWidthS.setBlockIncrement(10);
        lineWidthS.setMinorTickCount(5);
        lineWidthS.setValue(40);
        lineWidthS.setMajorTickUnit(10);
        lineWidthS.snapToTicksProperty().set(true);
        lineWidthS.setPadding(new Insets(0, 60, 0, 60));
        Label label = new Label("choose line width:");
        label.setPadding(new Insets(0, 60, 0, 60));
        root.add(label, 0, 4);
        root.add(lineWidthS, 0, 5);
        primaryStage.setScene(new Scene(root, 768, 640));
        primaryStage.show();
    }

}
