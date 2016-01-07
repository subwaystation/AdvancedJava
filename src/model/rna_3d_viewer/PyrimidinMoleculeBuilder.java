package model.rna_3d_viewer;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Created by heumos on 05.01.16.
 */
public class PyrimidinMoleculeBuilder {

    // the coordinates
    private float[] coordinates;

    // the mesh material
    protected PhongMaterial material = new PhongMaterial(Color.RED);

    private float[] texCoords = new float[]
            {
                    0, 1, // 1st triangle point
                    0.5f, 0.5f, // 2nd triangle point
                    0.5f, 1, // 3rd triangle point
            };

    private int[] faces = new int[]
            {
                    // N1, C2, C4
                    0,0,1,1,3,2,
                    0,0,3,2,1,1,
                    // N1, C4, C5
                    0,0,3,1,4,2,
                    0,0,4,2,3,1,
                    // C2, N3, C4
                    1,0,2,1,3,2,
                    1,0,3,2,2,1,
                    // N1, C5, C6
                    0,0,4,1,5,1,
                    0,0,5,1,4,1
            };


    public PyrimidinMoleculeBuilder() {

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
