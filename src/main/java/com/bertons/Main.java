package com.bertons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
         Stenography st = new Stenography("src/main/resources/TestImg2.png");
         BufferedImage encodedImage = st.Encode(new File("src/main/resources/t1.txt"));

        save(encodedImage, "src/main/resources/t1", "png");
    }

    private static void save(BufferedImage image, String fileName, String format)
    {
        File file = new File(fileName + "." + format);
        try
        {
            ImageIO.write(image, format, file); // ignore returned boolean
        }
        catch(IOException e)
        {
            System.out.println("Write error for " + file.getPath() + ": " + e.getMessage());
        }
    }
}