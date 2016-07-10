package function;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class FilesOpt {

    public static void listTheHardWay() {
        List<File> files = new ArrayList<>();
        File[] filesInCurrentDir = new File(".").listFiles();
        for (File file : filesInCurrentDir) {
            File[] filesInSubDir = file.listFiles();
            if (filesInSubDir != null) {
                files.addAll(Arrays.asList(filesInSubDir));
            } else {
                files.add(file);
            }
        }
        System.out.println("Count: " + files.size());
    }

    public static void betterWay() {
        List<File> files = Stream.of(new File(".").listFiles())
                .flatMap(file -> file.listFiles() == null ? Stream.of(file) : Stream.of(file.listFiles()))
                .collect(Collectors.toList());
        System.out.println("Better Way: " + files.size());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("list all files: ");
        Files.list(Paths.get(".")).forEach(System.out::println);

        System.out.println("list all directories: ");
        Files.list(Paths.get(".")).filter(Files::isDirectory).forEach(System.out::println);

        final String[] files = new File("./src/main/java/com/sandbox/function").list(new java.io.FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.endsWith(".java");
            }
        });
        Arrays.asList(files).stream().forEach(System.out::println);

        final File[] files1 = new File(".").listFiles(File::isHidden);
        Arrays.asList(files1).forEach(file->System.out.println(file.getName()));

        System.out.println("Files.newDirectoryStream: ");
        Files.newDirectoryStream(Paths.get("/home/Moriatry/document/github/java-sandbox/src/main/java/com/sandbox/function"),
                path->path.toString().endsWith(".java")).forEach(System.out::println);

        System.out.println("List the Hard way: ");
        listTheHardWay();

        betterWay();
    }
}
