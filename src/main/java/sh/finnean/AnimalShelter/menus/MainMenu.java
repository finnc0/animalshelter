package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.menus.animal.AnimalManagementMenu;

import java.util.Scanner;

public class MainMenu extends Menu {

    private final Scanner scanner;
    private final OwnerManagementMenu ownerManagementMenu;
    private final DataIngestionMenu dataIngestionMenu;
    private final AnimalManagementMenu animalManagementMenu;
    private final AdoptionMenu adoptionMenu;

    public MainMenu(String title,
                    Scanner scanner,
                    OwnerManagementMenu ownerManagementMenu,
                    DataIngestionMenu dataIngestionMenu,
                    AnimalManagementMenu animalManagementMenu,
                    AdoptionMenu adoptionMenu) {
        super(title, scanner);
        this.scanner = scanner;
        this.ownerManagementMenu = ownerManagementMenu;
        this.dataIngestionMenu = dataIngestionMenu;
        this.animalManagementMenu = animalManagementMenu;
        this.adoptionMenu = adoptionMenu;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Owner Management Menu");
        System.out.println("2. Initial Data Import Menu");
        System.out.println("3. Animal Management Menu");
        System.out.println("4. Adoption Menu");
        System.out.println("5. Quit");

        System.out.println();
        System.out.print("Please enter a choice 1-5: ");
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
                // animal management menu
            case 3:
                animalManagementMenu.run();
                break;
            case 4:
                adoptionMenu.run();
                break;
            case 5: return false;
            default:
                // if number is out of bounds, it will fall to default. Make sure choice is a possible choice
                System.out.print("Please enter a choice 1-5.");
        }
        return true;
    }
}
