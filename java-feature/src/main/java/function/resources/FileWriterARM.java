package function.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.AutoCloseable;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class FileWriterARM implements AutoCloseable {

    private final FileWriter writer;

    public FileWriterARM(final String fileName) throws IOException {
        writer = new FileWriter(fileName);
    }

    public void writeStuff(final String message) throws IOException {
        writer.write(message);
    }

    @Override
    public void close() throws IOException {
        System.out.println("close called automatically...");
        writer.close();
    }

    public static void main(final String[] args) throws IOException {
        try(final FileWriterARM writerARM = new FileWriterARM("peekaboo.txt")) {
            writerARM.writeStuff("peek-a-boo");

            System.out.println("done with the resource ...");
        }
    }
}
