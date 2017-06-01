import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import entity.MethodEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TestFileDetector {
    private String absoluteFilePath;
    private List<MethodEntity> methods;

    public TestFileDetector() {
        methods = new ArrayList<>();
    }

    public List<MethodEntity> parseFile(String absoluteFilePath) throws FileNotFoundException {
        this.absoluteFilePath = absoluteFilePath;

        if(absoluteFilePath.length()!=0){
            FileInputStream fTemp = new FileInputStream(absoluteFilePath);
            CompilationUnit compilationUnit = JavaParser.parse(fTemp);
            ClassVisitor cv = new ClassVisitor();
            cv.visit(compilationUnit,null);
        }

        return  methods;
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


    }
}
