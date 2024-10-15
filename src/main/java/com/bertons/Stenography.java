package com.bertons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

        ArrayList<Byte> fileBits = getBitsFromFile(src);

        //Navigate the image pixel by pixel
        for (int x = 0; x < imgWidth; x++)
        {
            for(int y = 0; y < imgHeight; y++)
            {
                //Get RGB values of the pixel
                int[] rgbPixel = pixelColorValuesRGB(srcImage.getRGB(x, y));
                System.out.println("Pixel(" +x+","+y+"): " + Arrays.toString(rgbPixel));

                //Change RGB values injecting data
                int[] encodedPixel = {rgbPixel[0],rgbPixel[1],rgbPixel[2]};

                //overwrite the least significant bit
                for(int i = 0; i < 3 && !fileBits.isEmpty(); i++)
                {
                    byte bit = fileBits.getFirst();
                    if(bit == 1)
                    {
                        encodedPixel[i] |= 1;
                    }
                    else
                    {
                        encodedPixel[i] &= ~1;
                    }
                    fileBits.removeFirst();
                }

                stenographyImg.setRGB(x, y, pixelColorToRGB(encodedPixel));
            }
        }

        return stenographyImg;
    }

    private ArrayList<Byte> getBitsFromFile(File src) {
        byte[] fileBytes = new byte[0];
        try{
            fileBytes = Files.readAllBytes(src.toPath());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        ArrayList<Byte> fileBits = new ArrayList<>();

        for(byte b : fileBytes)
        {
            fileBits.add((byte) ((b & 0b10000000) >> 7));
            fileBits.add((byte) ((b & 0b01000000) >> 6));
            fileBits.add((byte) ((b & 0b00100000) >> 5));
            fileBits.add((byte) ((b & 0b00010000) >> 4));
            fileBits.add((byte) ((b & 0b00001000) >> 3));
            fileBits.add((byte) ((b & 0b00000100) >> 2));
            fileBits.add((byte) ((b & 0b00000010) >> 1));
            fileBits.add((byte) ((b & 0b00000001)));
        }

        return fileBits;
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
    private void save(BufferedImage image, String fileName, String format)
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
