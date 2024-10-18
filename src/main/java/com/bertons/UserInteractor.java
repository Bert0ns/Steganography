package com.bertons;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public abstract class UserInteractor {
    public static File chooseImage()
    {
        File imgPath = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        while(Objects.isNull(imgPath))
        {
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                imgPath = chooser.getSelectedFile();

            }
            if(Objects.isNull(imgPath) || !isFileAnImage(imgPath))
            {
                System.out.println("Something went wrong, Select image file");
                imgPath = null;
            }
        }
        return imgPath;
    }

    public static File chooseFile()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        File fileChosen = null;
        while(Objects.isNull(fileChosen))
        {
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                fileChosen = chooser.getSelectedFile();
            }
            if(Objects.isNull(fileChosen))
            {
                System.out.println("Something went wrong, Select a file");
            }
        }
        
        return fileChosen;
    }

    public static File chooseSaveDestination()
    {
        JFileChooser chooser = new JFileChooser();
        File pathToSave = null;
        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            pathToSave = chooser.getSelectedFile();
        }
        return pathToSave;
    }

    public static boolean isFileAnImage(File file)
    {
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

    public static void hideFileInImage() {
        System.out.println("Select source image");
        File srcImage = UserInteractor.chooseImage();
        System.out.println(srcImage.getAbsolutePath());

        System.out.println("Select file to encode");
        File fileToEncode = UserInteractor.chooseFile();
        System.out.println(fileToEncode.getAbsolutePath());

        System.out.println("Select where do you want to save the new file");
        File pathToSave = UserInteractor.chooseSaveDestination();
        System.out.println(pathToSave.getAbsolutePath());

        Steganography.hideFileInImage(srcImage, fileToEncode, pathToSave);
    }

    public static void extractFileFromImage() {
        System.out.println("Select source image");
        File srcImage = UserInteractor.chooseImage();
        System.out.println(srcImage.getAbsolutePath());

        System.out.println("Select where do you want to save the new file");
        File pathToSave = UserInteractor.chooseSaveDestination();
        System.out.println(pathToSave.getAbsolutePath());

        Steganography.extractFileFromImage(srcImage, pathToSave);
    }
}
