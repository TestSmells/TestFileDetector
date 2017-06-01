package  entity;

import java.nio.file.Path;
import java.util.List;


public class FileEntity {
    private Path path;
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

    public long getTestMethodWithoutAnnotationCount() {
        return methods.parallelStream().filter((p)->p.isHasAnnotation()==false && p.isHasTestInName()==true).count();
    }

    public String getFilePath() {
        return path.toAbsolutePath().toString();
    }

    public long getTestAnnotationCount() {
        return methods.parallelStream().filter((p)->p.isHasAnnotation()).count();
    }

    public boolean isHasTestInFileName(){
        return  path.getFileName().toString().toLowerCase().contains("test");
    }

    public String getFileName(){
        return path.getFileName().toString();
    }

    public long getTotalTestMethods() {
        return (getTestMethodWithoutAnnotationCount() + getTestAnnotationCount());
    }
}
