import entity.FileEntity;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileWalker {
    private List<FileEntity> files;


    public List<FileEntity> getJavaTestFiles(String directoryPath, boolean recursive) throws IOException {
        files = new ArrayList<>();

        if (recursive) {
            Path startDir = Paths.get(directoryPath);
            Files.walkFileTree(startDir, new FindJavaTestFilesVisitor());
        }
        return files;
    }

    public List<FileEntity> getJavaFiles(String directoryPath, boolean recursive) throws IOException {
        files = new ArrayList<>();

        if (recursive) {
            Path startDir = Paths.get(directoryPath);
            Files.walkFileTree(startDir, new FindJavaFilesVisitor());
        }
        return files;
    }

    private class FindJavaFilesVisitor extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs)
                throws IOException {

            if (file.toString().toLowerCase().endsWith(".java")) {
                files.add(new FileEntity(file));
            }

            return FileVisitResult.CONTINUE;
        }
    }

    public class FindJavaTestFilesVisitor extends SimpleFileVisitor<Path>  {
        @Override
        public FileVisitResult visitFile(Path file,
                                         BasicFileAttributes attrs)
                throws IOException {
            if (file.toString().toLowerCase().endsWith(".java") && file.getFileName().toString().toLowerCase().contains("test")) {
                files.add(new FileEntity(file));
            }
            return FileVisitResult.CONTINUE;
        }
    }
}


