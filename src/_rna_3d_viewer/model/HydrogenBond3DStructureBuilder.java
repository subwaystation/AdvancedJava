package _rna_3d_viewer.model;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 31.01.16.
 */
public class HydrogenBond3DStructureBuilder {

    private Residue residue1;

    private Residue residue2;

    private Molecule3DConnectionBuilder molecule3DConnectionBuilder;

    private List<Cylinder> hydrogenBonds3DStructure = new ArrayList<>();

    public HydrogenBond3DStructureBuilder() {
        this.molecule3DConnectionBuilder = new Molecule3DConnectionBuilder(0.2);
        this.molecule3DConnectionBuilder.setColor(Color.BLUE);
    }

    public Residue getResidue1() {
        return residue1;
    }

    public void setResidue1(Residue residue1) {
        this.residue1 = residue1;
    }

    public Residue getResidue2() {
        return residue2;
    }

    public void setResidue2(Residue residue2) {
        this.residue2 = residue2;
    }

    public Molecule3DConnectionBuilder getMolecule3DConnectionBuilder() {
        return molecule3DConnectionBuilder;
    }

    public void setMolecule3DConnectionBuilder(Molecule3DConnectionBuilder molecule3DConnectionBuilder) {
        this.molecule3DConnectionBuilder = molecule3DConnectionBuilder;
    }

    public boolean buildAdeUraHydrogenBond() {
        this.hydrogenBonds3DStructure.clear();
        // adenine coordinates
        Point3D n6points = residue1.getAtomPoint3D("N6");
        Point3D h6points = residue1.getAtomPoint3D("H62");
        Point3D n1points = residue1.getAtomPoint3D("N1");

        // uracil coordinates
        Point3D o4points = residue2.getAtomPoint3D("O4");
        Point3D n3points = residue2.getAtomPoint3D("N3");
        Point3D h3points = residue2.getAtomPoint3D("H3");

        double dist1 = o4points.distance(h6points);
        double dist2 = h3points.distance(n1points);

        double angle1 = h6points.angle(n6points, o4points);
        double angle2 = h3points.angle(n3points, n1points);

        // verify shit here!!!
        boolean dist1B = isRightDist(dist1);
        boolean dist2B = isRightDist(dist2);

        boolean angle1B = isRightAngle(angle1);
        boolean angle2B = isRightAngle(angle2);

        boolean distB = dist1B || dist2B;
        boolean angleB = angle1B || angle2B;
        
        boolean isValidHydrogenBond = distB && angleB;

        if (isValidHydrogenBond) {
            // this.molecule3DConnectionBuilder.setPoints(o4points, n6points);
            // this.hydrogenBonds3DStructure.add(this.molecule3DConnectionBuilder.createConnection());
            this.molecule3DConnectionBuilder.setPoints(n1points, n3points);
            this.hydrogenBonds3DStructure.add(this.molecule3DConnectionBuilder.createConnection());
        }

        return isValidHydrogenBond;
    }

    public boolean buildGuaCytHydrogenBond() {
        this.hydrogenBonds3DStructure.clear();
        // guanine coordinates
        Point3D n4points = residue2.getAtomPoint3D("N4");
        Point3D h4points = residue2.getAtomPoint3D("H41");
        Point3D n3points = residue2.getAtomPoint3D("N3");
        Point3D o2points = residue2.getAtomPoint3D("O2");

        // cytosine coordinates
        Point3D o6points = residue1.getAtomPoint3D("O6");
        Point3D n1points = residue1.getAtomPoint3D("N1");
        Point3D h1points = residue1.getAtomPoint3D("H1");
        Point3D n2points = residue1.getAtomPoint3D("N2");
        Point3D h2points = residue1.getAtomPoint3D("H21");

        double dist1 = h4points.distance(o6points);
        double dist2 = h1points.distance(n3points);
        double dist3 = h2points.distance(o2points);

        double angle1 = h4points.angle(n4points, o6points);
        double angle2 = h1points.angle(n1points, n3points);
        double angle3 = h2points.angle(n2points, o2points);

        // verify shit here!!!
        boolean dist1B = isRightDist(dist1);
        boolean dist2B = isRightDist(dist2);
        boolean dist3B = isRightDist(dist3);

        boolean angle1B = isRightAngle(angle1);
        boolean angle2B = isRightAngle(angle2);
        boolean angle3B = isRightAngle(angle3);

        boolean distB = dist1B || dist2B || dist3B;
        boolean angleB = angle1B || angle2B || angle3B;

        boolean isValidHydrogenBond = distB && angleB;

        if (isValidHydrogenBond) {
            // this.molecule3DConnectionBuilder.setPoints(o4points, n6points);
            // this.hydrogenBonds3DStructure.add(this.molecule3DConnectionBuilder.createConnection());
            this.molecule3DConnectionBuilder.setPoints(n1points, n3points);
            this.hydrogenBonds3DStructure.add(this.molecule3DConnectionBuilder.createConnection());
        }

        return isValidHydrogenBond;
    }

    private boolean isRightDist(double dist) {
        return (dist <= 6.0 && dist >= 1.5);
    }

    private boolean isRightAngle(double angle) {
        return (angle <= 160.0 && angle >= 130.0);
    }

    public List<Cylinder> getHydrogenBonds3DStructure() {
        return this.hydrogenBonds3DStructure;
    }
}
