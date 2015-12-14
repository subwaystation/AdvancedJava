package model.rna_3d_viewer;

import io.PdbFileParser;
import javafx.scene.shape.MeshView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heumos on 14.12.15.
 */
public class Rna3DViewerModel {

    private double mouseX;

    private double mouseY;

    // the path to the PDB file
    private String pdbFile;

    // the list of atoms stored in a HashMap!
    private HashMap<Integer, List<PdbAtom>> atomHashMap;

    // the resulting list of meshes
    private List<MeshView> meshViewList = new ArrayList<>();

    public Rna3DViewerModel(String pdbFile) throws IOException {
        this.pdbFile = pdbFile;
        PdbFileParser pdbFileParser = new PdbFileParser(this.pdbFile);
        fillAtomHashMap(pdbFileParser);
        // TODO validate atom integrity for every sugar/base combination
        SugarMoleculeBuilder sugarMoleculeBuilder = new SugarMoleculeBuilder();
        for (Map.Entry<Integer, List<PdbAtom>> listEntry : this.atomHashMap.entrySet()) {
            sugarMoleculeBuilder.setCoordinates(SugarCoordinateExtractor.extractSugarCoordinates(listEntry.getValue()));
            this.meshViewList.add(sugarMoleculeBuilder.generateMeshView());
        }

    }

    private void fillAtomHashMap(PdbFileParser pdbFileParser) throws IOException {
        List<PdbAtom> atoms = pdbFileParser.parsePdb();
        HashMap<Integer, List<PdbAtom>> atomHashMap = new HashMap<>();
        for (PdbAtom atom : atoms) {
            Integer residueIndex = atom.getResidueIndex();
            if (atomHashMap.get(residueIndex) != null) {
                List<PdbAtom> atomList = atomHashMap.get(residueIndex);
                atomList.add(atom);
            } else {
                List<PdbAtom> atomList = new ArrayList<>();
                atomList.add(atom);
                atomHashMap.put(residueIndex, atomList);
            }
        }
        this.atomHashMap = atomHashMap;
    }

    public HashMap<Integer, List<PdbAtom>> getAtomHashMap() {
        return atomHashMap;
    }

    public List<MeshView> getMeshViewList() {
        return meshViewList;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }
}
