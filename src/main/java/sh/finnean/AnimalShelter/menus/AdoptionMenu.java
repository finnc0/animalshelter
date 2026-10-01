package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.contracts.Adoptable;
import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.manager.OwnerManager;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import javax.management.InstanceNotFoundException;
import java.util.Scanner;

public class AdoptionMenu extends Menu {

    private final Scanner scanner;
    private final AnimalManager animalManager;
    private final OwnerManager ownerManager;

    public AdoptionMenu(String title, Scanner scanner, AnimalManager animalManager, OwnerManager ownerManager) {
        super(title, scanner);

        this.scanner = scanner;
        this.animalManager = animalManager;
        this.ownerManager = ownerManager;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Adopt an animal");
        System.out.println("2. Unadopt an animal");
        System.out.println("3. Back");

        System.out.print("Please enter a choice 1-3: ");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                this.adoptAnAnimal();
                break;
            case 2:
                this.unAdoptAnAnimal();
                break;
            case 3: return false;
            default:
                System.out.print("Please enter a choice 1-3: ");
        }
        return true;
    }

    private void adoptAnAnimal() {
        // get animal id, then get owner
        long id = ShelterUtil.longPromptValidNotBlank("animal ID", scanner);

        try {
            Animal a = animalManager.getAnimal(id);

            // verify animal is adoptable
            if (a instanceof Adoptable) {
                // if adoptable, lets get owner and link the two.
                String email = ShelterUtil.strPromptNotBlank("email", scanner, null);

                // both getByEmail on owner and getAnimal on animalmanager throw instancenotfound, so we dont need to error handle here really other than the catch.
                Owner owner = ownerManager.getByEmail(email);

                a.setOwner(owner);
                System.out.printf("Successfully adopted %s.", a.getName());
            }
            System.out.println("Animal can not be adopted.");
        } catch (InstanceNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
    private void unAdoptAnAnimal() {
        long id = ShelterUtil.longPromptValidNotBlank("animal ID", scanner);

        Animal animal;
        try {
            animal = animalManager.getAnimal(id);
        } catch (InstanceNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (!(animal instanceof Adoptable adoptable)) {
            System.out.println("Animal can not be adopted or unadopted.");
            return;
        }

        if (!adoptable.isAdoptable()) {
            System.out.println("Animal is already unadopted.");
            return;
        }

        adoptable.setAdoptable(false);
        animal.setOwner(null);
    }
}
