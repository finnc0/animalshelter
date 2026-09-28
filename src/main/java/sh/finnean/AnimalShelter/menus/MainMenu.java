package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Menu;

import java.util.Scanner;

public class MainMenu extends Menu {

    private final Scanner scanner;
    private final OwnerManagementMenu ownerManagementMenu;
    private final DataIngestionMenu dataIngestionMenu;

    public MainMenu(String title, Scanner scanner, OwnerManagementMenu ownerManagementMenu, DataIngestionMenu dataIngestionMenu) {
        super(title, scanner);
        this.scanner = scanner;
        this.ownerManagementMenu = ownerManagementMenu;
        this.dataIngestionMenu = dataIngestionMenu;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Owner Management Menu");
        System.out.println("2. Initial Data Import Menu");
        System.out.println("3. Animal Management Menu");
        System.out.println("4. Adoption Menu");
        System.out.println("5. Quit");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            // owner management menu
            case 1:
                ownerManagementMenu.run();
                break;
                // data ingestion menu
            case 2:
                dataIngestionMenu.run();
                break;
            case 5: return false;
            default:
                // if number is out of bounds, it will fall to default. Make sure choice is a possible choice
                System.out.println("Please enter a choice 1-5.");
        }
        return true;
    }
}
