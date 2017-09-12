import entity.FileEntity;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        final String rootDirectory = "C:\\Projects\\TestSmells\\testing";

        //recursively identify all 'java' files in the specified directory
        Util.writeOperationLogEntry("Identify all 'java' test files", Util.OperationStatus.Started);
        FileWalker fw = new FileWalker();
        List<FileEntity> files = fw.getJavaTestFiles(rootDirectory, true);
        Util.writeOperationLogEntry("Identify all 'java' test files", Util.OperationStatus.Completed);


        //foreach of the identified 'java' files, obtain details about the methods that they contain
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Started);
        TestFileDetector testFileDetector;
        for (FileEntity fileEntity : files) {
            try {
                testFileDetector = new TestFileDetector();
                fileEntity.setMethods(testFileDetector.parseFile(fileEntity.getFilePath()));
            } catch (Exception e) {
                Util.writeException(e, "File: " + fileEntity.getFilePath());
            }
        }
        Util.writeOperationLogEntry("Obtain method details", Util.OperationStatus.Completed);


        //output the results into CSV files
        Util.writeOperationLogEntry("Save results to CSV", Util.OperationStatus.Started);
        ResultsWriter resultsWriter = new ResultsWriter(files);
        resultsWriter.outputToCSV(true);
        Util.writeOperationLogEntry("Save results to CSV", Util.OperationStatus.Completed);


    }
}
