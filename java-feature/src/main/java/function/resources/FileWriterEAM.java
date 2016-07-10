package function.resources;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class FileWriterEAM {

    private final FileWriter fileWriter;

    private FileWriterEAM(final String fileName) throws IOException {
        fileWriter = new FileWriter(fileName);
    }

    private void close() throws IOException {
        System.out.println("close called automatically ... ");
        fileWriter.close();
    }

    public void writeStuff(final String message) throws IOException {
        fileWriter.write(message);
    }

    public static void use(final String fileName,
                           final UserInstance<FileWriterEAM, IOException> block) throws IOException {
        final FileWriterEAM writerEAM = new FileWriterEAM(fileName);
        try{
            block.accept(writerEAM);
        } finally {
            writerEAM.close();
        }
    }

    public static void main(String[] args) throws IOException {
        FileWriterEAM.use("eam.txt", writeEAM -> writeEAM.writeStuff("123test321"));

        FileWriterEAM.use("eam_1.txt", writeEAM -> {
            writeEAM.writeStuff("11111111111111\n");
            writeEAM.writeStuff("22222222222222");
        });
    }
}
