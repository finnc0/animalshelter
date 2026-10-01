package sh.finnean.AnimalShelter.utils;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.AnimalType;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ShelterUtil {

    // can be modified in the future instead of hardcoding magic numbers or pulled from a yaml config or similar.
    private static final double dogAdoptionBaseFee = 120.99;
    private static final double catAdoptionBaseFee = 59.66;
    private static final double birdAdoptionBaseFee = 25.00;
    private static final double horseAdoptionBaseFee = 439.99;

    private static final String dataDirectoryURI = "data";


    public static double dogAdoptionFee() { return dogAdoptionBaseFee; }
    public static double catAdoptionFee() { return catAdoptionBaseFee; }
    public static double birdAdoptionFee() { return birdAdoptionBaseFee; }
    public static double horseAdoptionFee() { return horseAdoptionBaseFee; }

    public static String getDataDirURI() { return dataDirectoryURI; }

    public static String strPromptNotBlank(String fieldName, Scanner s, String example) {
        // looks something like "Please enter a fieldname: " or "Please enter a fieldname e.(example): "
        String basePrompt = example == null ? "Please enter a " + fieldName + ": " : "Please enter a " + fieldName + " e.( " + example + " ): ";
        System.out.printf(basePrompt, fieldName);
        String result = s.nextLine();

        // we need to capitalize the fieldname for this next prompt so the grammar is correct.
        String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        while (result.isBlank()) {
            System.out.printf("%s must not be blank. %s", capitalizedFieldName, basePrompt);
            result = s.nextLine();
        }

        return result;
    }

    public static LocalDate promptDate(String label, Scanner scanner) {
        LocalDate vaccDate = null;
        while (vaccDate == null) {
            String dateRaw = strPromptNotBlank(label, scanner, "2007-12-0,none").trim();
            if (dateRaw.toLowerCase().trim().equals("none")) break;
            try {
                vaccDate = LocalDate.parse(dateRaw);
            } catch (DateTimeParseException e) {
                System.out.print("Date must be valid, try again. ");
            }
        }
        return vaccDate;
    }

    public static long longPromptValidNotBlank(String fieldName, Scanner s) {
        while (true) {
            String rawLong = strPromptNotBlank(fieldName, s,null);

            try {
                return Long.parseLong(rawLong);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid " + fieldName + ", try again. ");
            }
        }
    }

    public static int intPromptInRange(String prompt, Scanner s, int size) {
        System.out.print(prompt + ": ");
        while (true) {
            String choiceStr = s.nextLine();

            if (choiceStr.isBlank()) {
                System.out.print("You must enter a choice, try again. ");
                // skip
                continue;
            }

            try {
                int choice = Integer.parseInt(choiceStr);

                // successfully have an int, make sure is in range
                if (choice > 0 && choice <= size) {
                    return choice;
                }
                System.out.println("Choice must be in range 1-" + size + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Choice must be a valid number, try again. ");
            }

        }
    }

    public static boolean boolPromptNotBlank(String fieldName, Scanner s, AnimalType animalType, String prefix) {
        // set default sentence prefix if none is provided. Prefix is used to change the start word of the sentence depending on the context
        if (prefix == null) prefix = "Is";
        String prompt = prefix + " this " + animalType.toString().toLowerCase() + " " + fieldName + "? (true/false): ";

        while (true) {
            System.out.print(prompt);
            String input = s.nextLine().trim().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("This field can't be blank.");
                continue;
            }

            switch (input) {
                case "true", "t", "yes", "y" -> { return true; }
                case "false", "f", "no", "n" -> { return false; }
                default -> System.out.println("You must enter either true or false.");
            }
        }
    }

    public static void displaySubAnimalInfo(Animal a) {
        System.out.println("--Animal Detail View--");
        System.out.println();
        switch (a) {
            case Dog dog -> dog.displayInfo();
            case Cat cat -> cat.displayInfo();
            case Horse horse -> horse.displayInfo();
            case Bird bird -> bird.displayInfo();
            case null, default -> System.out.println("Invalid animal.");
        }
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println();
    }
}
