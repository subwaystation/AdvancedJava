package archive;

import _rna_3d_viewer.io.PdbFileParser;
import _rna_3d_viewer.io.PdbAtom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heumos on 09.11.15.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        List<StringBuilder> stringBuilders = new ArrayList<>();
        stringBuilders.add(new StringBuilder().append("ABC").append(234));
        stringBuilders.add(new StringBuilder().append("GGG").append(676));

        List<StringBuilder> stringBuilders1 = new ArrayList<>();
        stringBuilders1.addAll(stringBuilders);

        System.out.println(stringBuilders);
        System.out.println(stringBuilders1);
        System.out.println();
        stringBuilders.get(0).setLength(0);
        System.out.println(stringBuilders);
        System.out.println(stringBuilders1);

    }
}
