import entity.ClassEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            System.out.println("Please provide the path to the project directory");
            return;
        }
        if(!args[0].isEmpty()){
            File inputFile = new File(args[0]);
            if(!inputFile.exists() || !inputFile.isDirectory()) {
                System.out.println("Please provide a valid path to the project directory");
                return;
            }
        }
        final String rootDirectory = args[0];
        TestFileDetector testFileDetector = TestFileDetector.createTestFileDetector();
        ResultsWriter resultsWriter = ResultsWriter.createResultsWriter();
        ClassEntity classEntity;


        //recursively identify all 'java' files in the specified directory
        Util.writeOperationLogEntry("Identify all 'java' test files", Util.OperationStatus.Started);
        FileWalker fw = new FileWalker();
        List<Path> files = fw.getJavaTestFiles(rootDirectory, true);
        Util.writeOperationLogEntry("Identify all 'java' test files", Util.OperationStatus.Completed);


        //foreach of the identified 'java' files, obtain details about the methods that they contain
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Started);
        for (Path file : files) {
            try {
                classEntity = testFileDetector.runAnalysis(file);
                resultsWriter.outputToCSV(classEntity);
            } catch (Exception e) {
                Util.writeException(e, "File: " + file.toAbsolutePath().toString());
            }
        }
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Completed);


        resultsWriter.closeOutputFiles();


    }
}
