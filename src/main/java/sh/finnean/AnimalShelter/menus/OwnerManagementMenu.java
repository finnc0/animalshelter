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
import sh.finnean.AnimalShelter.utils.ShelterUtil;

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

        System.out.println();
        System.out.print("Please enter a choice 1-5: ");
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
                System.out.print("Please enter a choice 1-5.");
        }
        return true;
    }

    private void addOwner() {
        // get info to create an owner
        String name = ShelterUtil.strPromptNotBlank("name", scanner, null);

        String email = ShelterUtil.strPromptNotBlank("email", scanner, null);

        String phoneNumber = ShelterUtil.strPromptNotBlank("phone number", scanner, "843-123-4567");
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
        String searchEmail = ShelterUtil.strPromptNotBlank("email", scanner,null);
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

        String viewingEmail = ShelterUtil.strPromptNotBlank("email", scanner, null);

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
                System.out.println("---ADOPTED PETS---");
                System.out.println();
                for (Animal a : adoptions.get()) {
                    ShelterUtil.displaySubAnimalInfo(a);
                    // new line after each adoption is printed.
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


