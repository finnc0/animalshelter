package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.manager.CsvManager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DataIngestionMenu extends Menu {

    private final String title;
    private final Scanner scanner;
    private final CsvManager csvManager;

    public DataIngestionMenu(String title, Scanner scanner, CsvManager csvManager) {
        super(title, scanner);

        this.title = title;
        this.scanner = scanner;
        this.csvManager = csvManager;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Search for CSV files to import");
        System.out.println("2. Back");

        System.out.println();
        System.out.print("Please enter a choice 1-2: ");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                if (!handleFileSearchOption()) break;
                break;
            case 2: return false;
            default:
                System.out.println("Please enter a choice 1-2.");
        }
        return true;
    }

    private boolean promptYesNo() {
        System.out.print("Would like to proceed ingesting one of these files (y/n): ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;

            System.out.print("Please enter y or n: ");
        }
    }

    private boolean handleFileSearchOption() {
        try {
            System.out.println("--File List--");
            List<String> fileNames = csvManager.getIngestionFileNames();

            // print all file names in data dir
            for (String n : fileNames) {
                // returns something like "1. file.csv", we add 1 to idx so the list starts at 1\
                System.out.println((fileNames.indexOf(n)+1) + ". " + n);
            }

            // print clean empty line
            System.out.println();

            // next ask user if they want to proceed with ingestion
            boolean proceed = this.promptYesNo();
            if (!proceed) return false;

            // user wants to proceed so we need the number of the file they want to import
            // verify input is a number not any other type.
            boolean run = true;
            while (run) {
                System.out.print("Enter the number of the file you wish to ingest: ");
                String fileNumberStr = scanner.nextLine();
                try {
                    int fileNumberInt = Integer.parseInt(fileNumberStr);

                    // we can get the file name by using the choice - 1 as our choice lists starts at indexOf(fileNames[idx] +1
                    if (fileNumberInt - 1 > fileNames.size()) throw new IndexOutOfBoundsException();
                    String fileNameToIngest = fileNames.get(fileNumberInt-1);
                    // ingest file
                    csvManager.ingestFile(fileNameToIngest);

                    // safely exit loop
                    run = false;
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Please enter a number listed.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while searching for files. Make sure the directory PROJ_ROOT/resources/data exists");
            // return false to start back at menu again
            return false;
        }
        return true;
    }

}
