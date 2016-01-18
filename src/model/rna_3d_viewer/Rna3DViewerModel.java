package model.rna_3d_viewer;

import io.PdbFileParser;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
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
    private HashMap<Integer, List<PdbAtom>> atomHashMap = new HashMap<>();

    // the resulting list of meshes
    private List<MeshView> meshViewList = new ArrayList<>();

    // the resulting list of connections
    private List<Cylinder> connectionList = new ArrayList<>();

    // the resulting list of phosphporus atoms
    private List<Sphere> phosphorusList = new ArrayList<>();

    public Rna3DViewerModel() throws IOException {

    }

    public void parsePDB(String pdbFile) throws IOException {
        this.pdbFile = pdbFile;
        PdbFileParser pdbFileParser = new PdbFileParser(this.pdbFile);
        fillAtomHashMap(pdbFileParser);
    }

    public void createMolecules() {
        this.meshViewList.clear();
        this.connectionList.clear();
        this.phosphorusList.clear();

        SugarMoleculeBuilder sugarMoleculeBuilder = new SugarMoleculeBuilder();
        PurinMoleculeBuilder purinMoleculeBuilder = new PurinMoleculeBuilder();
        PyrimidinMoleculeBuilder pyrimidinMoleculeBuilder = new PyrimidinMoleculeBuilder();
        MoleculeConnectionBuilder moleculeConnectionBuilder = new MoleculeConnectionBuilder(0.05);
        PhosphorusMoleculeBuilder phosphorusMoleculeBuilder = new PhosphorusMoleculeBuilder();

        TreeMap<Integer, List<PdbAtom>> sortedAtomHashMap = new TreeMap<>(this.atomHashMap);

        Integer residueNumberOld = 0;
        float[] oldPhosphorusCoords = new float[0];
        List<ResidueCentre> residueCentreList = new ArrayList<>();

        for (Map.Entry<Integer, List<PdbAtom>> listEntry : sortedAtomHashMap.entrySet()) {
            // build sugar molecules
            float[] sugarCoordinates = SugarCoordinateExtractor.extractSugarCoordinates(listEntry.getValue());
            sugarMoleculeBuilder.setCoordinates(sugarCoordinates);
            this.meshViewList.add(sugarMoleculeBuilder.generateMeshView());
            float[] c4DashCoordinates = extractC4Coodinates(sugarCoordinates);

            String residueType = listEntry.getValue().get(0).getResidueType();
            float[] bondCoordinates;
            // build base molecules
            if (residueType.startsWith("A") || residueType.startsWith("G")) {
                // build purine
                purinMoleculeBuilder.setCoordinates(PurinCoordinateExtractor.extractPurinCoordinates(listEntry.getValue()));
                this.meshViewList.add(purinMoleculeBuilder.generateMeshView(residueType, listEntry.getKey()));
                Point3D centrePoint;
                if ((centrePoint = purinMoleculeBuilder.getMoleculeCentre()) != null) {
                    residueCentreList.add(new ResidueCentre(listEntry.getKey(), centrePoint));
                }
                bondCoordinates = CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(listEntry.getValue(), true);
            } else {
                // build pyrimidin
                pyrimidinMoleculeBuilder.setCoordinates(PyrimidinCoordinateExtractor.
                        extractPyrimidinCoordinates(listEntry.getValue()));
                this.meshViewList.add(pyrimidinMoleculeBuilder.generateMeshView(residueType, listEntry.getKey()));
                Point3D centrePoint;
                if ((centrePoint = pyrimidinMoleculeBuilder.getMoleculeCentre()) != null) {
                    residueCentreList.add(new ResidueCentre(listEntry.getKey(), centrePoint));
                }
                bondCoordinates = CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(listEntry.getValue(), false);
            }
            moleculeConnectionBuilder.setPoints(bondCoordinates);
            connectionList.add(moleculeConnectionBuilder.createBond());
            // build phosphorus spheres
            float[] phosphorusCoordinates = PhosphorusMoleculeExtractor.extractPhosphorusCoordinates(listEntry.getValue());
            phosphorusMoleculeBuilder.setCoordinates(phosphorusCoordinates);
            this.phosphorusList.add(phosphorusMoleculeBuilder.generatePhosphate());

            // build connections between phosphorus and sugars
            float[] phosSugarConnCoords = buildPhosphorusSugarCoords(c4DashCoordinates, phosphorusCoordinates);
            moleculeConnectionBuilder.setPoints(phosSugarConnCoords);
            connectionList.add(moleculeConnectionBuilder.createBond());

            // connect phosphorus atoms
            if (residueNumberOld == 0) {
                residueNumberOld = listEntry.getKey();
                oldPhosphorusCoords = phosphorusCoordinates;
            } else {
                if (residueNumberOld - listEntry.getKey() == -1) {
                    moleculeConnectionBuilder.setInitPoint(phosphorusCoordinates);
                    moleculeConnectionBuilder.setEndPoint(oldPhosphorusCoords);
                    connectionList.add(moleculeConnectionBuilder.createBond());
                }
                residueNumberOld = listEntry.getKey();
                oldPhosphorusCoords = phosphorusCoordinates;
            }

            // connect purine with pyrimidine if distance of molecules is within 10 Ângström range
            // FIXME no knew object generating?!
            // moleculeConnectionBuilder = new MoleculeConnectionBuilder(0.1);
            // moleculeConnectionBuilder.setColor(Color.BLUE);
            // connectBasePairs(moleculeConnectionBuilder, residueCentreList);
        }
    }

    private void connectBasePairs(MoleculeConnectionBuilder moleculeConnectionBuilder, List<ResidueCentre> residueCentreList) {
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

                    switch (base1) {
                        case "U":
                            if (base2.equals("A")) {
                                basePairRange(moleculeConnectionBuilder, res1, res2);
                            }
                            break;
                        case "C":
                            if (base2.equals("G")) {
                                basePairRange(moleculeConnectionBuilder, res1, res2);
                            }
                            break;
                        case "A":
                            if (base2.equals("U")) {
                                basePairRange(moleculeConnectionBuilder, res1, res2);
                            }
                            break;
                        case "G":
                            if (base2.equals("C")) {
                                basePairRange(moleculeConnectionBuilder, res1, res2);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void basePairRange(MoleculeConnectionBuilder moleculeConnectionBuilder, ResidueCentre res1, ResidueCentre res2) {
        Double dist = res1.getCentre().distance(res2.getCentre());
        // we are within the required range
        if (dist <= 10) {
            moleculeConnectionBuilder.setInitPoint(res1.getCentre());
            moleculeConnectionBuilder.setEndPoint(res2.getCentre());
            connectionList.add(moleculeConnectionBuilder.createBond());
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

    public List<Cylinder> getConnectionList() {
        return connectionList;
    }

    public String getPdbFile() {
        return pdbFile;
    }

    public List<Sphere> getPhosphorusList() {
        return phosphorusList;
    }

    private void fillAtomHashMap(PdbFileParser pdbFileParser) throws IOException {
        this.atomHashMap.clear();
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

        this.atomHashMap = atomHashMap;
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
