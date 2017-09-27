import com.opencsv.CSVWriter;
import entity.FileEntity;
import entity.MethodEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ResultsWriter {

    List<FileEntity>  files;
    String testFileCSV,testMethodCSV;

    public ResultsWriter(List<FileEntity>  files){
        this.files = files;
    }

    public void outputToCSV(boolean includeMethodDetails) throws IOException {
        String time =  String.valueOf(Calendar.getInstance().getTimeInMillis());

        if(includeMethodDetails){
           testFileCSV = MessageFormat.format("{0}_{1}_{2}.{3}", "Output","TestFile",time, "csv");
           testMethodCSV = MessageFormat.format("{0}_{1}_{2}.{3}", "Output","TestMethod", time, "csv");
            outputTestFileResults();
            outputTestMethodResults();
       }
       else
        {
            testFileCSV = MessageFormat.format("{0}_{1}_{2}.{3}", "Output","TestFile", time, "csv");
            outputTestFileResults();
        }
    }

    private void outputTestMethodResults() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(testMethodCSV.toString()), ',');
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames  = {"App", "FilePath", "RelativeFilePath", "FileName", "MethodName","TotalLines","MethodHasAnnotation", "MethodHasTestInName", "FileHasTestInName"};
        fileLines.add(columnNames);

        String[] dataLine;
        for (FileEntity fileEntity: files) {
            try {
                for (MethodEntity methodEntity: fileEntity.getMethods()) {
                    dataLine = new String[9];
                    dataLine[0] = fileEntity.getAppName();
                    dataLine[1] = fileEntity.getFilePath();
                    dataLine[2] = fileEntity.getRelativeFilePath();
                    dataLine[3] = fileEntity.getFileName();
                    dataLine[4] = methodEntity.getMethodName();
                    dataLine[5] = String.valueOf(methodEntity.getTotalLines());
                    dataLine[6] = methodEntity.isHasAnnotation()?"True":"False";
                    dataLine[7] = methodEntity.isHasTestInName()?"True":"False";
                    dataLine[8] = fileEntity.isHasTestInFileName()?"True":"False";

                    fileLines.add(dataLine);
                }
            } catch (Exception e) {
                Util.writeException(e,"File: "+ fileEntity.getFilePath());
            }
        }

        writer.writeAll(fileLines,false);

        writer.close();
    }

    private void outputTestFileResults() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(testFileCSV.toString()), ',');
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames  = {"App","FilePath", "RelativeFilePath", "FileName", "TotalMethods","TotalTestMethods","AnnotationCount", "TestsWithoutAnnotationCount", "HasTestInFileName",
                "junit.framework.Test", "junit.framework.TestCase", "org.junit.Test", "android.test.AndroidTestCase", "android.test.InstrumentationTestCase", "org.junit.Assert"};
        fileLines.add(columnNames);

        String[] dataLine;
        for (FileEntity fileEntity: files) {
            try {
                dataLine = new String[15];
                dataLine[0] = fileEntity.getAppName();
                dataLine[1] = fileEntity.getFilePath();
                dataLine[2] = fileEntity.getRelativeFilePath();
                dataLine[3] = fileEntity.getFileName();
                dataLine[4] = String.valueOf(fileEntity.getMethods().size());
                dataLine[5] = String.valueOf(fileEntity.getTotalTestMethods());
                dataLine[6] = String.valueOf(fileEntity.getTestAnnotationCount());
                dataLine[7] = String.valueOf(fileEntity.getTestMethodWithoutAnnotationCount());
                dataLine[8] = fileEntity.isHasTestInFileName()?"True":"False";
                dataLine[9] = fileEntity.getHas_junitframeworkTest()?"True":"False";
                dataLine[10] = fileEntity.getHas_junitframeworkTestCase()?"True":"False";
                dataLine[11] = fileEntity.getHas_orgjunitTest()?"True":"False";
                dataLine[12] = fileEntity.getHas_androidtestAndroidTestCase()?"True":"False";
                dataLine[13] = fileEntity.getHas_androidtestInstrumentationTestCase()?"True":"False";
                dataLine[14] = fileEntity.getHas_orgjunitAssert()?"True":"False";

                fileLines.add(dataLine);
            } catch (Exception e) {
                Util.writeException(e,"File: "+ fileEntity.getFilePath());
            }
        }

        writer.writeAll(fileLines,false);

        writer.close();
    }


}
