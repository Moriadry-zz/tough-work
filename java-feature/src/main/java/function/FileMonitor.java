package function;

import java.nio.file.*;
import java.util.concurrent.TimeUnit;

/**
 * Author: Moriatry
 * Date:   15-12-11
 */
public class FileMonitor {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get(".");
        final WatchService watchService = path.getFileSystem().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        System.out.println("Report any file changed within next 1 minute...");

        final WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);
        if(watchKey != null) {
            watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
        }
    }
}
