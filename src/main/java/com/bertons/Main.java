package com.bertons;

import java.io.File;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {

        System.out.println("Select source image");
        File srcImage = UserInteractor.chooseImage();

        System.out.println("Select file to encode");
        File fileToEncode = UserInteractor.chooseFile();

        System.out.println("Select where do you want to save the new file");
        File pathToSave = UserInteractor.chooseSaveDestination();

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
}