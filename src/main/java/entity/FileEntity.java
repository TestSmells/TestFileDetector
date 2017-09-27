package  entity;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;


public class FileEntity {
    private Path path;
    private int totalLines;
    private List<MethodEntity> methods;
    private Map<String, Boolean> imports;

    public FileEntity(Path path) {
        this.path = path;
    }

    public List<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodEntity> methods) {
        this.methods = methods;
    }

    public void setImports(Map<String, Boolean> imports) {
        this.imports = imports;
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

    public boolean getHas_junitframeworkTest(){
        return imports.get("junit.framework.Test");
    }

    public boolean getHas_junitframeworkTestCase(){
        return imports.get("junit.framework.TestCase");
    }

    public boolean getHas_orgjunitTest(){
        return imports.get("org.junit.Test");
    }

    public boolean getHas_androidtestAndroidTestCase(){
        return imports.get("android.test.AndroidTestCase");
    }

    public boolean getHas_androidtestInstrumentationTestCase()
    {
        return  imports.get("android.test.InstrumentationTestCase");
    }

    public boolean getHas_orgjunitAssert(){
        return imports.get("org.junit.Assert");
    }

    public String getRelativeFilePath(){
        String filePath = path.toAbsolutePath().toString();
        String[] splitString = filePath.split("\\\\");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stringBuilder.append(splitString[i] + "\\");
        }
        return filePath.substring(stringBuilder.toString().length()).replace("\\","/");
    }

    public String getAppName(){
        String filePath = path.toAbsolutePath().toString();
        return filePath.split("\\\\")[3];
    }
}
