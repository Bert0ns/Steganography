package com.bertons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SteganographyImage {
    BufferedImage steganographyImage;

    public SteganographyImage(BufferedImage steganographyImage) {
        this.steganographyImage = steganographyImage;
    }
    public SteganographyImage(String filePath)
    {
        this.steganographyImage = OpenImage(filePath);
    }
    public SteganographyImage(File path)
    {
        this.steganographyImage = OpenImage(path);
    }

    public BufferedImage getImage() {
        return steganographyImage;
    }

    public void saveImage(File path, String format)
    {
        try
        {
            ImageIO.write(steganographyImage, format, path); // ignore returned boolean
        }
        catch(IOException e)
        {
            System.out.println("Write error for " + path.getPath() + ": " + e.getMessage());
        }
    }

    public BufferedImage OpenImage(String filePath)
    {
        File imgFile = new File(filePath);
        return OpenImage(imgFile);
    }

    public BufferedImage OpenImage(File imgFile)
    {
        BufferedImage img = null;
        try{
            img = ImageIO.read(imgFile);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return img;
    }
}
