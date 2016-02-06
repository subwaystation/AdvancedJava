package _rna_3d_viewer.model;

import _rna_3d_viewer.PseudoknotUtils;
import _rna_3d_viewer.io.PdbAtom;
import _rna_3d_viewer.io.PdbFileParser;
import _rna_3d_viewer.model.structure.Nucleotide3DStructures;
import _rna_3d_viewer.model.structure.SecondaryStructure;
import _rna_3d_viewer.model.structure_builder.HydrogenBond3DStructureBuilder;
import _rna_3d_viewer.model.structure_builder.Molecule3DConnectionBuilder;
import _rna_3d_viewer.model.structure_builder.Residue3DStructureBuilder;
import javafx.scene.shape.*;
import javafx.util.Pair;

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
    private List<MeshView> meshStructures = new ArrayList<>();

    // the resulting Nucleotide 3D Structures
    private Nucleotide3DStructures nucleotide3DStructures = new Nucleotide3DStructures();

    // the resulting list of connections to/from the sugar molecule
    private List<Cylinder> sugarConnectionList = new ArrayList<>();

    // the resulting list of phosphporus atoms
    private List<Sphere> phosphorusList = new ArrayList<>();

    // the resulting list of phosphorus connections
    private List<Cylinder> phosphorusConnections = new ArrayList<>();

    // the resulting list of hydrogen bonds
    private List<Cylinder> hydrogenBonds = new ArrayList<>();

    // the list of nucleotide pairs that pair together in the secondary structure
    private List<Pair<Integer, Integer>> nucleotidePairs = new ArrayList<>();

    private StringBuilder sequenceBuilder = new StringBuilder();

    private StringBuilder dotBracketNotationBuilder = new StringBuilder();

    // the pseudo knot free nucleotides
    List<Pair<Integer, Integer>> adjustedNucleotidePairsList = new ArrayList<>();

    public Rna3DViewerModel() throws IOException {

    }

    public void parsePDB(String pdbFile) throws IOException {
        this.residues.clear();
        this.pdbFile = pdbFile;
        PdbFileParser pdbFileParser = new PdbFileParser(this.pdbFile);
        createResidueList(pdbFileParser);
    }

    public void build3DStructures() {
        this.meshStructures.clear();
        this.nucleotide3DStructures.clear();
        this.sugarConnectionList.clear();
        this.phosphorusList.clear();
        this.getPhosphorusConnections().clear();
        this.hydrogenBonds.clear();
        this.nucleotidePairs.clear();
        this.sequenceBuilder.setLength(0);
        this.dotBracketNotationBuilder.setLength(0);

        Residue3DStructureBuilder residue3DStructureBuilder = new Residue3DStructureBuilder();
        Molecule3DConnectionBuilder molecule3DConnectionBuilder = new Molecule3DConnectionBuilder(0.1);

        Integer residueNumberOld = 0;
        float[] oldPhosphorusCoords = new float[0];

        for (Residue residue : this.residues) {
            residue3DStructureBuilder.setResidue(residue);

            // build sugar molecule
            this.meshStructures.add(residue3DStructureBuilder.buildSugar3DStructure());

            // build nucleotide
            this.nucleotide3DStructures.addStructure(residue3DStructureBuilder.buildNucleotide3DStructure());

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
                            (!(phosphorusCoordinates[0] == 0.0) && !(phosphorusCoordinates[1] == 0.0) &&
                                    !(phosphorusCoordinates[2] == 0.0))) {
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
            this.sequenceBuilder.append(res1.getResidueType().charAt(0));
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
                                this.nucleotidePairs.add(new Pair<>(i + 1, j + 1));
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
                                this.nucleotidePairs.add(new Pair<>(i + 1, j + 1));
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
                                this.nucleotidePairs.add(new Pair<>(i + 1, j + 1));
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
                                this.nucleotidePairs.add(new Pair<>(i + 1, j + 1));
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

        }
        buildSecondaryStructure();
    }

    private void buildSecondaryStructure() {
        HashSet<Pair<Integer, Integer>> adjustedNucleotidePairs =
                new HashSet<>(PseudoknotUtils.adjustPseudoknots(new ArrayList<>(this.nucleotidePairs)));
        this.adjustedNucleotidePairsList = new ArrayList<>(adjustedNucleotidePairs);

        for (int i = 0; i < this.residues.size(); i++) {
            dotBracketNotationBuilder.append(".");
        }

        // add nucleotide pairs
        for(Pair<Integer, Integer> entry : adjustedNucleotidePairs){
            dotBracketNotationBuilder.setCharAt(entry.getKey() - 1, '(');
            dotBracketNotationBuilder.setCharAt(entry.getValue() - 1, ')');
        }

        HashSet<Pair> pseudoKnots = new HashSet<>(this.nucleotidePairs);
        pseudoKnots.removeAll(adjustedNucleotidePairs);

        // add adjusted pseudoknots
        for(Pair<Integer, Integer> entry : pseudoKnots){
            dotBracketNotationBuilder.setCharAt(entry.getKey() - 1, '[');
            dotBracketNotationBuilder.setCharAt(entry.getValue() - 1, ']');
        }
        // FIXME
        System.out.println(this.sequenceBuilder.toString());
        System.out.println(this.dotBracketNotationBuilder.toString());
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

    private void createResidueList(PdbFileParser pdbFileParser) throws IOException {
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

    public List<MeshView> getMeshStructures() {
        return meshStructures;
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

    public Nucleotide3DStructures getNucleotide3DStructures() {
        return this.nucleotide3DStructures;
    }

    public String getDotBracketNotation() {
        return this.dotBracketNotationBuilder.toString();
    }

    public SecondaryStructure getSecondaryStructure() {
        return new SecondaryStructure(this.sequenceBuilder.toString(), this.nucleotidePairs, this.getDotBracketNotation());
    }

    public List<Residue> getResidues() {
        return residues;
    }

    public List<Pair<Integer, Integer>> getPseudoKnotsFreeList() { return this.adjustedNucleotidePairsList; }
}
