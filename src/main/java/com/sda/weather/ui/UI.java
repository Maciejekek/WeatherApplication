package com.sda.weather.ui;

import java.util.Scanner;

public class UI {
    private final Scanner scanner = new Scanner(System.in);
    public void run(){


        System.out.println("Witaj w aplikacji pogodowej");

        while (true){
            System.out.println("Co chciałbyś uczynić");
            System.out.println("1. Dodać nową lokalizację");
            System.out.println("2. Wyświetlić dodane lokalizacje");
            System.out.println("3. Wyświetlić informacje o pogodzie dla lokalizacji");
            System.out.println("4. Zamknąć aplikację");

            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Podana wartość musi być cyfrą. Wpisz cyfrę: ");
            }
        }
    }
}
