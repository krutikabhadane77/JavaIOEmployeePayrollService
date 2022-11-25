package com.employeepayroll;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertTrue;

public class NIOFileAPITest {
    private static String HOME = "C:\\Terminalcommand\\linux-content\\JavaIOEmployeePayrollService";
    private static String PLAY_WITH_NIO = "TempPlayGround";

    public boolean deleteFiles(File contentsToDelete) {
        File[] allContents = contentsToDelete.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFiles(file);
            }
        }
        return contentsToDelete.delete();
    }

    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {

        Path homePath = Paths.get(HOME);
        assertTrue(Files.exists(homePath));

        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if (Files.exists(playPath)) deleteFiles(playPath.toFile());
        assertTrue(Files.notExists(playPath));

        Files.createDirectory(playPath);
        assertTrue(Files.exists(playPath));

        IntStream.range(1, 10).forEach(cntr -> {
            Path tempFile = Paths.get(playPath + "/temp" + cntr);

            assertTrue(Files.notExists(tempFile));

            try {Files.createFile(tempFile);}
            catch (IOException e) { System.out.println("IO Exception Occured."); }

            assertTrue(Files.exists(tempFile));
        });

        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);

        Files.newDirectoryStream(playPath).forEach(System.out::println);

        Files.newDirectoryStream(playPath, path -> path.toFile().isFile() && path.toString().startsWith("temp"))
                .forEach(System.out::println);
    }
}
