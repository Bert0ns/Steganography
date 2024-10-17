package com.bertons;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        System.out.println("Select source image");

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File srcImage = null;

        while(Objects.isNull(srcImage))
        {
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                srcImage = chooser.getSelectedFile();

            }
            if(Objects.isNull(srcImage) || !isFileAnImage(srcImage))
            {
                System.out.println("Something went wrong, Select image file");
                srcImage = null;
            }
        }
        System.out.println("Select file to encode");
        File fileToEncode = null;
        while(Objects.isNull(fileToEncode))
        {
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                fileToEncode = chooser.getSelectedFile();
            }
            if(Objects.isNull(fileToEncode))
            {
                System.out.println("Something went wrong, Select a file");
            }
        }

        System.out.println("Select where do you want to save the new file");
        File pathToSave = null;
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            pathToSave = chooser.getSelectedFile();
        }


        SteganographyImage initialImage = new SteganographyImage(srcImage);
        SteganographyImage encodedImage = Steganography.Encode(initialImage.getImage(), fileToEncode);
        if(Objects.isNull(encodedImage))
        {
            System.out.println("Something went wrong, while encoding");
            return;
        }

        encodedImage.saveImage(new File(pathToSave.toPath() + ".png"), "png");
        Steganography.Decode(encodedImage, new File("src/main/java/com/bertons/output/decoded.txt"));

    }

    public static boolean isFileAnImage(File file) {
        String mimeType;
        try
        {
            mimeType = Files.probeContentType(file.toPath());
        }
        catch(IOException e)
        {
            return false;
        }

        return mimeType != null && mimeType.startsWith("image/");
    }
}