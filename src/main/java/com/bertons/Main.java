package com.bertons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SteganographyImage initialImage = new SteganographyImage("src/main/resources/TestImg2.png");
         SteganographyImage encodedImage = Steganography.Encode(initialImage.getImage(), new File("src/main/resources/t1.txt"));

        encodedImage.saveImage(new File("src/main/java/com/bertons/output/encoded.png"), "png");
    }
}