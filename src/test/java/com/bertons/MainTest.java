package com.bertons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void test1() throws IOException {
        File srcImage = new File("src/main/resources/TestImg1.png");
        File fileToEncode = new File("src/main/resources/t1.txt");
        File pathToSave = new File("src/main/java/com/bertons/output/encoded.png");
        Steganography.hideFileInImage(srcImage, fileToEncode, pathToSave);

        File pathToDecode = new File("src/main/java/com/bertons/output/decoded.txt");
        Steganography.extractFileFromImage(pathToSave, pathToDecode);

        byte[] start = Files.readAllBytes(fileToEncode.toPath());
        byte[] startAppend = new byte[start.length + 1];
        System.arraycopy(start, 0, startAppend, 0, start.length);
        startAppend[start.length] = 0;

        byte[] end = Files.readAllBytes(pathToDecode.toPath());
        assertArrayEquals(startAppend, end);
    }
}