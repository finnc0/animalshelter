package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Menu;

import java.util.Scanner;

public class AnimalManagementMenu extends Menu {
    public AnimalManagementMenu(String title, Scanner scanner) {
        super(title, scanner);
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Add an animal");
        System.out.println("2. Remove an animal");
        System.out.println("3. Get a specific animal");
        System.out.println("4. Get all animals");
        System.out.println("5. Get all adopted animals");
        System.out.println("6. Get all un-adopted animals");
        System.out.println("7. Modify an animal");
        System.out.println("8. Back");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch(choice) {

            // adding an animal
            case 1:
                this.addAnimal();
                break;
            case 8: return false;
            default:
                System.out.println("Please enter a choice 1-8.");
        }
        return true;
    }

    private void addAnimal() {
        System.out.println("Adding an animal");
    }
}
