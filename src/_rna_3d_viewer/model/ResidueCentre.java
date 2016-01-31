package _rna_3d_viewer.model;

import javafx.geometry.Point3D;

/**
 * Created by heumos on 15.01.16.
 */
public class ResidueCentre {

    // the type of the residue
    private Integer residueNumber;

    // the 3D point of the centre of the given residue
    private Point3D centre;

    public ResidueCentre(Integer residueNumber, Point3D centre) {
        this.residueNumber = residueNumber;
        this.centre = centre;
    }

    public Integer getResidueNumber() {
        return residueNumber;
    }

    public void setResidueNumber(Integer residueNumber) {
        this.residueNumber = residueNumber;
    }

    public Point3D getCentre() {
        return centre;
    }

    public void setCentre(Point3D centre) {
        this.centre = centre;
    }
}
