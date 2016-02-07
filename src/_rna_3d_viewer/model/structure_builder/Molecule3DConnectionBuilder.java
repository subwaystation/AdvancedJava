package _rna_3d_viewer.model.structure_builder;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Creates molecule connections given end and start points (atoms).
 *
 * Created by heumos on 06.01.16.
 */
public class Molecule3DConnectionBuilder {

    // the radius of the resulting cylinder
    private double radius;

    // the starting point of the cylinder
    private Point3D initPoint;

    // the end point of the cylinder
    private Point3D endPoint;

    // the color of the cylinder
    private Color color;

    public Molecule3DConnectionBuilder(double radius) {
        this.radius = radius;
        this.color = Color.BLACK;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point3D getInitPoint() {
        return initPoint;
    }

    public Point3D getEndPoint() {
        return endPoint;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setInitPoint(Point3D initPoint) {
        this.initPoint = initPoint;
    }

    public void setEndPoint(Point3D endPoint) {
        this.endPoint = endPoint;
    }

    public void setInitPoint(float[] initAtom) {
        this.setInitPoint(new Point3D(initAtom[0], initAtom[1], initAtom[2]));
    }

    public void setEndPoint(float[] endAtom) {
        this.setEndPoint(new Point3D(endAtom[0], endAtom[1], endAtom[2]));
    }

    public void setPoints(float[] atoms) {
        setInitPoint(new Point3D(atoms[0], atoms[1], atoms[2]));
        setEndPoint(new Point3D(atoms[3], atoms[4], atoms[5]));
    }

    public void setPoints(Point3D point1, Point3D point2) {
        setInitPoint(point1);
        setEndPoint(point2);
    }

    /**
     * Create molecule (atom) connection in 3d representation.
     * @return the molecule connection
     */
    public Cylinder createConnection() {
        if(this.initPoint != null && this.endPoint != null){
            Point3D yAxis = new Point3D(0, 1, 0);
            Point3D diff = this.endPoint.subtract(this.initPoint);
            double height = diff.magnitude();

            Point3D mid = this.endPoint.midpoint(this.initPoint);
            Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

            Point3D axisOfRotation = diff.crossProduct(yAxis);
            double angle = Math.acos(diff.normalize().dotProduct(yAxis));
            Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

            Cylinder cylinder = new Cylinder(this.radius, height);

            cylinder.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
            cylinder.setMaterial(new PhongMaterial(this.color));

            return cylinder;
        } else{
            return new Cylinder();
        }
    }

}
