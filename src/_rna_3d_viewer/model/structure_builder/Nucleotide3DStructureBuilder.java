package _rna_3d_viewer.model.structure_builder;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Created by heumos on 22.01.16.
 */
public abstract class Nucleotide3DStructureBuilder {

    // the coordinates for the whole molecule
    private float[] coordinates;

    // the color for the resulting molecule
    private Color color;

    public Nucleotide3DStructureBuilder(Color color) {
        this.color = color;
    }

    /**
     * Main method, returns a 3D representative view.
     * @param residueType
     * @param residueNumber
     * @return
     */
    public MeshView generateMeshView(String residueType, Integer residueNumber) {
        MeshView meshView = new MeshView();
        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().addAll(this.getCoordinates());
        triangleMesh.getTexCoords().addAll(this.getTexCoords());
        triangleMesh.getFaces().addAll(this.getFaces());
        meshView.setMesh(triangleMesh);
        meshView.setMaterial(new PhongMaterial(this.getColor()));
        String tooltipS = residueType + Integer.toString(residueNumber);
        Tooltip tooltip = new Tooltip(tooltipS);
        Tooltip.install(meshView, tooltip);
        return meshView;
    }

    protected abstract float[] getTexCoords();

    protected abstract int[] getFaces();

    public float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
