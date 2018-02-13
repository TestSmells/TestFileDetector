import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import entity.ClassEntity;
import entity.MethodEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestFileDetector {
    private List<MethodEntity> methods;
    private ClassEntity classEntity;
    private ArrayList<String> imports;

    /**
     * Constructor
     */
    private TestFileDetector() {
        initialize();
    }

    /**
     * Factory method that provides a new instance of the TestFileDetector
     *
     * @return new TestFileDetector instance
     */
    public static TestFileDetector createTestFileDetector() {
        return new TestFileDetector();
    }

    private void initialize(){
        methods = new ArrayList<>();
        imports = new ArrayList<>();
    }

    public ClassEntity runAnalysis(Path filePath) throws FileNotFoundException {
        initialize();

        CompilationUnit compilationUnit=null;

        if(filePath != null){
            System.out.println("Processing: "+filePath);
            classEntity = new ClassEntity(filePath);
            FileInputStream fileInputStream = new FileInputStream(classEntity.getFilePath());
            compilationUnit = JavaParser.parse(fileInputStream);
            ClassVisitor cv = new ClassVisitor();
            cv.visit(compilationUnit,null);

            classEntity.setMethods(methods);
            classEntity.setImports(imports);
        }

        return classEntity;
    }

    private class ClassVisitor extends VoidVisitorAdapter<Void> {

        //keywords obtained from: http://ieeexplore.ieee.org/document/7820211/
        private final String[] DEBT_KEYWORDS = {"hack", "workaround", "yuck!", "kludge", "stupidity","needed?","unused?","fixme","todo","wtf?"};

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            classEntity.setClassName(n.getNameAsString());

            for (Comment comment:n.getAllContainedComments()) {
                if (containsKeyword(comment.getContent())){
                    System.out.println(comment.getContent());
                }
            }

            if(n.getComment().isPresent()){
                if (containsKeyword(n.getComment().get().getContent())){
                    System.out.println(n.getComment().get().getContent());
                }
            }

            if(n.getJavadocComment().isPresent()){
                if (containsKeyword(n.getJavadocComment().get().getContent())){
                    System.out.println(n.getJavadocComment().get().getContent());
                }
            }

            for (Comment comment:n.getOrphanComments()) {
                if (containsKeyword(comment.getContent())){
                    System.out.println(comment.getContent());
                }
            }


            super.visit(n, arg);
        }

        private Boolean containsKeyword(String input){
            Boolean hasKeyword = false;

            Pattern pattern;
            Matcher matcher;
            for (String keyword : DEBT_KEYWORDS) {
                pattern = Pattern.compile("\\b"+keyword+"\\b",Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(input);
                while (matcher.find()) {
                /*
                    System.out.print("Start index: " + matcher.start());
                    System.out.print(" End index: " + matcher.end() + " ");
                    System.out.println(matcher.group());
                */
                    return true;
                }
            }

            return hasKeyword;
        }


        @Override
        public void visit(MethodDeclaration n, Void arg) {
            MethodEntity method = new MethodEntity(n.getNameAsString());
            method.setTotalStatements(n.getBody().isPresent()?n.getBody().get().getStatements().size():0);
            method.setParameterCount(n.getParameters().size());
            method.setReturnType(n.getType().toString());
            method.setAccessModifier(n.getModifiers().stream().map(i -> i.asString()).collect(Collectors.joining("; ")));

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
            imports.add(n.getNameAsString());
            super.visit(n, arg);
        }
    }
}
