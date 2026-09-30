package sh.finnean.AnimalShelter.utils;

import sh.finnean.AnimalShelter.instance.AnimalType;

import javax.management.InstanceNotFoundException;
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

    public static boolean boolPromptNotBlank(String fieldName, Scanner s, AnimalType animalType, String prefix) {
        // set default sentence prefix.
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
}
