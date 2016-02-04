package _rna_3d_viewer.model.structure;

import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by heumos on 04.02.16.
 */
public class SecStruct2DRepresentations {

    private List<SecStruct2DCircle> secStruct2DCircles;

    private List<SecStruct2DLine> secStruct2DLines;

    private HashMap<Integer, SecStruct2DCircle> secStruct2DCircleHashMap;

    public SecStruct2DRepresentations(List<SecStruct2DCircle> secStruct2DCircles,
                                      List<SecStruct2DLine> secStruct2DLines) {
        this.secStruct2DCircles = secStruct2DCircles;
        this.secStruct2DLines = secStruct2DLines;
    }


    public List<SecStruct2DCircle> getSecStruct2DCircles() {
        return secStruct2DCircles;
    }

    public void setSecStruct2DCircles(List<SecStruct2DCircle> secStruct2DCircles) {
        this.secStruct2DCircles = secStruct2DCircles;
    }

    public List<SecStruct2DLine> getSecStruct2DLines() {
        return secStruct2DLines;
    }

    public void setSecStruct2DLines(List<SecStruct2DLine> secStruct2DLines) {
        this.secStruct2DLines = secStruct2DLines;
    }

    public Shape[] get2DStructures() {
        List<Shape> _2DStructures = new ArrayList<>();
        for (SecStruct2DCircle secStruct2DCircle : this.secStruct2DCircles) {
            _2DStructures.add(secStruct2DCircle.getStructure());
        }
        for (SecStruct2DLine secStruct2DLine : this.secStruct2DLines) {
            _2DStructures.add(secStruct2DLine.getStructure());
        }
        Shape[] shapes = new Shape[_2DStructures.size()];
        shapes = _2DStructures.toArray(shapes);
        return shapes;
    }

    public ANucleotideStructure[] get2DStructureRepresentations() {
        ANucleotideStructure[] aSecStruct2Ds = new ANucleotideStructure[this.secStruct2DLines.size() + this.secStruct2DCircles.size()];
        List<ANucleotideStructure> aSecStruct2DsList = new ArrayList<>();
        aSecStruct2DsList.addAll(this.secStruct2DCircles);
        aSecStruct2DsList.addAll(this.secStruct2DLines);
        aSecStruct2Ds = aSecStruct2DsList.toArray(aSecStruct2Ds);
        return aSecStruct2Ds;
    }

    public int secStruct2DCirclesSize() {
        return this.secStruct2DCircles.size() - 1;
    }

    public SecStruct2DCircle getCircle2DStruct(int residueIndex) {
        SecStruct2DCircle secStruct2DCircle = this.secStruct2DCircleHashMap.get(residueIndex);
        return secStruct2DCircle;
    }

    public void createCircle2DMap() {
        this.secStruct2DCircleHashMap = new HashMap<>();
        for (SecStruct2DCircle secStruct2DCircle : this.secStruct2DCircles) {
            int residueIndex = secStruct2DCircle.getResidueIndex();
            this.secStruct2DCircleHashMap.put(residueIndex, secStruct2DCircle);
        }
    }

    public int secStruct2DLinesSize() {
        return this.secStruct2DLines.size() - 1;
    }
}
