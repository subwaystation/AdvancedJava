package _rna_3d_viewer.model.coordinate_extractor;

import _rna_3d_viewer.io.PdbAtom;

import java.util.List;

/**
 * Created by heumos on 08.01.16.
 */
public class PhosphorusMoleculeExtractor {

    public static float[] extractPhosphorusCoordinates(List<PdbAtom> pdbAtomList) {
        float[] coordinates = new float[3];
        for (PdbAtom atom : pdbAtomList) {
            String atomName = atom.getAtomName();

            if (atomName.equals("P")) {
                System.arraycopy(atom.getCoordinates(), 0, coordinates, 0, 3);
            }
        }
        return coordinates;
    }
}
