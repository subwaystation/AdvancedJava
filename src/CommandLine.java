import model.AlignmentModel;

public class CommandLine {

    public static void main(String[] args) {
        AlignmentModel alignmentModel = new AlignmentModel(args);

        System.out.println(alignmentModel.getStringRepresentation());
    }

}