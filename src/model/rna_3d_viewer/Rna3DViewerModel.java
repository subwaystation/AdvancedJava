package model.rna_3d_viewer;

import io.PdbFileParser;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;

import java.io.IOException;
import java.util.*;

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

    // the resulting list of connections
    private List<Cylinder> connectionList = new ArrayList<>();

    public Rna3DViewerModel(String pdbFile) throws IOException {
        this.pdbFile = pdbFile;
        PdbFileParser pdbFileParser = new PdbFileParser(this.pdbFile);
        fillAtomHashMap(pdbFileParser);
        // TODO validate atom integrity for every sugar/base combination
        SugarMoleculeBuilder sugarMoleculeBuilder = new SugarMoleculeBuilder();
        PurinMoleculeBuilder purinMoleculeBuilder = new PurinMoleculeBuilder();
        PyrimidinMoleculeBuilder pyrimidinMoleculeBuilder = new PyrimidinMoleculeBuilder();
        SugarBaseConnectionBuilder sugarBaseConnectionBuilder = new SugarBaseConnectionBuilder(0.05);

        for (Map.Entry<Integer, List<PdbAtom>> listEntry : this.atomHashMap.entrySet()) {
            // build sugar molecules
            sugarMoleculeBuilder.setCoordinates(SugarCoordinateExtractor.extractSugarCoordinates(listEntry.getValue()));
            this.meshViewList.add(sugarMoleculeBuilder.generateMeshView());
            String residueType = listEntry.getValue().get(0).getResidueType();
            float[] bondCoordinates;
            // build base molecules
            if (residueType.startsWith("A") || residueType.startsWith("G")) {
                // build purine
                purinMoleculeBuilder.setCoordinates(PurinCoordinateExtractor.extractPurinCoordinates(listEntry.getValue()));
                this.meshViewList.add(purinMoleculeBuilder.generateMeshView());
                bondCoordinates = CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(listEntry.getValue(), true);
                System.out.println(Arrays.toString(bondCoordinates));
            } else {
                // build pyrimidin
                pyrimidinMoleculeBuilder.setCoordinates(PyrimidinCoordinateExtractor.
                        extractPyrimidinCoordinates(listEntry.getValue()));
                this.meshViewList.add(pyrimidinMoleculeBuilder.generateMeshView());
                bondCoordinates = CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(listEntry.getValue(), false);
                System.out.println(bondCoordinates);
            }
            sugarBaseConnectionBuilder.setPoints(bondCoordinates);
            connectionList.add(sugarBaseConnectionBuilder.createBond());
        }

    }

    public List<Cylinder> getConnectionList() {
        return connectionList;
    }

    private void fillAtomHashMap(PdbFileParser pdbFileParser) throws IOException {
        List<PdbAtom> atoms = pdbFileParser.parsePdb();
        // TODO center all atom positions
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
