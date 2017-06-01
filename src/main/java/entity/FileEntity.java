package  entity;

import java.nio.file.Path;
import java.util.List;


public class FileEntity {
    private Path path;
    private int testAnnotationCount;
    private int testMethodWithoutAnnotationCount;
    private int totalLines;
    private List<MethodEntity> methods;

    public FileEntity(Path path) {
        this.path = path;
    }

    public List<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodEntity> methods) {
        this.methods = methods;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getTestMethodWithoutAnnotationCount() {
        return testMethodWithoutAnnotationCount;
    }

    public void setTestMethodWithoutAnnotationCount(int testMethodWithoutAnnotationCount) {
        this.testMethodWithoutAnnotationCount = testMethodWithoutAnnotationCount;
    }

    public String getFilePath() {
        return path.toAbsolutePath().toString();
    }

    public int getTestAnnotationCount() {
        return testAnnotationCount;
    }

    public void setTestAnnotationCount(int testAnnotationCount) {
        this.testAnnotationCount = testAnnotationCount;
    }

    public String getFileName(){
        return path.getFileName().toString();
    }

    public int getTotalTestMethods() {
        return (testAnnotationCount + testMethodWithoutAnnotationCount);
    }
}
