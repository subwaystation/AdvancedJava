package _rna_3d_viewer.model;

import _rna_3d_viewer.io.PdbFileParser;
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
                    if (phosphorusCoordinates != null &&
                            (phosphorusCoordinates[0] == 0.0 && phosphorusCoordinates[1] == 0.0 &&
                                    phosphorusCoordinates[2] == 0.0)) {
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
        Set<Integer> encounteredResidues = new HashSet<>();
        HydrogenBond3DStructureBuilder hydrogenBond3DStructureBuilder = new HydrogenBond3DStructureBuilder();
        for (int i = 0; i < this.residues.size(); i++) {
            Residue res1 = this.residues.get(i);
            if (encounteredResidues.contains(res1.getResidueIndex())) {
                continue;
            }
            for (int j = 0; j < this.residues.size(); j++) {
                if (i == j) {
                    continue;
                }
                Residue res2 = this.residues.get(j);

                if (encounteredResidues.contains(res2.getResidueIndex())) {
                    continue;
                }
                if (encounteredResidues.contains(res1.getResidueIndex())) {
                    continue;
                }
                if (res1.getResidueIndex() - res2.getResidueIndex() == 1) {
                    continue;
                }

                String nucleotide1 = res1.getResidueType();
                String nucleotide2 = res2.getResidueType();
                switch (nucleotide1) {
                    case "U":
                    case "URA":
                        if (nucleotide2.startsWith("A")) {
                            hydrogenBond3DStructureBuilder.setResidue1(res2);
                            hydrogenBond3DStructureBuilder.setResidue2(res1);
                            if (hydrogenBond3DStructureBuilder.buildAdeUraHydrogenBond()) {
                                this.hydrogenBonds.addAll(hydrogenBond3DStructureBuilder.getHydrogenBonds3DStructure());
                                encounteredResidues.add(res1.getResidueIndex());
                                encounteredResidues.add(res2.getResidueIndex());
                            }
                        }
                        break;
                    case "A":
                    case "ADE":
                        if (nucleotide2.startsWith("U")) {
                            hydrogenBond3DStructureBuilder.setResidue2(res2);
                            hydrogenBond3DStructureBuilder.setResidue1(res1);
                            if (hydrogenBond3DStructureBuilder.buildAdeUraHydrogenBond()) {
                                this.hydrogenBonds.addAll(hydrogenBond3DStructureBuilder.getHydrogenBonds3DStructure());
                                encounteredResidues.add(res1.getResidueIndex());
                                encounteredResidues.add(res2.getResidueIndex());
                            }
                        }
                        break;
                    case "G":
                    case "GUA":
                        if (nucleotide2.startsWith("C")) {
                            hydrogenBond3DStructureBuilder.setResidue1(res1);
                            hydrogenBond3DStructureBuilder.setResidue2(res2);
                            if (hydrogenBond3DStructureBuilder.buildGuaCytHydrogenBond()) {
                                this.hydrogenBonds.addAll(hydrogenBond3DStructureBuilder.getHydrogenBonds3DStructure());
                                encounteredResidues.add(res1.getResidueIndex());
                                encounteredResidues.add(res2.getResidueIndex());
                            }
                        }
                        break;
                    case "C":
                    case "CYT":
                        if (nucleotide2.startsWith("G")) {
                            hydrogenBond3DStructureBuilder.setResidue1(res2);
                            hydrogenBond3DStructureBuilder.setResidue2(res1);
                            if (hydrogenBond3DStructureBuilder.buildGuaCytHydrogenBond()) {
                                this.hydrogenBonds.addAll(hydrogenBond3DStructureBuilder.getHydrogenBonds3DStructure());
                                encounteredResidues.add(res1.getResidueIndex());
                                encounteredResidues.add(res2.getResidueIndex());
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

        }

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
