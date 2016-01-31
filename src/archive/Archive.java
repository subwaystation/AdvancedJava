package archive;

/**
 * Created by heumos on 31.01.16.
 */
public class Archive {

/*    String residueType = listEntry.getValue().get(0).getResidueType();
    float[] bondCoordinates;
    // build base molecules
    if (residueType.startsWith("A") || residueType.startsWith("G")) {
        // build purine
        purine3DStructureBuilder.setCoordinates(PurineCoordinateExtractor.extractPurineCoordinates(listEntry.getValue()));
        this.meshViewList.add(purine3DStructureBuilder.generateMeshView(residueType, listEntry.getKey()));
        bondCoordinates = CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(listEntry.getValue(), true);
    } else {
        // build pyrimidin
        pyrimidine3DStructureBuilder.setCoordinates(PyrimidineCoordinateExtractor.
                extractPyrimidineCoordinates(listEntry.getValue()));
        this.meshViewList.add(pyrimidine3DStructureBuilder.generateMeshView(residueType, listEntry.getKey()));
        bondCoordinates = CovalentBondCoordinateExtractor.extractCovalentBondCoordinates(listEntry.getValue(), false);
    }
    molecule3DConnectionBuilder.setPoints(bondCoordinates);
    sugarConnectionList.add(molecule3DConnectionBuilder.createConnection());
    // build phosphorus spheres
    float[] phosphorusCoordinates = PhosphorusMoleculeExtractor.extractPhosphorusCoordinates(listEntry.getValue());
    // was there a phosphorus atom?
    if (phosphorusCoordinates[0] == 0.0 && phosphorusCoordinates[1] == 0.0 && phosphorusCoordinates[2] == 0.0) {
        continue;
    }
    phosphorus3DStructureBuilder.setCoordinates(phosphorusCoordinates);
    this.phosphorusList.add(phosphorus3DStructureBuilder.generatePhosphate());

    // build connections between phosphorus and sugars
    float[] phosSugarConnCoords = buildPhosphorusSugarCoords(c4DashCoordinates, phosphorusCoordinates);
    molecule3DConnectionBuilder.setPoints(phosSugarConnCoords);
    sugarConnectionList.add(molecule3DConnectionBuilder.createConnection());*/
}
