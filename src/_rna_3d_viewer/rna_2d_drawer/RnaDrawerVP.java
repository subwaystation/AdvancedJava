package _rna_3d_viewer.rna_2d_drawer;

import _rna_3d_viewer.model.Residue;
import _rna_3d_viewer.model.structure.*;
import _rna_3d_viewer.view.Rna3DViewerVP;
import drawing.Graph;
import drawing.SpringEmbedder;
import javafx.animation.ParallelTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import util.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerVP {

    public static boolean firstDraw = false;

    public static void handleDrawBEvent(RnaDrawerView rnaDrawerView, RnaDrawerModel rnaDrawerModel) {
        // draw primary structure first
        drawPrimaryStructure(rnaDrawerModel, rnaDrawerView);

        // draw secondary structure
        boolean drawPseudoknots = rnaDrawerView.getDrawPseudoknotsCB().isSelected();
        if (firstDraw) {
            Rna3DViewerVP.handleDrawBEvent();
        } else {
            firstDraw = true;
            Rna3DViewerVP.setLocalRnaDrawerView(rnaDrawerView);
            SecStruct2DRepresentations secStruct2DRepresentations = rnaDrawerModel.getSecStruct2DRepresentations();
            List<Residue> residues = rnaDrawerModel.getResidueList();
            rnaDrawerView.getDrawingP().getChildren().clear();
            boolean animation = true;
            ParallelTransition parallelTransition = new ParallelTransition();
            String dotBrackets = rnaDrawerModel.getSecondaryStructure().getDotBracketsNotation();
            Graph graph = new Graph();
            try {
                graph.parseNotation(dotBrackets);
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
                Residue residue = residues.get(i);
                double[] coordinate = coordinatesOriginal[i];
                double xPoint = coordinate[0];
                double yPoint = coordinate[1];
                Circle circle = new Circle();
                circle.setCenterX(xPoint);
                circle.setCenterY(yPoint);
                circle.setRadius(5.0);
                circles.add(circle);
                // add circle to 2d representation
                SecStruct2DCircle secStruct2DCircle =  new SecStruct2DCircle(residue.getResidueIndex(), residue.getResidueType(), circle);
                secStruct2DRepresentations.getSecStruct2DCircles().add(secStruct2DCircle);

                String tooltipS = String.valueOf(i+1);
                tooltipS += ",";
                char base = rnaSeq.charAt(i);
                tooltipS += base;
                RnaDrawerModel.colorCircleByType(circle, base);
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
            if (!drawPseudoknots) {
                secondaryStructure = rnaDrawerModel.getPseudoKnotsFreeList();
            }
            for (Pair<Integer, Integer> pair : secondaryStructure) {
                // pairs positions begin with 1!!!
                Integer basePos1 = pair.getKey() - 1;
                Integer basePos2 = pair.getValue() - 1;
                Line line = new Line();
                line.setStrokeWidth(3.0);
                Circle circle1 = circles.get(basePos1);
                Circle circle2 = circles.get(basePos2);
                // add line to 2d presentation
                SecStruct2DLine secStruct2DLine = new SecStruct2DLine(line, residues.get(basePos1).getResidueIndex(),
                        residues.get(basePos2).getResidueIndex());
                secStruct2DRepresentations.getSecStruct2DLines().add(secStruct2DLine);
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
            secStruct2DRepresentations.createCircle2DMap();
            Rna3DViewerVP.initSelectionModel(rnaDrawerModel);
        }
    }

    private static void drawPrimaryStructure(RnaDrawerModel rnaDrawerModel, RnaDrawerView rnaDrawerView) {
        List<PrimaryStruct> primaryStructs = new ArrayList<>();
        TextFlow textFlow = new TextFlow();
        for (Residue residue : rnaDrawerModel.getResidueList()) {
            int residueIndex = residue.getResidueIndex();
            String residueType = residue.getResidueType();
            Text text = new Text(residueType);
            text.setFill(rnaDrawerModel.getColorByType(residueType.charAt(0)));
            textFlow.getChildren().add(text);
            PrimaryStruct primaryStruct = new PrimaryStruct(text, residueIndex);
            primaryStructs.add(primaryStruct);
        }
        rnaDrawerView.getPrimaryseqHB().getChildren().add(textFlow);
        PrimaryStructRepresentations primaryStructRepresentations = new PrimaryStructRepresentations(primaryStructs, textFlow);
        rnaDrawerModel.setPrimaryStructRepresentations(primaryStructRepresentations);
    }

    private static void eliminatePseudoKnots(List<Pair<Integer, Integer>> secStruct,
                                             List<Pair<Integer, Integer>> pseudoKnots) {
        System.out.println(secStruct);
        System.out.println(pseudoKnots);
        for (Pair<Integer, Integer> pseudoKnot : pseudoKnots) {
            secStruct.remove(pseudoKnot);
        }
    }

    public static void setFirstDraw(boolean b) {
        firstDraw = b;
    }
}
