import entity.FileEntity;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String file = "C:\\Projects\\TestSmells_ExisitngTools\\samples\\org.xbmc.kore\\app\\src\\test\\java\\org\\xbmc\\kore\\provider\\mediaprovider\\TestValues.java";
        TestFileDetector testFileDetector;


        FileWalker fw = new FileWalker();
        List<FileEntity> files = fw.getJavaTestFiles("C:\\Projects\\TestSmells_ExisitngTools\\samples",true);


        for (FileEntity fileEntity: files) {
            testFileDetector = new TestFileDetector();
            fileEntity.setMethods(testFileDetector.parseFile(fileEntity.getFilePath()));
        }

        System.out.println(files.size());
    }
}
