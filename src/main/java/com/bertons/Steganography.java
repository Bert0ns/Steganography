package com.bertons;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Steganography {
    public static SteganographyImage Encode(BufferedImage srcImage, File toEncode) {
        int imgWidth = srcImage.getWidth();
        int imgHeight = srcImage.getHeight();
        SteganographyImage stenographyImg = new SteganographyImage(new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB));

        ArrayList<Byte> fileBits = getBitsFromFile(toEncode);

        //Navigate the image pixel by pixel
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                //Get RGB values of the pixel
                int[] rgbPixel = pixelColorValuesRGB(srcImage.getRGB(x, y));
                //overwrite the least significant bit
                int[] encodedPixel = {rgbPixel[0], rgbPixel[1], rgbPixel[2]};
                for (int i = 0; i < 3 && !fileBits.isEmpty(); i++) {
                    byte bit = fileBits.getFirst();
                    if (bit == 1) {
                        encodedPixel[i] |= 1;
                    } else {
                        encodedPixel[i] &= ~1;
                    }
                    fileBits.removeFirst();
                }

                stenographyImg.getImage().setRGB(x, y, pixelColorToRGB(encodedPixel));

                //Debug
                System.out.print("Origin Image->  ");
                System.out.println("Pixel(" + x + "," + y + "): " + Arrays.toString(rgbPixel));
                System.out.print("Encoded Image-> ");
                System.out.println("Pixel(" + x + "," + y + "): " + Arrays.toString(encodedPixel));
            }
        }

        return stenographyImg;
    }

    private static ArrayList<Byte> getBitsFromFile(File src)
    {
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
    private static int[] pixelColorValuesRGB(int colorRGB)
    {
        int[] rgb = new int[3];
        rgb[0] = (colorRGB >> 16) & 0xFF;
        rgb[1] = (colorRGB >> 8) & 0xFF;
        rgb[2] = (colorRGB) & 0xFF;
        return rgb;
    }
    private static int pixelColorToRGB(int[] rgbValues)
    {
        int rgb = rgbValues[2] & 0xFF;
        rgb |= (rgbValues[1] << 8) & 0xFF00;
        rgb |= (rgbValues[0] << 16) & 0xFF0000;
        return rgb;
    }
}
