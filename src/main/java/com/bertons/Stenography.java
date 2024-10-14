package com.bertons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Stenography {

    BufferedImage srcImage;
    public Stenography(String imgPath)
    {
        this.srcImage = OpenImage(imgPath);
    }

    public BufferedImage Encode(File src)
    {
        int imgWidth = srcImage.getWidth();
        int imgHeight = srcImage.getHeight();
        BufferedImage stenographyImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < imgWidth; x++)
        {
            for(int y = 0; y < imgHeight; y++)
            {
                //Navigate the image pixel by pixel
                //Get RGB values
                int[] rgbPixel = pixelColorValuesRGB(srcImage.getRGB(x, y));
                System.out.println("Pixel(" +x+","+y+"): " + Arrays.toString(rgbPixel));

                //Change RGB values
                int[] encodedPixel = {0,0,0};

                //overwrite least significant bit




                stenographyImg.setRGB(x, y, pixelColorToRGB(encodedPixel));
            }
        }


        return stenographyImg;
    }


    private BufferedImage OpenImage(String filePath)
    {
        File imgFile = new File(filePath);
        BufferedImage img = null;
        try{
            img = ImageIO.read(imgFile);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return img;
    }

    private int[] pixelColorValuesRGB(int colorRGB)
    {
        int[] rgb = new int[3];
        rgb[0] = (colorRGB >> 16) & 0xFF;
        rgb[1] = (colorRGB >> 8) & 0xFF;
        rgb[2] = (colorRGB) & 0xFF;
        return rgb;
    }
    private int pixelColorToRGB(int[] rgbValues)
    {
        int rgb = rgbValues[2] & 0xFF;
        rgb |= (rgbValues[1] << 8) & 0xFF00;
        rgb |= (rgbValues[0] << 16) & 0xFF0000;
        return rgb;
    }

}
