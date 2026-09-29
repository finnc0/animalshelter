package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;
import sh.finnean.AnimalShelter.manager.AnimalManager;

import java.util.Scanner;

public class AnimalManagementMenu extends Menu {

    private final AnimalManager animalManager;

    public AnimalManagementMenu(String title, Scanner scanner, AnimalManager animalManager) {
        super(title, scanner);

        this.animalManager = animalManager;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Add an animal");
        System.out.println("2. Remove an animal");
        System.out.println("3. Get a specific animal (including actions)");
        System.out.println("4. Get all animals");
        System.out.println("5. Get all adopted animals");
        System.out.println("6. Get all un-adopted animals");
        System.out.println("7. Modify an animal");
        System.out.println("8. Create contacts for all animals");
        System.out.println("8. Back");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch(choice) {

            // adding an animal
            case 1:
                this.addAnimal();
                break;
            case 4:
                this.getAllAnimals();
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

    private void getAllAnimals() {
        for (Animal a : this.animalManager.getAllAnimals()) {
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
}
