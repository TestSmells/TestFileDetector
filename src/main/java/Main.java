import entity.FileEntity;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String file = "C:\\Projects\\TestSmells_ExisitngTools\\samples\\org.xbmc.kore\\app\\src\\test\\java\\org\\xbmc\\kore\\provider\\mediaprovider\\TestValues.java";
        TestFileDetector testFileDetector;

        //recursively identify all 'java' files in the specified directory
        System.out.println("Started - Identify all 'java' files");
        FileWalker fw = new FileWalker();
        List<FileEntity> files = fw.getJavaFiles("C:\\Projects\\TestSmells_ExisitngTools\\samples",true);
        System.out.println("Ended - Identify all 'java' files");


        //foreach of the identified 'java' files, obtain details about the methods that they contain
        System.out.println("Started - Obtain method details");
        for (FileEntity fileEntity: files) {
            testFileDetector = new TestFileDetector();
            fileEntity.setMethods(testFileDetector.parseFile(fileEntity.getFilePath()));
        }
        System.out.println("Ended - Obtain method details");


        //output the results into CSV files
        System.out.println("Started - Save results to CSV");
        ResultsWriter resultsWriter = new ResultsWriter(files);
        resultsWriter.outputToCSV(true);
        System.out.println("Ended - Save results to CSV");


    }
}
