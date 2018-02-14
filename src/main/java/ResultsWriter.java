import com.opencsv.CSVWriter;
import entity.ClassEntity;
import entity.MethodEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ResultsWriter {

    private CSVWriter classCSVWriter, methodCSVWriter, debtCSVWriter;

    public static ResultsWriter createResultsWriter() throws IOException {
        return new ResultsWriter();
    }

    private ResultsWriter() throws IOException {
        String time = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String classFileName = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "Class", time, "csv");
        String methodFileName = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "Method", time, "csv");
        String debtFileName = MessageFormat.format("{0}_{1}_{2}.{3}", "Output", "Debt", time, "csv");
        methodCSVWriter = new CSVWriter(new FileWriter(methodFileName), ',');
        classCSVWriter = new CSVWriter(new FileWriter(classFileName), ',');
        debtCSVWriter = new CSVWriter(new FileWriter(debtFileName), ',');

        createClassFile();
        createMethodFile();
        createDebtFile();
    }
    private void createDebtFile() throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames = {
                "App",
                "Tag",
                "FilePath",
                "RelativeFilePath",
                "FileName",
                "ClassName",
                "Comment"
        };
        fileLines.add(columnNames);

        debtCSVWriter.writeAll(fileLines, false);
        debtCSVWriter.flush();
    }

    private void createClassFile() throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames = {
                "App",
                "Tag",
                "FilePath",
                "RelativeFilePath",
                "FileName",
                "ClassName",
                "TotalImports",
                "TotalMethods",
                "TotalMethodStatements",
                "TotalTestMethods",
                "AnnotationCount",
                "TestsWithoutAnnotationCount",
                "HasTestInFileName",
                "HasTestInClassName",
                "junitFrameworkTest",
                "junitFrameworkTestCase",
                "orgJunitTest",
                "androidTestAndroidTestCase",
                "androidTestInstrumentationTestCase",
                "orgJunitAssert",
                "androidTestActivityInstrumentationTestCase2",
                "HasTechnicalDebtComment",
        };
        fileLines.add(columnNames);

        classCSVWriter.writeAll(fileLines, false);
        classCSVWriter.flush();
    }

    private void createMethodFile() throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] columnNames = {
                "App",
                "Tag",
                "FilePath",
                "RelativeFilePath",
                "FileName",
                "ClassName",
                "MethodName",
                "TotalStatements",
                "TotalParameters",
                "ReturnType",
                "AccessModifier",
                "MethodHasAnnotation",
                "MethodHasTestInName",
                "FileHasTestInName",
                "ClassHasTestInName"
        };
        fileLines.add(columnNames);

        methodCSVWriter.writeAll(fileLines, false);
        methodCSVWriter.flush();
    }

    public void outputToCSV(ClassEntity classEntity) throws IOException {
        outputClassDetails(classEntity);
        outputMethodDetails(classEntity);
        outputDebtDetails(classEntity);
    }

    private void outputDebtDetails(ClassEntity classEntity) throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] dataLine;
        for (String comment : classEntity.getTechnicalDebtComments()) {

            dataLine = new String[7];
            dataLine[0] = classEntity.getAppName();
            dataLine[1] = classEntity.getTagName();
            dataLine[2] = classEntity.getFilePath();
            dataLine[3] = classEntity.getRelativeFilePath();
            dataLine[4] = classEntity.getFileName();
            dataLine[5] = classEntity.getClassName();
            dataLine[6] = "\""+comment.replace('"',' ')+"\"";

            fileLines.add(dataLine);
        }
        debtCSVWriter.writeAll(fileLines, false);
        debtCSVWriter.flush();
    }

    public void closeOutputFiles() throws IOException {
        classCSVWriter.close();
        methodCSVWriter.close();
        debtCSVWriter.close();
    }

    private void outputMethodDetails(ClassEntity classEntity) throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] dataLine;
        for (MethodEntity methodEntity : classEntity.getMethods()) {

            dataLine = new String[15];
            dataLine[0] = classEntity.getAppName();
            dataLine[1] = classEntity.getTagName();
            dataLine[2] = classEntity.getFilePath();
            dataLine[3] = classEntity.getRelativeFilePath();
            dataLine[4] = classEntity.getFileName();
            dataLine[5] = classEntity.getClassName();
            dataLine[6] = methodEntity.getMethodName();
            dataLine[7] = String.valueOf(methodEntity.getTotalStatements());
            dataLine[8] = String.valueOf(methodEntity.getParameterCount());
            dataLine[9] = methodEntity.getReturnType();
            dataLine[10] = methodEntity.getAccessModifier();
            dataLine[11] = String.valueOf(methodEntity.isHasAnnotation());
            dataLine[12] = String.valueOf(methodEntity.isHasTestInName());
            dataLine[13] = String.valueOf(classEntity.isHasTestInFileName());
            dataLine[14] = String.valueOf(classEntity.isHasTestInClassName());

            fileLines.add(dataLine);
        }
        methodCSVWriter.writeAll(fileLines, false);
        methodCSVWriter.flush();
    }

    private void outputClassDetails(ClassEntity classEntity) throws IOException {
        List<String[]> fileLines = new ArrayList<String[]>();
        String[] dataLine;

        dataLine = new String[22];
        dataLine[0] = classEntity.getAppName();
        dataLine[1] = classEntity.getTagName();
        dataLine[2] = classEntity.getFilePath();
        dataLine[3] = classEntity.getRelativeFilePath();
        dataLine[4] = classEntity.getFileName();
        dataLine[5] = classEntity.getClassName();
        dataLine[6] = String.valueOf(classEntity.getTotalImports());
        dataLine[7] = String.valueOf(classEntity.getTotalMethods());
        dataLine[8] = String.valueOf(classEntity.getTotalMethodStatement());
        dataLine[9] = String.valueOf(classEntity.getTotalTestMethods());
        dataLine[10] = String.valueOf(classEntity.getTestAnnotationCount());
        dataLine[11] = String.valueOf(classEntity.getTestMethodWithoutAnnotationCount());
        dataLine[12] = String.valueOf(classEntity.isHasTestInFileName());
        dataLine[13] = String.valueOf(classEntity.isHasTestInClassName());
        dataLine[14] = String.valueOf(classEntity.getHas_junitframeworkTest());
        dataLine[15] = String.valueOf(classEntity.getHas_junitframeworkTestCase());
        dataLine[16] = String.valueOf(classEntity.getHas_orgjunitTest());
        dataLine[17] = String.valueOf(classEntity.getHas_androidtestAndroidTestCase());
        dataLine[18] = String.valueOf(classEntity.getHas_androidtestInstrumentationTestCase());
        dataLine[19] = String.valueOf(classEntity.getHas_orgjunitAssert());
        dataLine[20] = String.valueOf(classEntity.getHas_androidtestActivityInstrumentationTestCase2());
        dataLine[21] = String.valueOf(classEntity.getHasTechnicalDebtComments());

        fileLines.add(dataLine);

        classCSVWriter.writeAll(fileLines, false);
        classCSVWriter.flush();
    }
}
