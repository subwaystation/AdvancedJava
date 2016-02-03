package _rna_3d_viewer.rna_drawer;

import drawing.Graph;
import drawing.SpringEmbedder;
import javafx.animation.ParallelTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import util.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVP {

    public static void handleDrawBEvent(RnaDrawerView rnaDrawerView, RnaDrawerModel rnaDrawerModel) {
        rnaDrawerView.getDrawingP().getChildren().clear();
        boolean animation = true;
        ParallelTransition parallelTransition = new ParallelTransition();
        String dotBracktes = rnaDrawerModel.getSecondaryStructure().getDotBracketsNotation();
        Graph graph = new Graph();
        try {
            graph.parseNotation(dotBracktes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] edges = graph.getEdges();
        double[][] coordinatesOriginal = SpringEmbedder.
                computeSpringEmbedding(1000, graph.getNumberOfNodes(), edges, null);
        double[][] coordinatesCentered = ArrayUtils.deepCopyDoubleMatrix(coordinatesOriginal);
        SpringEmbedder.centerCoordinates(coordinatesCentered, 1, 600, 1, 600);
        // is animation selected?
        if (!animation) {
            coordinatesOriginal = coordinatesCentered;
        }
        Circle circleOld = new Circle();
        List<Circle> circles = new ArrayList<>();
        // draw the circles
        String rnaSeq;
            rnaSeq = rnaDrawerModel.getSecondaryStructure().getSequence();
        for (int i = 0; i < coordinatesOriginal.length; i++) {
            double[] coordinate = coordinatesOriginal[i];
            double xPoint = coordinate[0];
            double yPoint = coordinate[1];
            Circle circle = new Circle();
            circle.setCenterX(xPoint);
            circle.setCenterY(yPoint);
            circle.setRadius(5.0);
            circles.add(circle);
            String tooltipS = String.valueOf(i+1);
            tooltipS += ",";
            char base = rnaSeq.charAt(i);
            tooltipS += base;
            RnaDrawerModel.colorCircle(circle, base);
            Tooltip tooltip = new Tooltip(tooltipS);
            Tooltip.install(circle, tooltip);
            if (animation) {
                RnaDrawerModel.addCircleToAnimation(parallelTransition, circle, coordinatesCentered[i]);
                // register mouse even handler
                circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        circle.setFill(Color.ORANGE);
                        rnaDrawerModel.setxPoint(circle.getCenterX());
                        rnaDrawerModel.setyPoint(circle.getCenterY());
                    }
                });
                circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        circle.setCenterY(event.getY());
                        circle.setCenterX(event.getX());
                    }
                });
                circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        circle.setFill(Color.BLACK);
                        RnaDrawerModel.animateCircle(circle, event, rnaDrawerModel);
                    }
                });
            }
            rnaDrawerView.getDrawingP().getChildren().add(circle);
            // draw the edges to connect dots
            // no bridges drawn yet
            if (i > 0) {
                Line line = new Line();
                line.startXProperty().bind(circleOld.centerXProperty().add(circleOld.translateXProperty()));
                line.startYProperty().bind(circleOld.centerYProperty().add(circleOld.translateYProperty()));
                line.endXProperty().bind(circle.centerXProperty().add(circle.translateXProperty()));
                line.endYProperty().bind(circle.centerYProperty().add(circle.translateYProperty()));
                rnaDrawerView.getDrawingP().getChildren().add(line);
            }
            circleOld = circle;
        }
        // draw bridges if necessary
        List<Pair<Integer,Integer>> secondaryStructure;
        secondaryStructure = rnaDrawerModel.getSecondaryStructure().getSecondaryStructure();
        // FIXME
        System.out.println(circles.size());
        for (Pair<Integer, Integer> pair : secondaryStructure) {
            // pairs positions begin with 1!!!
            Integer basePos1 = pair.getKey() - 1;
            Integer basePos2 = pair.getValue() - 1;
            Line line = new Line();
            Circle circle1 = circles.get(basePos1);
            Circle circle2 = circles.get(basePos2);
            line.startXProperty().bind(circle1.centerXProperty().add(circle1.translateXProperty()));
            line.startYProperty().bind(circle1.centerYProperty().add(circle1.translateYProperty()));
            line.endXProperty().bind(circle2.centerXProperty().add(circle2.translateXProperty()));
            line.endYProperty().bind(circle2.centerYProperty().add(circle2.translateYProperty()));
            rnaDrawerView.getDrawingP().getChildren().add(line);
            char base1 = rnaSeq.charAt(basePos1);
            char base2 = rnaSeq.charAt(basePos2);
            int binding = RnaDrawerModel.validateBinding(base1, base2);
            if (binding == 3) {
                 line.setStroke(Color.RED);
            } else if (binding == 2) {
                line.setStroke(Color.BLUE);
            }
        }
        if (animation) {
            parallelTransition.play();
        }
    }
}
