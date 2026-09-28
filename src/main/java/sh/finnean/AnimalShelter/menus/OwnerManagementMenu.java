package sh.finnean.AnimalShelter.menus;

import java.util.Scanner;

public class OwnerManagementMenu extends Menu{

    private final Scanner scanner;

    public OwnerManagementMenu(String title, Scanner scanner) {
        super(title, scanner);
        this.scanner = scanner;
    }

    @Override
    protected void printOptions() {
        System.out.println("1. Add new owner");
        System.out.println("2. Remove owner");
        System.out.println("3. View owner");
        System.out.println("4. View all owners");
        System.out.println("5. Back");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            case 1: System.out.println("Adding new owner");
            case 5: return false;
            default: return false;
        }
    }
}
