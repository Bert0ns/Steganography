package com.bertons;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void test1() {
        SteganographyImage initialImage = new SteganographyImage("src/main/resources/TestImg1.png");
        SteganographyImage encodedImage = Steganography.Encode(initialImage.getImage(), new File("src/main/resources/t1.txt"));
        encodedImage.saveImage(new File("src/main/java/com/bertons/output/encoded.png"), "png");

        Steganography.Decode(encodedImage, new File("src/main/java/com/bertons/output/decoded.txt"));
    }
}