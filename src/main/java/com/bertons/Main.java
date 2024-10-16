package com.bertons;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SteganographyImage initialImage = new SteganographyImage("src/main/resources/TestImg1.png");
        SteganographyImage encodedImage = Steganography.Encode(initialImage.getImage(), new File("src/main/resources/t1.txt"));
        encodedImage.saveImage(new File("src/main/java/com/bertons/output/encoded.png"), "png");

        Steganography.Decode(encodedImage, new File("src/main/java/com/bertons/output/decoded.txt"));
    }
}