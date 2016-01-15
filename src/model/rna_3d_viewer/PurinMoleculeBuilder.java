package model.rna_3d_viewer;

import javafx.geometry.Point3D;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Created by heumos on 05.01.16.
 */
public class PurinMoleculeBuilder {

    // the coordinates
    private float[] coordinates;

    // the mesh material
    protected PhongMaterial material = new PhongMaterial(Color.GREEN);

    //

    private float[] texCoords = new float[]
            {
                    0, 1, // 1st triangle point
                    0.5f, 0.5f, // 2nd triangle point
                    0.5f, 1, // 3rd triangle point
            };

    private int[] faces = new int[]
            {
                    // C4, C8, N9
                    3,0,7,1,8,2,
                    3,0,8,2,7,1,
                    // C4, C5, C8
                    3,0,4,1,7,2,
                    3,0,7,2,4,1,
                    // C5, N7, C8
                    4,0,6,1,7,2,
                    4,0,7,2,6,1,

                    // N1, C2, C5
                    0,0,1,1,4,2,
                    0,0,4,2,1,1,
                    // N1, C5, C6
                    0,0,4,1,5,2,
                    0,0,5,2,4,1,
                    // C2, N3, C4
                    1,0,2,1,3,2,
                    1,0,3,2,2,1,
                    // C2, C4, C5
                    1,0,3,1,4,2,
                    1,0,4,2,3,1
            };


    public PurinMoleculeBuilder() {

    }

    /**
     * The centre of the molecule is defined as the centre point
     * being N1.
     * @return
     */
    public Point3D getMoleculeCentre() {
        if (this.coordinates != null) {
            return new Point3D(this.coordinates[0], this.coordinates[1], this.coordinates[2]);
        } else {
            return null;
        }
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public MeshView generateMeshView(String residueType, Integer residueNumber) {
        MeshView meshView = new MeshView();
        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().addAll(this.coordinates);
        triangleMesh.getTexCoords().addAll(this.texCoords);
        triangleMesh.getFaces().addAll(this.faces);
        meshView.setMesh(triangleMesh);
        meshView.setMaterial(this.material);
        String tooltipS = residueType + Integer.toString(residueNumber);
        Tooltip tooltip = new Tooltip(tooltipS);
        Tooltip.install(meshView, tooltip);
        return meshView;
    }
}
