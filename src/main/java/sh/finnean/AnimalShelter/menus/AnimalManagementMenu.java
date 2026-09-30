package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.AnimalType;
import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

public class AnimalManagementMenu extends Menu {

    private final AnimalManager animalManager;
    private final Scanner scanner;

    public AnimalManagementMenu(String title, Scanner scanner, AnimalManager animalManager) {
        super(title, scanner);

        this.animalManager = animalManager;
        this.scanner = scanner;
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
        // make uppercase so we can compare with enum.
        AnimalType type = null;
        while (type == null) {
            String raw = ShelterUtil.strPromptNotBlank("animal type", scanner, "dog,cat,bird,horse")
                    .trim().toUpperCase();
            try {
                type = AnimalType.valueOf(raw);
            } catch (IllegalArgumentException e) {
                System.out.print("Type must be one of " + Arrays.toString(AnimalType.values()) + ". ");
            }
        }

        // verify type is one of animal type
        String name = ShelterUtil.strPromptNotBlank("name", scanner,null);

        // parse date, make sure it's a valid date.
        LocalDate vaccDate = null;
        while (vaccDate == null) {
            String vaccDateRaw = ShelterUtil.strPromptNotBlank("vaccination date", scanner,"2007-12-0,none").trim();
            if (vaccDateRaw.toLowerCase().trim().equals("none")) break;
            try {
                vaccDate = LocalDate.parse(vaccDateRaw);
            } catch (DateTimeParseException e) {
                System.out.print("Date must be valid, try again. ");
            }
        }

        // now that we have the abstract data, lets get the animal specific fields.

        switch (type) {
            case DOG:
                // dogs specific fields include crateTrained and likesWalks
                boolean crateTrained = ShelterUtil.boolPromptNotBlank("crate trained",scanner, AnimalType.DOG, null);
                boolean likesWalks = ShelterUtil.boolPromptNotBlank("likes walks",scanner, AnimalType.DOG, "Does");

                Animal dog = new Dog(name,vaccDate,null,likesWalks,crateTrained);

                this.animalManager.addAnimal(dog);
                System.out.println("Successfully added the dog!");
                break;
            case CAT:
                // cat specific fields include likesCatNip and isLitterBoxTrained
                boolean likesCatNip = ShelterUtil.boolPromptNotBlank("like catnip",scanner, AnimalType.CAT, "Does");
                boolean isLitterBoxTrained = ShelterUtil.boolPromptNotBlank("litter box trained",scanner, AnimalType.CAT, null);

                Animal cat = new Cat(name,vaccDate,null,likesCatNip,isLitterBoxTrained);

                this.animalManager.addAnimal(cat);
                System.out.println("Successfully added the cat!");
                break;
            case BIRD:
                // cat specific fields include likesCatNip and isLitterBoxTrained
                boolean canTalk = ShelterUtil.boolPromptNotBlank("talk",scanner, AnimalType.BIRD, "Can");
                boolean canFly = ShelterUtil.boolPromptNotBlank("fly",scanner, AnimalType.BIRD, "Can");

                Animal bird = new Bird(name,vaccDate,null,canTalk,canFly);

                this.animalManager.addAnimal(bird);
                System.out.println("Successfully added the bird!");
                break;
            case HORSE:
                // horse specific fields include isRideable
                boolean isRideable = ShelterUtil.boolPromptNotBlank("rideable",scanner, AnimalType.HORSE, null);

                Animal horse = new Horse(name,vaccDate,null,isRideable);

                this.animalManager.addAnimal(horse);
                System.out.println("Successfully added the horse!");
                break;
            default: System.out.println("Invalid type. This error should not occur.");
        }
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
