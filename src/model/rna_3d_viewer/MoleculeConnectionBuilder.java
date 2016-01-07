package model.rna_3d_viewer;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Created by heumos on 06.01.16.
 */
public class MoleculeConnectionBuilder {

    // the radius of the resulting cylinder
    private double radius;

    // the starting point of the cylinder
    private Point3D initPoint;

    // the end point of the cylinder
    private Point3D endPoint;

    public MoleculeConnectionBuilder(double radius) {
        this.radius = radius;
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

    public Cylinder createBond() {
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
            cylinder.setMaterial(new PhongMaterial(Color.BLACK));

            return cylinder;
        } else{
            return new Cylinder();
        }
    }

}