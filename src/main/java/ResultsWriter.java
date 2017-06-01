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
        String[] columnNames  = {"FilePath", "FileName", "MethodName","TotalLines","MethodHasAnnotation", "MethodHasTestInName", "FileHasTestInName"};
        fileLines.add(columnNames);

        String[] dataLine;
        for (FileEntity fileEntity: files) {
            for (MethodEntity methodEntity: fileEntity.getMethods()) {
                dataLine = new String[7];
                dataLine[0] = fileEntity.getFilePath();
                dataLine[1] = fileEntity.getFileName();
                dataLine[2] = methodEntity.getMethodName();
                dataLine[3] = String.valueOf(methodEntity.getTotalLines());
                dataLine[4] = methodEntity.isHasAnnotation()?"True":"False";
                dataLine[5] = methodEntity.isHasTestInName()?"True":"False";
                dataLine[6] = fileEntity.isHasTestInFileName()?"True":"False";

                fileLines.add(dataLine);
            }
        }

        writer.writeAll(fileLines,false);

        writer.close();
    }

    private void outputTestFileResults() throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(testFileCSV.toString()), ',');
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames  = {"FilePath", "FileName", "TotalMethods","TotalTestMethods","AnnotationCount", "TestsWithoutAnnotationCount"};
        fileLines.add(columnNames);

        String[] dataLine;
        for (FileEntity fileEntity: files) {
            dataLine = new String[6];
            dataLine[0] = fileEntity.getFilePath();
            dataLine[1] = fileEntity.getFileName();
            dataLine[2] = String.valueOf(fileEntity.getMethods().size());
            dataLine[3] = String.valueOf(fileEntity.getTotalTestMethods());
            dataLine[4] = String.valueOf(fileEntity.getTestAnnotationCount());
            dataLine[5] = String.valueOf(fileEntity.getTestMethodWithoutAnnotationCount());

            fileLines.add(dataLine);
        }

        writer.writeAll(fileLines,false);

        writer.close();
    }


}
