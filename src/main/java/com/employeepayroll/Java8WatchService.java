package com.employeepayroll;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Java8WatchService {
    public final WatchService watcher;
    public final Map<WatchKey, Path> dirWatchers;
    public Java8WatchService(Path dir) throws IOException{
        this.watcher = FileSystems.getDefault().newWatchService();
        this.dirWatchers = new HashMap<WatchKey,Path>();
        scanAndRegisterDir(dir);
    }
    public void registerDirWatchers(Path dir) throws IOException{
        WatchKey key = dir.register(watcher,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        dirWatchers.put(key, dir);
    }
    public void scanAndRegisterDir(final Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attrs) throws IOException{
                registerDirWatchers(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    public boolean processEvents() {
        while(true) {
            WatchKey key;
            try {
                key = watcher.take();
            }catch(InterruptedException exception) {
                return false;
            }
            Path dir = dirWatchers.get(key);
            if(dir==null) continue;
            for(WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                System.out.format("%s : %s\n", event.kind().name(),child);

                if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    try {
                        if(Files.isDirectory(child)) scanAndRegisterDir(child);
                    }catch (IOException exception) {}
                }else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
                    if(Files.isDirectory(child)) dirWatchers.remove(key);
                }
            }

            boolean valid = key.reset();
            if(!valid) {
                dirWatchers.remove(key);
                if(dirWatchers.isEmpty()) break;
            }
        }
        return true;
    }
}
