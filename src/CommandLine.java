import model.RnaSeqsModel;

public class CommandLine {

    public static void main(String[] args) {
        RnaSeqsModel rnaSeqsModel = new RnaSeqsModel(args);
        System.out.println(rnaSeqsModel.getFullStringRepresentation());
    }

}