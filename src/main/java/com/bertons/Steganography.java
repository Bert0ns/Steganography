package com.bertons;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Steganography {
    public static boolean hideFileInImage(File srcImage, File fileToEncode, File pathToSave)
    {
        SteganographyImage initialImage = new SteganographyImage(srcImage);
        SteganographyImage encodedImage = Steganography.Encode(initialImage.getImage(), fileToEncode);
        if(Objects.isNull(encodedImage))
        {
            System.out.println("Something went wrong, while encoding");
            return false;
        }

        encodedImage.saveImage(new File(pathToSave.toPath() + ".png"), "png");
        return true;
    }

    public static void extractFileFromImage(File srcImage, File pathToSave)
    {
        SteganographyImage img = new SteganographyImage(srcImage);
        Steganography.Decode(img, new File(pathToSave.getPath() + ".txt"));
    }

    private static SteganographyImage Encode(final BufferedImage srcImage, final File toEncode)
    {
        int imgWidth = srcImage.getWidth();
        int imgHeight = srcImage.getHeight();
        if(!fileCanBeEncodedInImage(imgHeight, imgWidth, toEncode))
        {
            System.out.println("File can't be encoded beacause it is too large");
            return null;
        }

        SteganographyImage stenographyImg = new SteganographyImage(new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB));

        ArrayList<Byte> fileBits = getBitsFromFile(toEncode);
        appendNullByte(fileBits);
        //Navigate the image pixel by pixel
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                //Get RGB values of the pixel
                int[] rgbPixel = pixelColorValuesRGB(srcImage.getRGB(x, y));
                //overwrite the least significant bit
                int[] encodedPixel = getEncodedPixel(rgbPixel, fileBits);

                stenographyImg.getImage().setRGB(x, y, pixelColorToRGB(encodedPixel));

                /*Debug
                System.out.print("Origin Image->  ");
                System.out.println("Pixel(" + x + "," + y + "): " + Arrays.toString(rgbPixel));
                System.out.print("Encoded Image-> ");
                System.out.println("Pixel(" + x + "," + y + "): " + Arrays.toString(encodedPixel));
                 */
            }
        }

        return stenographyImg;
    }
    private static void Decode(final BufferedImage srcImage, final File pathNewFile)
    {
        int imgWidth = srcImage.getWidth();
        int imgHeight = srcImage.getHeight();

        ArrayList<Byte> fileBits = new ArrayList<>();

        //Navigate the image pixel by pixel
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                //Get RGB values of the pixel
                int[] rgbPixel = pixelColorValuesRGB(srcImage.getRGB(x, y));
                //Get the least significant bits
                fileBits.addLast((byte)(rgbPixel[0] & 0x00000001));
                fileBits.addLast((byte)(rgbPixel[1] & 0x00000001));
                fileBits.addLast((byte)(rgbPixel[2] & 0x00000001));
            }
        }

        byte[] fileBytes = getBytesFromBitsList(fileBits);
        fileBytes = cutWhereFound(fileBytes, (byte) 0);
        writeToNewFileTheDecodedBytes(pathNewFile, fileBytes);
    }
    private static void writeToNewFileTheDecodedBytes(File pathNewFile, byte[] fileBytes)
    {
        try
        {
            Files.write(pathNewFile.toPath(), fileBytes);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    private static void Decode(final SteganographyImage srcImage, final File pathNewFile)
    {
        Decode(srcImage.getImage(), pathNewFile);
    }
    private static void appendNullByte(ArrayList<Byte> fileBits)
    {
        //Add a null byte
        for(int i = 0; i < 8; i++)
        {
            fileBits.add((byte) 0);
        }
    }
    private static byte[] cutWhereFound(byte[] bytes, byte toFind)
    {
        int i;
        for(i = 0; i < bytes.length; i++) {
            if(bytes[i] == toFind) {
                break;
            }
        }
        if(i == bytes.length) {
            System.out.println("Error Last byte Not found");
            return bytes;
        }

        byte[] newBytes = new byte[i + 1];
        System.arraycopy(bytes, 0, newBytes, 0, newBytes.length);
        return newBytes;
    }
    private static byte[] getBytesFromBitsList(ArrayList<Byte> fileBits)
    {
        ArrayList<Byte> bytes = new ArrayList<>();
        int bitNumber = 0;
        int byteNumber = 0;
        while(!fileBits.isEmpty())
        {
            if(bitNumber == 0)
            {
                bytes.add((byte) 0);
            }
            if(bitNumber < 8)
            {
                byte data = bytes.getLast();
                data = (byte) ((data << 1) + fileBits.removeFirst());
                bytes.set(byteNumber, data);
            }

            bitNumber++;
            if(bitNumber == 8)
            {
                bitNumber = 0;
                byteNumber++;
            }
        }

        byte[] byteArray = new byte[bytes.size()];

        for(int i = 0; i < bytes.size(); i++)
        {
            byteArray[i] = bytes.get(i);
        }

        return byteArray;
    }
    private static int[] getEncodedPixel(int[] rgbPixel, ArrayList<Byte> fileBits)
    {
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
        return encodedPixel;
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
    private static boolean fileCanBeEncodedInImage(int imgHeight, int imgWidth, File toEncode)
    {
        return (toEncode.length() * 8) <= (long) imgHeight * imgWidth * 3;
    }
}
