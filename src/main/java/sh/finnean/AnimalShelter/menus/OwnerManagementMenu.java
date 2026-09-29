package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.manager.OwnerManager;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OwnerManagementMenu extends Menu {

    private final Scanner scanner;
    private final OwnerManager ownerManager;
    private final AnimalManager animalManager;

    public OwnerManagementMenu(String title, Scanner scanner, OwnerManager ownerManager, AnimalManager animalManager) {
        super(title, scanner);
        this.scanner = scanner;
        this.ownerManager = ownerManager;
        this.animalManager = animalManager;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Add new owner");
        System.out.println("2. Remove owner");
        System.out.println("3. View owner");
        System.out.println("4. View all owners");
        System.out.println("5. Back");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            //add new owner
            case 1:
                this.addOwner();
                break;
                // remove a user with their email
            case 2:
                this.removeOwner();
                break;
                // gets a specific owner and their adoptions if any
            case 3:
                this.getOwnerAndAdoptedPets();
                break;
                // displays all owners
            case 4:
                this.displayAllOwners();
                break;
            case 5: return false;
            default:
                System.out.println("Please enter a choice 1-5.");
        }
        return true;
    }

    private void addOwner() {
        // get info to create an owner
        System.out.print("Enter a name: ");
        String name = scanner.nextLine();

        System.out.print("Enter an email: ");
        String email = scanner.nextLine();

        System.out.print("Enter a phone number e. 843-123-4567: ");
        String phoneNumber = scanner.nextLine();

        // catch exception if thrown from ownermanager
        try {
            ownerManager.addOwner(new Owner(name, email, phoneNumber));
            System.out.println("Successfully added a new owner!");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayAllOwners() {
        for (Owner owner : ownerManager.getAllOwners()) {
            // print new line above and before owner data
            System.out.println();
            owner.displayInfo();
            System.out.println();
        }
    }

    private void removeOwner() {
        // we will remove by accepting an email.
        System.out.print("Please enter an email: ");
        String searchEmail = scanner.nextLine();
        // if owner could not be found with email, the exception will be thrown.
        try {
            Owner removingOwner = ownerManager.getByEmail(searchEmail);
            // email is valid and maps to an owner
            // now check if the owner has any adopted pets on their file, if so deny the removal
            if (animalManager.getOwnersAdoptions(removingOwner).isPresent()) throw new IllegalStateException("Please unadopt your pets first.");

            // owner is valid for removal
            ownerManager.removeOwner(removingOwner);
            System.out.println("Successfully removed the owner.");

        } catch(InstanceNotFoundException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getOwnerAndAdoptedPets() {
        System.out.print("Please enter an email: ");
        String viewingEmail = scanner.nextLine();

        // get owner obj and get adopted pets
        try {
            Owner viewingOwner = ownerManager.getByEmail(viewingEmail);
            // successfully have owner obj
            // next get adopted pets
            Optional<List<Animal>> adoptions = animalManager.getOwnersAdoptions(viewingOwner);

            // finally display info
            System.out.println("---Owner Detail View---");
            viewingOwner.displayInfo();

            if (adoptions.isEmpty()) {
                System.out.println("Owner has no current adoptions.");
            } else {
                for (Animal a : adoptions.get()) {
                    switch (a) {
                        case Dog d:
                            d.displayInfo();
                            break;
                        case Horse h:
                            h.displayInfo();
                            break;
                        case Cat c:
                            c.displayInfo();
                            break;
                        case Bird b:
                            b.displayInfo();
                            break;
                        default:
                            System.out.println("Invalid pet.");
                    }
                    // new line after each adoption is printed.
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


