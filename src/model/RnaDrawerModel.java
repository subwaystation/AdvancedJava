package model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.util.Pair;
import lib.sec_struct.Nussinov;

import java.util.ArrayList;

/**
 * Created by heumos on 23.11.15.
 */
public class RnaDrawerModel {

    // nussinov
    private Nussinov nussinov;

    // store the location of the last clicked node
    private double xPoint;
    private double yPoint;

    public RnaDrawerModel() {

    }

    public static boolean validateDotBrackets(String secStruct) {
        if (secStruct.length() < 1) {
            return false;
        }
        String dotBrackets = ".()";
        for (int i = 0; i < secStruct.length(); i++) {
            char c = secStruct.charAt(i);
            if (!dotBrackets.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    public Nussinov getNussinov() {
        return nussinov;
    }

    public void setNussinov(Nussinov nussinov) {
        this.nussinov = nussinov;
    }

    public static int validateBinding(char base1, char base2) {
        base1 = Character.toLowerCase(base1);
        base2 = Character.toLowerCase(base2);
        char u = 'u';
        char a = 'a';
        char c = 'c';
        char g = 'g';
        if ((base1 == c && base2 == g) || (base1 == g && base2 == c)) {
            return 3;
        } else if ((base1 == u && base2 == a) || (base1 == a && base2 == u)) {
            return 2;
        } else {
            return -1;
        }
    }

    public static ArrayList<Pair<Integer, Integer>> parseDotBracketToPairs(String text) {
        ArrayList<Integer> helperList = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> secondaryStructure = new ArrayList<>();
        char dot = '.';
        char open = '(';
        char close = ')';
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == open) {
                helperList.add(i);
            } else if (c == close) {
                Pair<Integer, Integer> pair = new Pair<>(new Integer(helperList.get(helperList.size() - 1)),i);
                helperList.remove(helperList.size() - 1);
                secondaryStructure.add(pair);
            }
        }
        return secondaryStructure;
    }

    public static void colorCircle(Circle circle, char base) {
        base = Character.toLowerCase(base);
        switch(base) {
            case 'a':
                circle.setStroke(Color.AQUAMARINE);
                break;
            case 'u':
                circle.setStroke(Color.BROWN);
                break;
            case 'c':
                circle.setStroke(Color.GREENYELLOW);
                break;
            case 'g':
                circle.setStroke(Color.DARKRED);
                break;
            default:
                circle.setStroke(Color.BLACK);
                break;
        }
    }

    public static void addCircleToAnimation(ParallelTransition parallelTransition,
                                            Circle circle, double[] coordinate) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue xKeyVal = new KeyValue(circle.centerXProperty(), coordinate[0]);
        KeyValue yKeyVal = new KeyValue(circle.centerYProperty(), coordinate[1]);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(5000), xKeyVal, yKeyVal);
        timeline.getKeyFrames().add(keyFrame);
        parallelTransition.getChildren().add(timeline);
    }

    public double getxPoint() {
        return xPoint;
    }

    public void setxPoint(double xPoint) {
        this.xPoint = xPoint;
    }

    public double getyPoint() {
        return yPoint;
    }

    public void setyPoint(double yPoint) {
        this.yPoint = yPoint;
    }

    public static void animateCircle(Circle circle, MouseEvent event, RnaDrawerModel rnaDrawerModel) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue xKeyVal = new KeyValue(circle.centerXProperty(), new Double(rnaDrawerModel.getxPoint()));
        KeyValue yKeyVal = new KeyValue(circle.centerYProperty(), new Double(rnaDrawerModel.getyPoint()));
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), xKeyVal, yKeyVal);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
}
