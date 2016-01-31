package _rna_3d_viewer.model;

import io.PdbFileParser;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;

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
    // TODO delete
    // private HashMap<Integer, List<PdbAtom>> atomHashMap = new HashMap<>();

    private List<Residue> residues = new ArrayList<>();

    // the resulting list of meshes
    private List<MeshView> meshViewList = new ArrayList<>();

    // the resulting list of connections to/from the sugar molecule
    private List<Cylinder> sugarConnectionList = new ArrayList<>();

    // the resulting list of phosphporus atoms
    private List<Sphere> phosphorusList = new ArrayList<>();

    // the resulting list of phosphorus connections
    private List<Cylinder> phosphorusConnections = new ArrayList<>();

    // the resulting list of hydrogen bonds
    private List<Cylinder> hydrogenBonds = new ArrayList<>();

    public Rna3DViewerModel() throws IOException {

    }

    public void parsePDB(String pdbFile) throws IOException {
        this.pdbFile = pdbFile;
        PdbFileParser pdbFileParser = new PdbFileParser(this.pdbFile);
        buildResidueList(pdbFileParser);
    }

    public void build3DStructures() {
        this.meshViewList.clear();
        this.sugarConnectionList.clear();
        this.phosphorusList.clear();

        Residue3DStructureBuilder residue3DStructureBuilder = new Residue3DStructureBuilder();
        Molecule3DConnectionBuilder molecule3DConnectionBuilder = new Molecule3DConnectionBuilder(0.1);

        Integer residueNumberOld = 0;
        float[] oldPhosphorusCoords = new float[0];
        List<ResidueCentre> residueCentreList = new ArrayList<>();

        for (Residue residue : this.residues) {
            residue3DStructureBuilder.setResidue(residue);

            // build sugar molecule
            this.meshViewList.add(residue3DStructureBuilder.buildSugar3DStructure());

            // build nucleotide
            this.meshViewList.add(residue3DStructureBuilder.buildNucleotide3DStructure());

            // connect nucleotide with its sugar
            this.sugarConnectionList.add(residue3DStructureBuilder.buildNucleotideSugarConnection3DStructure());

            // build phosphorus spheres
            this.phosphorusList.add(residue3DStructureBuilder.buildPhosphorus3DStructure());

            // build phosphorus sugar connections
            Cylinder phosphorusSugarConnection = residue3DStructureBuilder.buildSugarPhosphorusConnection3DStructure();
            if (phosphorusSugarConnection != null) {
                this.sugarConnectionList.add(phosphorusSugarConnection);
            }

            // build phosphorus spheres
            float[] phosphorusCoordinates = residue3DStructureBuilder.getPhosphorusCoords();

            // was there a phosphorus atom?
            if (phosphorusCoordinates == null) {
                continue;
            }
            if (phosphorusCoordinates[0] == 0.0 && phosphorusCoordinates[1] == 0.0 && phosphorusCoordinates[2] == 0.0) {
                continue;
            }

            // connect phosphorus atoms
            if (residueNumberOld == 0) {
                residueNumberOld = residue.getResidueIndex();
                oldPhosphorusCoords = phosphorusCoordinates;
            } else {
                if (residueNumberOld - residue.getResidueIndex() == -1) {
                    if (phosphorusCoordinates != null) {
                        molecule3DConnectionBuilder.setInitPoint(phosphorusCoordinates);
                        molecule3DConnectionBuilder.setEndPoint(oldPhosphorusCoords);
                        this.phosphorusConnections.add(molecule3DConnectionBuilder.createConnection());
                    }
                }
                residueNumberOld = residue.getResidueIndex();
                oldPhosphorusCoords = phosphorusCoordinates;
            }

        }
        // build hydrogen bonds and extract nucleotide pairs
        buildHydrogenBonds();
    }

    private void buildHydrogenBonds() {
        Set<Residue> encounteredResidues = new HashSet<>();
        HydrogenBond3DStructureBuilder hydrogenBond3DStructureBuilder = new HydrogenBond3DStructureBuilder();
        for (int i = 0; i < this.residues.size(); i++) {
            for (int j = 0; j < this.residues.size(); j++) {
                if (i == j) {
                    continue;
                }

                String nucleotide1 = this.residues.get(i).getResidueType();
                String nucleotide2 = this.residues.get(j).getResidueType();
                switch (nucleotide1) {
                    case "U":
                    case "URA":
                        if (nucleotide2.startsWith("A")) {
                            // TODO
                        }
                        break;
                    case "A":
                    case "ADE":
                        if (nucleotide2.startsWith("U")) {
                            // TODO
                        }
                        break;
                    case "G":
                    case "GUA":
                        if (nucleotide2.startsWith("C")) {
                            // TODO
                        }
                        break;
                    case "C":
                    case "CYT":
                        if (nucleotide2.startsWith("G")) {
                            // TODO
                        }
                        break;
                    default:
                        break;
                }
            }

        }

    }
/*
    private void connectBasePairs(Molecule3DConnectionBuilder molecule3DConnectionBuilder, List<ResidueCentre> residueCentreList) {
        for (int i = 0; i < residueCentreList.size(); i++) {
            for (int j = 0; j < residueCentreList.size(); j++) {
                if (i == j) {
                    continue;
                } else {
                    ResidueCentre res1 = residueCentreList.get(i);
                    Integer res1Number = res1.getResidueNumber();
                    String base1 = this.atomHashMap.get(res1Number).get(0).getResidueType();
                    ResidueCentre res2 = residueCentreList.get(j);
                    Integer res2Number =res2.getResidueNumber();
                    String base2 = this.atomHashMap.get(res2Number).get(0).getResidueType();
                    // TODO cases ADE/GUA/CYT/URA
                    switch (base1) {
                        case "U":
                            if (base2.equals("A")) {
                                basePairRange(molecule3DConnectionBuilder, res1, res2);
                            }
                            break;
                        case "C":
                            if (base2.equals("G")) {
                                basePairRange(molecule3DConnectionBuilder, res1, res2);
                            }
                            break;
                        case "A":
                            if (base2.equals("U")) {
                                basePairRange(molecule3DConnectionBuilder, res1, res2);
                            }
                            break;
                        case "G":
                            if (base2.equals("C")) {
                                basePairRange(molecule3DConnectionBuilder, res1, res2);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }*/

    private void basePairRange(Molecule3DConnectionBuilder molecule3DConnectionBuilder, ResidueCentre res1, ResidueCentre res2) {
        Double dist = res1.getCentre().distance(res2.getCentre());
        // we are within the required range
        // TODO take angle into account
        if (dist <= 5) {
            molecule3DConnectionBuilder.setInitPoint(res1.getCentre());
            molecule3DConnectionBuilder.setEndPoint(res2.getCentre());
            sugarConnectionList.add(molecule3DConnectionBuilder.createConnection());
        }
    }

    private float[] buildPhosphorusSugarCoords(float[] c4DashCoordinates, float[] phosphorusCoordinates) {
        float[] coords = new float[6];
        coords[0] = c4DashCoordinates[0];
        coords[1] = c4DashCoordinates[1];
        coords[2] = c4DashCoordinates[2];
        coords[3] = phosphorusCoordinates[0];
        coords[4] = phosphorusCoordinates[1];
        coords[5] = phosphorusCoordinates[2];
        return coords;
    }

    private float[] extractC4Coodinates(float[] sugarCoordinates) {
        float[] c4DashCoordinates = new float[3];
        c4DashCoordinates[0] = sugarCoordinates[9];
        c4DashCoordinates[1] = sugarCoordinates[10];
        c4DashCoordinates[2] = sugarCoordinates[11];
        return c4DashCoordinates;
    }

    public List<Cylinder> getSugarConnectionList() {
        return sugarConnectionList;
    }

    public String getPdbFile() {
        return pdbFile;
    }

    public List<Sphere> getPhosphorusList() {
        return phosphorusList;
    }

    private void buildResidueList(PdbFileParser pdbFileParser) throws IOException {
        List<PdbAtom> atoms = pdbFileParser.parsePdb();
        // center all atom positions
        centerAtomPositions(atoms);
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
        TreeMap<Integer, List<PdbAtom>> sortedAtomHashMap = new TreeMap<>(atomHashMap);
        for (Map.Entry<Integer, List<PdbAtom>> entry : sortedAtomHashMap.entrySet()) {
            this.residues.add(new Residue(entry.getValue()));
        }
    }

    private void centerAtomPositions(List<PdbAtom> atoms) {
        int atomCount = 0;

        float Xcoords = 0f;
        float Ycoords = 0f;
        float Zcoords = 0f;

        for (PdbAtom atom : atoms) {
            float[] coords = atom.getCoordinates();
            Xcoords += coords[0];
            Ycoords += coords[1];
            Zcoords += coords[2];
            atomCount++;
        }
        Xcoords = Xcoords / atomCount;
        Ycoords = Ycoords / atomCount;
        Zcoords = Zcoords / atomCount;

        for (PdbAtom atom : atoms) {
            float[] coords = atom.getCoordinates();
            coords[0] = coords[0] - Xcoords;
            coords[1] = coords[1] - Ycoords;
            coords[2] = coords[2] - Zcoords;
        }
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

    public List<Cylinder> getPhosphorusConnections() {
        return phosphorusConnections;
    }

    public List<Cylinder> getHydrogenBonds() {
        return hydrogenBonds;
    }
}
