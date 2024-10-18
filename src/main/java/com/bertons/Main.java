package com.bertons;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        boolean doesChoiceExist = false;
        int choice = 0;
        while(!doesChoiceExist) {
            System.out.println("Do you want to hide a file[1] or to extract a file[2]? \nPress 1 or 2");
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                sc.nextLine();
                choice = 0;
            }
            doesChoiceExist = switch (choice) {
                case 1, 2 -> true;
                default -> {
                    System.out.println("Invalid choice, please try again");
                    yield false;
                }
            };
        }

        if (choice == 1) {
            UserInteractor.hideFileInImage();
        }
        else if (choice == 2) {
            UserInteractor.extractFileFromImage();
        }

        System.out.println("Thanks for using this app,\nBerto");
    }
}