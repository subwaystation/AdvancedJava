package ui.rna_drawer;

import drawing.Graph;
import drawing.SpringEmbedder;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import lib.sec_struct.Nussinov;
import model.RnaDrawerModel;
import seq.sequence.RnaSequence;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVP {

    public static void handleEnteredSeqChange(RnaDrawerView rnaDrawerView) {

        String rnaSeq = rnaDrawerView.getEnterSeqTA().getText().replace("\n", "");

        // check if valid RNA sequence
        if (RnaSequence.validateRnaString(rnaSeq)) {
            rnaDrawerView.getFoldB().setDisable(false);
        } else {
            rnaDrawerView.getFoldB().setDisable(true);
        }
    }

    public static void handleSecStructChange(RnaDrawerView rnaDrawerView) {
        String secStruct = rnaDrawerView.getSecStructTA().getText().replace("\n", "");

        // check if string contains only dots and brackets
        boolean validateDotBrackets = RnaDrawerModel.validateDotBrackets(secStruct);
        if (validateDotBrackets) {
            rnaDrawerView.getDrawB().setDisable(false);
        } else {
            rnaDrawerView.getDrawB().setDisable(true);
        }
    }

    public static void handleFoldBEvent(RnaDrawerView rnaDrawerView, RnaDrawerModel rnaDrawerModel) {
        String rnaSeq = rnaDrawerView.getEnterSeqTA().getText();
        rnaDrawerModel.setNussinov(new Nussinov(rnaSeq));
        String secStruct = rnaDrawerModel.getNussinov().getBracketNotation();
        rnaDrawerView.getSecStructTA().setText(secStruct);
    }


    public static void handleDrawBEvent(RnaDrawerView rnaDrawerView, RnaDrawerModel rnaDrawerModel) {
        boolean seqPresent = !rnaDrawerView.getFoldB().isDisabled();
        String dotBracktes = rnaDrawerView.getSecStructTA().getText();
        Graph graph = new Graph();
        try {
            graph.parseNotation(dotBracktes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] edges = graph.getEdges();
        double[][] coordinates = SpringEmbedder.computeSpringEmbedding(1000, graph.getNumberOfNodes(), edges, null);
        SpringEmbedder.centerCoordinates(coordinates, 1, 600, 1, 600);
        double xPointOld = 0.0;
        double yPointOld = 0.0;
        List<Circle> circles = new ArrayList<>();
        // draw the circles
        String rnaSeq;
        if (seqPresent) {
            rnaSeq = rnaDrawerModel.getNussinov().getSequence();
        } else {
            rnaSeq = "";
        }
        for (int i = 0; i < coordinates.length; i++) {
            double[] coordinate = coordinates[i];
            double xPoint = coordinate[0];
            double yPoint = coordinate[1];
            Circle circle = new Circle();
            circle.setCenterX(xPoint);
            circle.setCenterY(yPoint);
            circle.setRadius(5.0);
            circles.add(circle);
            String tooltipS = String.valueOf(i+1);
            if (seqPresent) {
                tooltipS += ",";
                char base = rnaSeq.charAt(i);
                tooltipS += base;
                RnaDrawerModel.colorCircle(circle, base);
            }
            Tooltip tooltip = new Tooltip(tooltipS);
            Tooltip.install(circle, tooltip);
            rnaDrawerView.getDrawingP().getChildren().add(circle);
            // draw the edges to connect dots
            // no bridges drawn yet
            if (i > 0) {
                Line line = new Line();
                line.setStartX(xPointOld);
                line.setStartY(yPointOld);
                line.setEndX(xPoint);
                line.setEndY(yPoint);
                rnaDrawerView.getDrawingP().getChildren().add(line);
            }
            xPointOld = xPoint;
            yPointOld = yPoint;
        }
        // draw bridges if necessary
        ArrayList<Pair<Integer,Integer>> secondaryStructure;
        if (seqPresent) {
            secondaryStructure = rnaDrawerModel.getNussinov().getSecondaryStructure();
        } else {
            secondaryStructure = RnaDrawerModel.parseDotBracketToPairs(rnaDrawerView.getSecStructTA().getText());
        }
        for (Pair<Integer, Integer> pair : secondaryStructure) {
            Integer basePos1 = pair.getKey();
            Integer basePos2 = pair.getValue();
            Line line = new Line();
            Circle circle1 = circles.get(basePos1);
            Circle circle2 = circles.get(basePos2);
            line.startXProperty().bind(circle1.centerXProperty().add(circle1.translateXProperty()));
            line.startYProperty().bind(circle1.centerYProperty().add(circle1.translateYProperty()));
            line.endXProperty().bind(circle2.centerXProperty().add(circle2.translateXProperty()));
            line.endYProperty().bind(circle2.centerYProperty().add(circle2.translateYProperty()));
            rnaDrawerView.getDrawingP().getChildren().add(line);
            if (seqPresent) {
                char base1 = rnaSeq.charAt(basePos1);
                char base2 = rnaSeq.charAt(basePos2);
                int binding = RnaDrawerModel.validateBinding(base1, base2);
                if (binding == 3) {
                    line.setStroke(Color.RED);
                } else if (binding == 2) {
                    line.setStroke(Color.BLUE);
                }
            } else {
                line.setStroke(Color.GREEN);
            }
        }
    }
}
