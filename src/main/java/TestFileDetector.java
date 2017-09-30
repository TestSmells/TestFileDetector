import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import entity.MethodEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFileDetector {
    private String absoluteFilePath;
    private List<MethodEntity> methods;
    private Map<String, Boolean> imports;

    public TestFileDetector(String absoluteFilePath) throws FileNotFoundException {
        methods = new ArrayList<>();
        imports = new HashMap<>();
        imports.put("junit.framework.Test",false);
        imports.put("junit.framework.TestCase",false);
        imports.put("org.junit.Test",false);
        imports.put("android.test.AndroidTestCase",false);
        imports.put("android.test.InstrumentationTestCase",false);
        imports.put("org.junit.Assert",false);
        imports.put("android.test.ActivityInstrumentationTestCase2",false);

        parseFile(absoluteFilePath);
    }

    private void parseFile(String absoluteFilePath) throws FileNotFoundException {
        this.absoluteFilePath = absoluteFilePath;

        if(absoluteFilePath.length()!=0){
            FileInputStream fTemp = new FileInputStream(absoluteFilePath);
            CompilationUnit compilationUnit = JavaParser.parse(fTemp);
            ClassVisitor cv = new ClassVisitor();
            cv.visit(compilationUnit,null);
        }
    }

    public List<MethodEntity> getMethodDetails(){
        return  methods;
    }

    public Map<String, Boolean> getImportDetails(){
        return  imports;
    }


    private class ClassVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            MethodEntity method = new MethodEntity(n.getNameAsString());
            method.setTotalLines(n.getEnd().get().line - n.getBegin().get().line);

            if(n.getAnnotationByName("Test").isPresent()){
                method.setHasAnnotation(true);
            }
            else{
                method.setHasAnnotation(false);
            }

            methods.add(method);

            super.visit(n, arg);
        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            if (n.getNameAsString().contains("junit.framework.Test")){
                imports.replace("junit.framework.Test",false,true);
            }

            if (n.getNameAsString().contains("junit.framework.TestCase")){
                imports.replace("junit.framework.TestCase",false,true);
            }

            if (n.getNameAsString().contains("org.junit.Test")){
                imports.replace("org.junit.Test",false,true);
            }

            if (n.getNameAsString().contains("android.test.AndroidTestCase")){
                imports.replace("android.test.AndroidTestCase",false,true);
            }

            if (n.getNameAsString().contains("android.test.InstrumentationTestCase")){
                imports.replace("android.test.InstrumentationTestCase",false,true);
            }

            if (n.getNameAsString().contains("org.junit.Assert")){
                imports.replace("org.junit.Assert",false,true);
            }

            if (n.getNameAsString().contains("android.test.ActivityInstrumentationTestCase2")){
                imports.replace("android.test.ActivityInstrumentationTestCase2",false,true);
            }

            super.visit(n, arg);
        }
    }
}
