package archive;

import model.AlignmentModel;

public class CommandLine {

    public static void main(String[] args) {
        AlignmentModel alignmentModel = new AlignmentModel(args);
        alignmentModel.setIdentifierVisibilty(true);
        alignmentModel.setSequenceVisibility(true);
        alignmentModel.setNumberingVisibility(true);
        System.out.println(alignmentModel.getStringRepresentation());
    }

}