package _rna_3d_viewer.model;

import javafx.geometry.Point3D;
import javafx.scene.shape.Cylinder;

/**
 * Created by heumos on 31.01.16.
 */
public class HydrogenBond3DStructureBuilder {

    private Residue residue1;

    private Residue residue2;

    private Molecule3DConnectionBuilder molecule3DConnectionBuilder;

    private Cylinder hydrogenBond;

    public HydrogenBond3DStructureBuilder() {
        this.molecule3DConnectionBuilder = new Molecule3DConnectionBuilder(0.2);
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
        // adenine coordinates
        Point3D n6points = residue1.getAtomPoint3D("N6");
        Point3D h6points = residue1.getAtomPoint3D("H6");
        Point3D n1points = residue1.getAtomPoint3D("N1");

        // uracil coordinates
        Point3D o4points = residue2.getAtomPoint3D("O4");
        Point3D n3points = residue2.getAtomPoint3D("N3");
        Point3D h3points = residue2.getAtomPoint3D("H3");

        double dist1 = o4points.distance(h6points);
        double dist2 = h3points.distance(n1points);

        double angle1 = o4points.angle(h6points, n6points);
        double angle2 = n1points.angle(h3points, n3points);

        // TODO verify shit here!!!
        return false;
    }
}
