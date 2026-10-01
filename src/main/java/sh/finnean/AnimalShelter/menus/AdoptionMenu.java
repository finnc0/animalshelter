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
                // verify animal is not already adopted
                // if adoptable, lets get owner and link the two.
                String email = ShelterUtil.strPromptNotBlank("email", scanner, null);

                // both getByEmail on owner and getAnimal on animalmanager throw instancenotfound, so we dont need to error handle here really other than the catch.
                Owner owner = ownerManager.getByEmail(email);

                ((Adoptable) a).adopt(owner);
                System.out.println("Successfully adopted " + a.getName() + ".");
                return;
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
            animal = this.animalManager.getAnimal(id);

            if (!(animal instanceof Adoptable adoptable)) {
                System.out.println("Animal can not be adopted or unadopted.");
                return;
            }

            adoptable.unAdopt();

        } catch (InstanceNotFoundException | IllegalCallerException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Successfully unadopted " + animal.getName());
    }
}
