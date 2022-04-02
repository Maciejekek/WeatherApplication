package com.sda.weather.ui;

import com.sda.weather.location.LocationController;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class UI {

    private final Scanner scanner;
    private final LocationController locationController;

    public void run(){
        System.out.println("|---------------------------|");
        System.out.println("|Witaj w aplikacji pogodowej|");
        System.out.println("|---------------------------|");

        while (true){
            System.out.println("Co chciałbyś uczynić");
            System.out.println("1. Dodać nową lokalizację");
            System.out.println("2. Wyświetlić dodane lokalizacje");
            System.out.println("3. Wyświetlić informacje o pogodzie dla lokalizacji");
            System.out.println("4. Zamknąć aplikację");

            int userInput = getInteger();

            switch (userInput) {
                case 1:
                    createLocation();
                    break;
                case 2:
                    getLocation();
                    break;
                case 3:
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Podana wartość musi być cyfrą! Wpisz cyfrę!");
            }
        }
    }

    private void createLocation() {
        System.out.println("Podaj nazwę miasta: ");
        String city = scanner.nextLine();
        System.out.println("Podaj nazwe regionu: ");
        String region = scanner.nextLine();
        System.out.println("Podaj nazwę kraju: ");
        String country = scanner.nextLine();
        System.out.println("Podaj szerokosc geograficzną: ");
        String longitude = scanner.nextLine();
        System.out.println("Podaj długość geograficzną: ");
        String latitude = scanner.nextLine();
        String response = locationController.createLocation(city, region, country, longitude, latitude);
        //todo rzucanie nowych wątków
        System.out.println("Odpowiedź serwera: " + response + "\n");
    }

    private void getLocation() {
        String result = locationController.getLocations();
        result = result
                .replaceAll("\\{", "\n\t\\{")
                .replaceAll("}]", "}\n]");
        System.out.println("Odpowiedź serwera: \n" + result + "\n");
    }

    private int getInteger() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Podana wartość musi być cyfrą! Wpisz cyfrę: ");
            }
        }
    }
}
